
//---------------------지도 생성--------------------------//
// 마커를 담을 배열입니다
var markers = [];

var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

//---------------------지도 확대/축소--------------------------//

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

// 지도가 확대 또는 축소되면 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
kakao.maps.event.addListener(map, 'zoom_changed', function() {

    // 지도의 현재 레벨을 얻어옵니다
    var level = map.getLevel();

});

//---------------------중심좌표 이동----------------------------//

//중심좌표 이동
function setCenter(lng, lat) {
    // 이동할 위도 경도 위치를 생성합니다
    var moveLatLon = new kakao.maps.LatLng(lat, lng);
    // 지도 중심을 이동 시킵니다
    map.setCenter(moveLatLon);
}

//중심좌표 부드럽게 이동
function panTo(lat, lng) {
    // 이동할 위도 경도 위치를 생성합니다
    var moveLatLon = new kakao.maps.LatLng(lat, lng);
    // 지도 중심을 부드럽게 이동시킵니다. 이동할 거리가 지도 화면보다 크면 효과 없이 이동합니다
    map.panTo(moveLatLon);
}

//--------------------영역선택 사용 유무 설정-----------------------//

var scope = angular.element(document.getElementsByClassName('map_wrap')).scope();
// var scope = angular.element(document.getElementsByClassName('map_wrap')).scope().$parent.$$childHead;

//---------------------클릭시 마커 생성----------------------------//

// 지도에 표시된 마커 객체를 가지고 있을 배열입니다
var testMarkers = [];
// 마커의 위경도 정보
var testPosition = [];
// 마커로 인해 그려진 사각형 정보
var sltRectangle = [];

// 지도를 클릭했을때 클릭한 위치에 마커를 추가하도록 지도에 클릭이벤트를 등록
kakao.maps.event.addListener(map, 'click', function(mouseEvent) {

    if(scope.useCheck && (testMarkers.length >= 0 && testMarkers.length < 2)){
        // 클릭한 위치에 마커를 표시합니다
        testaddMarker(mouseEvent.latLng);
    }

});

//------------------- 지도 현재위치 반환 -----------------------//

// 현재위치 반환 성공시
function locationSuccess(position){
    panTo(position.coords.latitude, position.coords.longitude);
};
// 현재위치 반환 에러시
function locationError(error){
    console.log(error.code);
};
// 지도의 현재 위치를 반환하는 함수
function presentLocation(){
    var lo = navigator.geolocation.getCurrentPosition(locationSuccess, locationError);
};


//------------------ 선택한 영역을 통한 검색 결과 ---------------------//


var buildingValueList = {};

