package com.crossent.microservice.plvfront.service;

import com.crossent.microservice.plvfront.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.*;

@Service
@RefreshScope
public class PublicLandValueService {
    private static Logger logger = LoggerFactory.getLogger(PublicLandValueService.class);

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Value("${gateway.basic.user:}")
    private String user;

    @Value("${gateway.basic.password:}")
    private String password;

    // spring security가 적용되어 있기 때문에 http 요청 시, header정보를 반드시 함께 전달해야함
    private HttpHeaders getHeaders(){
        final String basicAuth = String.format("%s:%s", user, password);
        final String base64Auth = Base64Utils.encodeToString(basicAuth.getBytes());

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Basic %s", base64Auth));
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        postConstruct();

        return headers;
    }

    @PostConstruct
    private void postConstruct() {
        System.out.println(">>Basic Auth user/password: " + user + "/" + password);
    }


    /**
     * 타원체와 위경도를 통한 건물 정보 조회
     * @param inputEllipsoid
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public List<BuildingPnu> buildingPnuList(final String inputEllipsoid, final Double lat1, final Double lng1, final Double lat2, final Double lng2){

        final HttpEntity<String> request = new HttpEntity<>(getHeaders());
        URI uri = URI.create("http://apigateway/plv-back/api/public/pnu/building");

        try{
            final UriComponents requestUri = UriComponentsBuilder.fromHttpUrl(String.valueOf(uri)).
                    queryParam("inputEllipsoid", inputEllipsoid).
                    queryParam("lat1", lat1).
                    queryParam("lng1", lng1).
                    queryParam("lat2", lat2).
                    queryParam("lng2", lng2).build().encode("UTF-8");
            uri = requestUri.toUri();
        }catch(Exception e){
            e.printStackTrace();
        }

        final ResponseEntity<Object> result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);

        return (List<BuildingPnu>)result.getBody();
    }

    /**
     * pnu를 통한 건물 정보 조회
     * @param pnu
     * @return
     */
    public List<BuildingValue> searchBuildingValueByPnu(final String pnu){

        final HttpEntity<String> request = new HttpEntity<>(getHeaders());
        URI uri = URI.create("http://apigateway/plv-back/api/public/value/building");

        try{
            final UriComponents requestUri = UriComponentsBuilder.fromHttpUrl(String.valueOf(uri)).
                    queryParam("pnu", pnu).build().encode("UTF-8");
            uri = requestUri.toUri();
        }catch(Exception e){
            e.printStackTrace();
        }

        final ResponseEntity<Object> result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);

        return (List<BuildingValue>)result.getBody();
    }


    /**
     * pnu를 통한 평당 가격 정보 조회
     * @param pnu
     * @return
     */
    public List<UnitValue> searchUnitValueByPnu(final String pnu) {

        final HttpEntity<String> request = new HttpEntity<>(getHeaders());
        URI uri = URI.create("http://apigateway/plv-back/api/public/value/building/unit");

        try{
            final UriComponents requestUri = UriComponentsBuilder.fromHttpUrl(String.valueOf(uri)).
                    queryParam("pnu", pnu).build().encode("UTF-8");
            uri = requestUri.toUri();
        }catch(Exception e){
            e.printStackTrace();
        }

        final ResponseEntity<Object> result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);

        return (List<UnitValue>)result.getBody();
    }
}
