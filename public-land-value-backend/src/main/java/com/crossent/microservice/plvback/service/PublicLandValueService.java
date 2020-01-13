package com.crossent.microservice.plvback.service;

import com.crossent.microservice.plvback.dto.*;

import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeoutException;

@Service
@RefreshScope
public class PublicLandValueService {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Value("${gateway.basic.user:}")
    String user;

    @Value("${gateway.basic.password:}")
    String password;

    @Value("${pblntf.api.key:}")
    private String apiAuthKey;

    @Value("${pblntf.api.uri:}")
    private String uriPblNtf;

    private String uriTransCrs = "http://transform-crs/api/crs/transform/";

    @Autowired
    RequestService requestService;

    enum Exeption{
        nullExeption(-1),
        timeout(-2);

        private Integer codeNum;
        Exeption(Integer code) {
            codeNum = code;
        }

        public Integer getCodeNum() {
            return codeNum;
        }
    }
    // uri 정의하기
    public URI setURI(String uri, MultiValueMap<String, String> maps) {
        return UriComponentsBuilder.fromHttpUrl(uri)
                .queryParams(maps)
                .build()
                .encode()
                .toUri();
    }

    // header 정의
    private HttpHeaders getHeaders(){
        String basicAuth = String.format("%s:%s", user, password);
        String base64Auth = Base64Utils.encodeToString(basicAuth.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", String.format("Basic %s", base64Auth));
        return headers;
    }

    // transform-crs로부터 json형태로 데이터 받기(restTemplate 이용)
    public JSONObject getJsonResponse(URI uri) {
        HttpEntity request = new HttpEntity(getHeaders());
        JSONObject responseBody = null;
        try {
            ResponseEntity<JSONObject> response = restTemplate.exchange(uri, HttpMethod.GET, request, JSONObject.class);
            responseBody = response.getBody();
            System.out.println(responseBody);
        } catch(Exception e) {
            System.out.println("<== getJsonResponse Function Exeption Error ==>");
            e.printStackTrace();
        }
        return responseBody;
    }

    // 두점을 통해 빌딩 찾기
    public List<BuildingPnu> searchBuildingPnuByTwoLocation(String inputEllipsoid, Double lat1, Double lng1, Double lat2, Double lng2) {
        // 위경도를 xy좌표로 변환 -> bbox형태로 담기
        TwoDimCrs twoDimCrs1 = transCrsToTwoDim(inputEllipsoid, lat1, lng1);
        TwoDimCrs twoDimCrs2 = transCrsToTwoDim(inputEllipsoid, lat2, lng2);
        BboxLocation bboxLocation = new BboxLocation(twoDimCrs1.getX(), twoDimCrs1.getY(),
                twoDimCrs2.getX(), twoDimCrs2.getY());

        // 호출할 uri 만들기
        MultiValueMap<String, String> maps = new LinkedMultiValueMap<>();
        maps.add("auth_key", apiAuthKey);
        maps.add("bbox", bboxLocation.toString());
        URI uri = setURI(uriPblNtf, maps);

        // Uri 호출 후 응답 받음.
        String response = requestService.getHttpAsString(uri.toString());
        List<OpenApiResponseData> openApiResponseDataList = getF163List(response);

        // 응답 받은 데이터를 DTO에 담기
        // pnu가 동일할 경우엔 pnu에 담지 않음.
        Set<String> pnuSet = new HashSet<>();
        List<BuildingPnu> buildingPnuList = new ArrayList<>();
        for(OpenApiResponseData data : openApiResponseDataList) {
            if (!pnuSet.contains(data.getPnu())) {
                pnuSet.add(data.getPnu());
                BuildingPnu buildingPnu = new BuildingPnu(
                        data.getName(), data.getDong(),
                        data.getPnu(), data.getYear()
                );
                buildingPnuList.add(buildingPnu);
            }
        }
        System.out.println(">>> PNU SET SIZE >>>" + pnuSet.size());
        System.out.println(">>> BuildingPNU LIST SIZE >>>" + buildingPnuList.size());
        return buildingPnuList;
    }

    // pnu를 통해 빌딩 찾기
    public List<BuildingValue> searchBuildingValueByPnu(String pnu) {
        // 호출할 uri 만들기
        MultiValueMap<String, String> maps = new LinkedMultiValueMap<>();
        maps.add("auth_key", apiAuthKey);
        maps.add("pnu", pnu);
        URI uri = setURI(uriPblNtf, maps);

        // uri 호출 후 응답받음.
        System.out.println("<== ValueByPnu ==>");
        String response = requestService.getHttpAsString(uri.toString());
        List<OpenApiResponseData> openApiResponseDataList = getF163List(response);

        // 응답 받은 데이터를 DTO에 담기
        List<BuildingValue> buildingValueList = new ArrayList<>();
        for(OpenApiResponseData data : openApiResponseDataList) {
            ThreeDimCrs threeDimCrs = transCrsToThreeDim("epsg5174", data.getX(), data.getY());
            BuildingValue buildingValue = new BuildingValue(
                    data.getName(), data.getDong(),
                    data.getYear(), threeDimCrs.getLat(), threeDimCrs.getLng(),
                    data.getAvrPublicValue(), data.getTotalPublicValue(), data.getUnitArValue()
            );
            buildingValueList.add(buildingValue);
        }
        return buildingValueList;
    }

    // pnu를 통해 UnitValue 받아오기
    public List<UnitValue> searchUnitValueByPnu(String pnu) {
        // 호출할 uri 만들기
        MultiValueMap<String, String> maps = new LinkedMultiValueMap<>();
        maps.add("auth_key", apiAuthKey);
        maps.add("pnu", pnu);
        URI uri = setURI(uriPblNtf, maps);

        // uri 호출 후 응답받음.
        System.out.println("<== UnitValueByPnu ==>");
        String response = requestService.getHttpAsString(uri.toString());
        List<OpenApiResponseData> openApiResponseDataList = getF163List(response);

        // 응답 받은 데이터를 DTO에 담기
        List<UnitValue> unitValueList = new ArrayList<>();
        for(OpenApiResponseData data : openApiResponseDataList) {
            UnitValue unitValue = new UnitValue(
                    data.getName(), data.getDong(),
                    data.getYear(), data.getUnitArValue()
            );
            unitValueList.add(unitValue);
        }
        return unitValueList;
    }


    // 좌표 변화 API 호출 3차원 -> 2차원
    public TwoDimCrs transCrsToTwoDim(String inputEllipsoid, Double lat, Double lng) {
        if(!(inputEllipsoid.equals("bessel")||inputEllipsoid.equals("grs80"))) {
            inputEllipsoid = "wgs84";
        }
        // 호출할 uri 만들기
        MultiValueMap<String, String> maps = new LinkedMultiValueMap<>();
        maps.add("inputEllipsoid", inputEllipsoid);
        maps.add("outputEpsg", "epsg5174");
        maps.add("lat", lat.toString());
        maps.add("lng", lng.toString());
        URI uri = setURI(uriTransCrs+"xy", maps);

        // 응답받은 데이터 DTO에 담기
        JSONObject jsonResponse = getJsonResponse(uri);
        TwoDimCrs twoDimCrs = new TwoDimCrs();
        twoDimCrs.setEpsg(jsonResponse.get("epsg").toString());
        twoDimCrs.setX(Double.parseDouble(jsonResponse.get("x").toString()));
        twoDimCrs.setY(Double.parseDouble(jsonResponse.get("y").toString()));

        return twoDimCrs;
    }
    // 좌표 변환 API 호출 2차원 -> 3차원
    public ThreeDimCrs transCrsToThreeDim(String epsg, Double x, Double y) {
        if(!(epsg.equals("epsg5179")||epsg.equals("epsg5181"))) {
            epsg = "epsg5174";
        }

        // 호출할 uri 만들기
        MultiValueMap<String, String> maps = new LinkedMultiValueMap<>();
        maps.add("inputEpsg", epsg);
        maps.add("outputEllipsoid", "wgs84");
        maps.add("x", x.toString());
        maps.add("y", y.toString());

        // uri 호출 후 응답받음.
        URI uri = setURI(uriTransCrs+"latlng", maps);
        JSONObject jsonResponse = getJsonResponse(uri);

        // 응답받은 데이터 DTO에 담기
        ThreeDimCrs threeDimCrs = new ThreeDimCrs();
        threeDimCrs.setEllipsoid(jsonResponse.get("ellipsoid").toString());
        threeDimCrs.setLat(Double.parseDouble(jsonResponse.get("lat").toString()));
        threeDimCrs.setLng(Double.parseDouble(jsonResponse.get("lng").toString()));

        return threeDimCrs;
    }


    // F163내용을 uri에 따라 받아오는 함수.
    public List<OpenApiResponseData> getF163List(String response) {
        List<OpenApiResponseData> openApiResponseDataList = new ArrayList<>();
        if(response != null) {
            JSONParser simpleParser = new JSONParser();
            String xmlString = XML.toJSONObject(response).toString();
            try {
                JSONObject xmlJSONObj = (JSONObject)simpleParser.parse(xmlString);

                JSONObject featureCollection = (JSONObject)xmlJSONObj.get("wfs:FeatureCollection");

                // feature member가 Array형태인지 Object형태인지 확인하기
                JSONArray featureMembers = null;
                if (featureCollection.get("gml:featureMember") instanceof JSONArray) {
                    featureMembers = (JSONArray)featureCollection.get("gml:featureMember");
                } else {
                    featureMembers = new JSONArray();
                    featureMembers.add(featureCollection.get("gml:featureMember"));
                }

                for(int i = 0; i < featureMembers.size(); i++) {
                    JSONObject featureMember = (JSONObject)featureMembers.get(i);
                    JSONObject f163 = (JSONObject)featureMember.get("NSDI:F163");

                    String dong = f163.get("NSDI:DONG_NM").toString();
                    if(dong.contains("xsi:nil")) {
                        dong = "";
                    }
                    OpenApiResponseData openApiResponseData = new OpenApiResponseData(f163.get("NSDI:APHUS_NM").toString(),
                            dong, Integer.parseInt(f163.get("NSDI:STDR_YEAR").toString()),
                            f163.get("NSDI:PNU").toString(), Double.parseDouble(f163.get("NSDI:X_CRDNT").toString()),
                            Double.parseDouble(f163.get("NSDI:Y_CRDNT").toString()), Long.parseLong(f163.get("NSDI:AVRG_PBLNTF_PC").toString()),
                            Long.parseLong(f163.get("NSDI:ALL_PBLNTF_PC").toString()), Long.parseLong(f163.get("NSDI:UNIT_AR_PC").toString())
                    );
                    openApiResponseDataList.add(openApiResponseData);
                }
            } catch (Exception e) {
                System.out.println("<== Error Occurs in FUNC getF163List(String url) ==>");
                if(e.getClass().equals(NullPointerException.class)) {
                    System.out.println("<===== null pointer exception =====>");
                    // Enum처리 해야하나...
                    OpenApiResponseData openApiResponseData = new OpenApiResponseData("건물 없음","-1" , 9999, "0", 0.0,0.0,(long)0, (long)0, (long)0);
                    openApiResponseDataList.add(openApiResponseData);
                } else if(e.getClass().equals(TimeoutException.class)) {
                    System.out.println("<===== Time Out exception =====>");
                    OpenApiResponseData openApiResponseData = new OpenApiResponseData("Time Out", "-2", 9999, "0", 0.0,0.0,(long)0, (long)0, (long)0);
                    openApiResponseDataList.add(openApiResponseData);
                } else {
                    e.printStackTrace();
                }
            }
        }
        return openApiResponseDataList;
    }
}