// 선택한 주택단지의 공시지가 리스트 출력
function makeValueList(pnu1, pnu2){

    pnu1 = pnu1+"";
    pnu2 = pnu2+"";
    var pnu = pnu1+pnu2;

    console.log(pnu);

    document.getElementById('mainLoading').style.display = 'block';

    $.ajax({
        url: '/api/pblntf/value/building',
        type: 'GET',
        data: {
            pnu: pnu
        },
        success: function(data){
            console.log(data);
            buildingValueList = angular.copy(data);

            $('#valueResultBody').empty();

            if(buildingValueList.length === 0){
                var emptyResult = "<tr id=\"emptyResult\">\n" +
                    "<td colspan=\"3\" style=\"height: 70px; text-align: center; padding-top: 7%; background-color: #f9f9f9;\">\n" +
                    "<h4>조회 결과가 존재하지 않습니다</h4>\n" +
                    "</td>\n" +
                    "</tr>";
                $('#valueResultBody').append(emptyResult);
            }else if(buildingValueList.length === 1 && buildingValueList[0].dong === "-1"){
                var emptyResult = "<tr id=\"emptyResult\">\n" +
                    "<td colspan=\"3\" style=\"height: 70px; text-align: center; padding-top: 7%; background-color: #f9f9f9;\">\n" +
                    "<h4>건물 정보가 존재하지 않습니다.\n국토부에 문의하세요</h4>\n" +
                    "</td>\n" +
                    "</tr>";
                $('#valueResultBody').append(emptyResult);
            }
            else if(buildingValueList.length === 1 && buildingValueList[0].dong === "-2"){
                var emptyResult = "<tr id=\"emptyResult\">\n" +
                    "<td colspan=\"3\" style=\"height: 70px; text-align: center; padding-top: 7%; background-color: #f9f9f9;\">\n" +
                    "<h4>건물 정보 데이터를 불러오는 데 많은 시간이 소요됩니다. 잠시 후 다시 시도해 주세요</h4>\n" +
                    "</td>\n" +
                    "</tr>";
                $('#valueResultBody').append(emptyResult);
            }

            //공시지가 결과 리스트 생성
            for(var i=0;i<buildingValueList.length;i++){
                if(buildingValueList[i].dong !== "-1" && buildingValueList[i].dong !== "-2"){
                    if(buildingValueList[i].dong === ""){
                        var row = "<tr onclick='sltBuildingLocation("+i+")' style='cursor: pointer'>" +
                            "<td id='td"+buildingValueList[i].dong+"-1' class=\"text-center\">"+'-'+"</td>" +
                            "<td id='td"+buildingValueList[i].dong+"-2' class=\"text-center\">"+numberWithCommas(buildingValueList[i].avgValue)+"</td>" +
                            "<td id='td"+buildingValueList[i].dong+"-3' class=\"text-center\">"+numberWithCommas(buildingValueList[i].unitArValue)+"</td></tr>";
                    }else{
                        var row = "<tr onclick='sltBuildingLocation("+i+")' style='cursor: pointer'>" +
                            "<td id='td"+buildingValueList[i].dong+"-1' class=\"text-center\">"+buildingValueList[i].dong+"</td>" +
                            "<td id='td"+buildingValueList[i].dong+"-2' class=\"text-center\">"+numberWithCommas(buildingValueList[i].avgValue)+"</td>" +
                            "<td id='td"+buildingValueList[i].dong+"-3' class=\"text-center\">"+numberWithCommas(buildingValueList[i].unitArValue)+"</td></tr>";
                    }

                    $('#valueResultBody').append(row);
                }
            }

            $('#modal-header').empty();
            // 새로운 헤더요소 추가
            if(buildingValueList.length == 0){
                $('#modal-header').append("<h4 class=\"modal-title\" id=\"myLargeModalLabel\">"+'공시지가 조회 결과'+"</h4>");
            }else{
                $('#modal-header').append("<h4 class=\"modal-title\" id=\"myLargeModalLabel\">"+buildingValueList[0].name+"</h4>");
            }

            //pnuList는 숨기고, valueList 노출
            document.getElementById("pnuList").style.display = "none";
            document.getElementById("valueList").style.display = "block";

            makeValueListMarker();
            document.getElementById('mainLoading').style.display = 'none';


        },
        error: function (request, status, error){
            var msg = "ERROR : " + request.status + "<br>"
            msg +=  + "내용 : " + request.responseText + "<br>" + error;
            console.log(msg);

            document.getElementById('mainLoading').style.display = 'none';
        }
    });
};

// 숫자 콤마 구분자
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

//---------------------------------- 결과값 마커 뿌리기 테스트 -------------------------------------------//

var resultMarkers = [];
var selectedMarker = null;
// 임시 마커 삭제 ( 영역선택 삭제하고 새로운 마커 뿌려야함 )
function makeValueListMarker() {

    // 영역선택 한 것 삭제
    setMarkers(null);
    deleteRectangle();
    testMarkers = [];
    testPosition = [];
    sltRectangle = [];

    //새로 마커 뿌려야함 // 마커 뿌릴 때, 이미지 변경할 수 있도록 체크
    // 결과 포지션은 실제 데이터 받았으 ㄹ때,
    for(var i=0;i<buildingValueList.length;i++){

        var imageSize = new kakao.maps.Size(29,42);
        var markerImage = new kakao.maps.MarkerImage(nomalImage, imageSize);

        var marker = new kakao.maps.Marker({
            map: map,
            position: new kakao.maps.LatLng(buildingValueList[i].latitude, buildingValueList[i].longitude),
            title:  buildingValueList[i].dong,
            // image: markerImage
        });

        clickResultMarkerListener(marker);

        resultMarkers.push(marker);
    }

};

