'use strict';

angular.module('publicLandValueApp')
    .controller('mapController', function($scope, $http, $window){

        var map = this;
        $scope.useCheck = false;
        $scope.searchList = false;
        $scope.buildingPnuList = {};

        $scope.useChange = function(value) {
            $scope.useCheck = value;
        };

        map.pageLoadData = function() {
        };

        map.getUseCheck = function() {
            return $scope.useCheck;
        };

        // 타원체와 위경도를 통한 건물 정보 조회
        $scope.getBuildingPnuList = function(positions) {

            document.getElementById('mainLoading').style.display = 'block';

            var params = {
                inputEllipsoid: 'wgs84',
                lat1: positions[0].Ha,
                lng1: positions[0].Ga,
                lat2: positions[1].Ha,
                lng2: positions[1].Ga
            };

            $http({
                method: 'GET',
                url: '/api/pblntf/pnu/building',
                params: params,
                headers: {'Content-Type': 'application/json; charset=utf-8'}

            }).then(function success(response) {
                console.log(response);
                $scope.buildingPnuList = angular.copy(response.data);

                $('#pnuResultBody').empty();

                // pnu 검색 결과가 2개 이상이라면
                if($scope.buildingPnuList.length >= 2){
                    document.getElementById('memo-txt').style.display = "block";
                }else if($scope.buildingPnuList.length == 0){
                    document.getElementById('memo-txt').style.display = "none";
                    var emptyResult = "<tr id=\"emptyResult\">\n" +
                        "<td colspan=\"3\" style=\"height: 70px; text-align: center; padding-top: 7%; background-color: #f9f9f9;\">\n" +
                        "<h4>조회 결과가 존재하지 않습니다</h4>\n" +
                        "</td>\n" +
                        "</tr>";
                    $('#pnuResultBody').append(emptyResult);
                }else if($scope.buildingPnuList.length == 1){
                    document.getElementById('memo-txt').style.display = "none";
                    if($scope.buildingPnuList[0].dong === "-1"){
                        var emptyResult = "<tr id=\"emptyResult\">\n" +
                            "<td colspan=\"3\" style=\"height: 70px; text-align: center; padding-top: 7%; background-color: #f9f9f9;\">\n" +
                            "<h4>건물 정보가 존재하지 않습니다.\n국토부에 문의하세요</h4>\n" +
                            "</td>\n" +
                            "</tr>";
                        $('#pnuResultBody').append(emptyResult);
                    }else if($scope.buildingPnuList[0].dong === "-2"){
                        var emptyResult = "<tr id=\"emptyResult\">\n" +
                            "<td colspan=\"3\" style=\"height: 70px; text-align: center; padding-top: 7%; background-color: #f9f9f9;\">\n" +
                            "<h4>건물 정보 데이터를 불러오는 데 많은 시간이 소요됩니다. 잠시 후 다시 시도해 주세요</h4>\n" +
                            "</td>\n" +
                            "</tr>";
                        $('#pnuResultBody').append(emptyResult);
                    }
                }

                //결과 pnu 리스트 생성
                for(var i=0;i<$scope.buildingPnuList.length;i++){

                    if($scope.buildingPnuList[0].dong != "-1" && $scope.buildingPnuList[0].dong != "-2" ){
                        var temp = $scope.buildingPnuList[i].pnu+"";

                        var row = "<tr><td>"+(i+1)+"</td>" +
                            "<td class='test-left'>"+$scope.buildingPnuList[i].name+"</td>" +
                            "<td><a onclick='makeValueList("+temp.substring(0,10)+","+temp.substring(10,temp.length)+")' class='color-blue' style='cursor: pointer'>선택하기</a></td></tr>";

                        $('#pnuResultBody').append(row);
                    }
                }

                document.getElementById('modal-content').style.display = "block";
                document.getElementById("valueList").style.display = "none";
                document.getElementById("pnuList").style.display = "block";

                document.getElementById('mainLoading').style.display = 'none';

            }, function error(response) {
                alert("error!");
                document.getElementById('mainLoading').style.display = 'none';

            });
        };

        map.pageLoadData();

    });