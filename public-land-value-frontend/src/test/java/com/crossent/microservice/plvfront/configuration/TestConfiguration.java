package com.crossent.microservice.plvfront.configuration;

import com.crossent.microservice.plvfront.dto.BuildingPnu;
import com.crossent.microservice.plvfront.dto.BuildingValue;
import com.crossent.microservice.plvfront.dto.UnitValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestConfiguration {

    public static String QUERY_ELLIPSOID = "wgs84";
    public static Double QUERY_LATITUDE1 = 37.504188426869256;
    public static Double QUERY_LONGITUDE1 = 126.99429996468722;
    public static Double QUERY_LATITUDE2 = 37.50072865678614;
    public static Double QUERY_LONGITUDE2 = 127.00327963088922;
    public static String QUERY_PNU = "1165010700100180001";


    public static List<BuildingPnu> setBuildingPnuData(){

        List<BuildingPnu> result = new ArrayList<>();
        result.add(new BuildingPnu("한신15차", "46", "1165010700100120000", 2019));
        result.add(new BuildingPnu("반포푸르지오", "102", "1165010700100100000", 2019));
        result.add(new BuildingPnu("래미안퍼스티지", "105", "1165010700100180001", 2019));
        result.add(new BuildingPnu("반포 힐스테이트", "105", "1165010700100160006", 2019));

        return result;
    }


    public static List<BuildingValue> setBuildingValueData(){

        List<BuildingValue> result = new ArrayList<>();
        result.add(new BuildingValue("래미안퍼스티지", "101", 2019, 37.502395156533744, 126.99597025536175,
                Long.parseLong("1656405063"), Long.parseLong("13085600000"), Long.parseLong("19503180")));
        result.add(new BuildingValue("래미안퍼스티지", "102", 2019, 37.502539523084124, 126.99646257949561,
                Long.parseLong("1660329412"), Long.parseLong("141128000000"), Long.parseLong("19549387")));
        result.add(new BuildingValue("래미안퍼스티지", "103", 2019, 37.503119809556374, 126.99642625775022,
                Long.parseLong("1649742574"), Long.parseLong("166624000000"), Long.parseLong("19453467")));
        result.add(new BuildingValue("래미안퍼스티지", "104", 2019, 37.50325921635061, 126.99709305849504,
                Long.parseLong("1253559322"), Long.parseLong("147920000000"), Long.parseLong("20918597")));

        return result;
    }


    public static List<UnitValue> setUnitValueData(){

        List<UnitValue> result = new ArrayList<>();
        result.add(new UnitValue("래미안퍼스티지", "101", 2019, Long.parseLong("19503180")));
        result.add(new UnitValue("래미안퍼스티지", "102", 2019, Long.parseLong("19549387")));
        result.add(new UnitValue("래미안퍼스티지", "103", 2019, Long.parseLong("19453467")));
        result.add(new UnitValue("래미안퍼스티지", "104", 2019, Long.parseLong("20918597")));

        return result;
    }

    // spring security가 적용되어 있기 때문에 http 요청 시, header정보를 반드시 함께 전달해야함
    public static HttpHeaders getHeaders(){
        String basicAuth = String.format("%s:%s", "user", "secret");
        String base64Auth = Base64Utils.encodeToString(basicAuth.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Basic %s", base64Auth));
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return headers;
    }

}