// 마커에 클릭 이벤트 등록. for문 내부에서 직접 이벤트 등록을 처리하려 할 경우, 마지막 인덱스의 마커에만 이벤트가 등록되기 때문에,
// 별도의 함수로 정의하고 값을 전달받아 등록
function clickResultMarkerListener(marker) {

    kakao.maps.event.addListener(marker, 'click', function() {

        // 클릭된 마커가 없고, click 마커가 클릭된 마커가 아니면
        // 마커의 이미지를 클릭 이미지로 변경합니다
        if (!prevMarker || prevMarker !== marker) {

            // 클릭된 마커 객체가 null이 아니면
            // 클릭된 마커의 이미지를 기본 이미지로 변경하고
            if( !!prevMarker ){
                prevMarker.setImage(new kakao.maps.MarkerImage(nomalImage, new kakao.maps.Size(29,42)));

                // 선택된 글자 색 되돌려줌
                document.getElementById("td"+prevMarker.mc+"-1").classList.remove("sltValue")
                document.getElementById("td"+prevMarker.mc+"-2").classList.remove("sltValue");
                document.getElementById("td"+prevMarker.mc+"-3").classList.remove("sltValue");
            }

            // 현재 클릭된 마커의 이미지는 클릭 이미지로 변경합니다
            marker.setImage(new kakao.maps.MarkerImage(clickImage, new kakao.maps.Size(29,42)));

            document.getElementById("td"+marker.mc+"-1").classList.add("sltValue");
            document.getElementById("td"+marker.mc+"-2").classList.add("sltValue");
            document.getElementById("td"+marker.mc+"-3").classList.add("sltValue");
        }

        // 클릭된 마커를 현재 클릭된 마커 객체로 설정합니다
        prevMarker = marker;
    });
}

var nomalImage = 'http://t1.daumcdn.net/mapjsapi/images/marker.png';
var clickImage = "http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
var prevMarker = null;

// 공시지가 가격 리스트 중 가격 선택
function sltBuildingLocation(num) {

    // 이전에 선택한 마커가 존재할 경우 마커 이미지 기본으로 변경, td 선택 해제
    if(prevMarker != null){

        var markerImage = new kakao.maps.MarkerImage(nomalImage, new kakao.maps.Size(29,42));
        prevMarker.setImage(markerImage);
        document.getElementById("td"+prevMarker.mc+"-1").classList.remove("sltValue")
        document.getElementById("td"+prevMarker.mc+"-2").classList.remove("sltValue");
        document.getElementById("td"+prevMarker.mc+"-3").classList.remove("sltValue");

    }

    // 클릭한 마커 이미지 변경
    var clickMarkerImage = new kakao.maps.MarkerImage(clickImage, new kakao.maps.Size(29,42));
    resultMarkers[num].setImage(clickMarkerImage);
    prevMarker = resultMarkers[num];

    document.getElementById("td"+prevMarker.mc+"-1").classList.add("sltValue");
    document.getElementById("td"+prevMarker.mc+"-2").classList.add("sltValue");
    document.getElementById("td"+prevMarker.mc+"-3").classList.add("sltValue");

};


//------------------ 지도에서 선택한 영역 마커 표시 ----------------------//

// 마커를 생성하고 지도위에 표시하는 함수입니다
function testaddMarker(position) {

    // 마커 생성
    var marker = new kakao.maps.Marker({
        position: position
    });

    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);

    // 생성된 마커를 배열에 추가합니다
    testMarkers.push(marker);
    // 마커의 위도, 경도를 배열에 추가
    testPosition.push(position);

    setMarkers(map);

    if(testMarkers.length == 2){
        drawRectangle();
    }
};

// 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수
function setMarkers(map) {
    for (var i = 0; i < testMarkers.length; i++) {
        testMarkers[i].setMap(map);
    }
}

// 공시지가 정보 결과 마커 삭제
function deleteResultMarkers(map) {
    for( var i=0; i<resultMarkers.length; i++){
        resultMarkers[i].setMap(map);
    }
    // prevMarker = -1;
    prevMarker = null;
    selectedMarker = null;
}

// 사각형 영역 삭제
function deleteRectangle() {
    for(var i=0;i<sltRectangle.length;i++){
        sltRectangle[i].setMap(null);
    }
}

// 영역 선택 초기화
function deleteMarkers() {

    setMarkers(null);
    deleteResultMarkers(null);
    deleteRectangle();
    testMarkers = [];
    testPosition = [];
    sltRectangle = [];
    resultMarkers = [];

    var temp = document.getElementById('modal-content');
    temp.style.display = "none";

    var keyword = document.getElementById('keyword');
    keyword.value = null;

    // 영역 다시선택 기능 이용할 시, 헤더부분 공동주택 단지 선택으로 변경
    $('#modal-header').empty();
    $('#modal-header').append("<h4 class='modal-title' id='myLargeModalLabel'>영역 내 공동주택 단지 선택 </h4>");

    useMarker(true);
}


//---------------------선택 영역에 사각형 그리기------------------------//

function drawRectangle() {

    var sw = new kakao.maps.LatLng(testPosition[0].Ha, testPosition[0].Ga), // 사각형 영역의 남서쪽 좌표
        ne = new kakao.maps.LatLng(testPosition[1].Ha, testPosition[1].Ga); // 사각형 영역의 북동쪽 좌표

    // 사각형을 구성하는 영역정보를 생성. 영역정보는 LatLngBounds 객체로 넘겨준다
    var rectangleBounds = new kakao.maps.LatLngBounds(sw, ne);

    // 지도에 표시할 사각형을 생성합니다
    var rectangle = new kakao.maps.Rectangle({
        bounds: rectangleBounds,    // 그려질 사각형의 영역정보
        strokeWeight: 4,            // 선의 두께
        strokeColor: '#FF3DE5',     // 선의 색깔
        strokeOpacity: 1,           // 선의 불투명도. 1에서 0 사이의 값이며 0에 가까울수록 투명
        strokeStyle: 'shortdashdot', // 선의 스타일
        fillColor: '#FF8AEF',       // 채우기 색깔
        fillOpacity: 0.8            // 채우기 불투명도
    });

    sltRectangle.push(rectangle);

    // 지도에 사각형을 표시합니다
    rectangle.setMap(map);
    // 영역 선택 기능 끔
    useMarker(false);

    // 타원체와 선택한 위경도를 통해 건물 정보 조회
    scope.getBuildingPnuList(testPosition);


}

//---------------------장소 검색 기능---------------------------//

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places();

// 키워드 검색을 요청하는 함수입니다
function searchPlaces() {

    var keyword = document.getElementById('keyword').value;

    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        alert('검색할 키워드를 입력해주세요!');
        return false;
    }

    // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
    ps.keywordSearch(keyword, placesSearchCB);
}


// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {

        if(scope.map.getUseCheck()===true){
            deleteMarkers();
        }else{
            deleteMarkers();
            useMarker(false);
            document.getElementById('useMarker').style.display = "inline-block";
            document.getElementById('resetMarker').style.display = "none";
        }

        // 장소검색이 완료되면 해당 장소로 중심좌표 이동
        if(data.length > 0){
            panTo(data[0].y, data[0].x);
        }

    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert('검색 결과가 존재하지 않습니다.');
        return;
    } else if (status === kakao.maps.services.Status.ERROR) {
        alert('검색 결과 중 오류가 발생했습니다.');
        return;
    }
}

// 영역 선택기능 사용 유무 설정
function useMarker(value){
    scope.useChange(value);
    if(value === true){
        document.getElementById('useMarker').style.display = "none";
        document.getElementById('resetMarker').style.display = "inline-block";
    }
}