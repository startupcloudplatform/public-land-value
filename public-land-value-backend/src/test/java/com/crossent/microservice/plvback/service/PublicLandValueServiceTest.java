package com.crossent.microservice.plvback.service;

import com.crossent.microservice.plvback.configuration.TestConfiguration;
import okhttp3.HttpUrl;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class PublicLandValueServiceTest {
    MockMvc mockMvc;

    @InjectMocks
    private PublicLandValueService publicLandValueService;

    @Mock
    private RequestService requestService;

    @Mock
    private RestTemplate restTemplate;


    @Value("${pblntf.api.key:}")
    private String apiAuthKey;

    //private String apiAuthKey = "0543fgrib0ggh48ou4rtbgad1ov8820n8";


    @Value("${pblntf.api.uri:}")
    private String uriPblNtf;

    //private String uriPblNtf = "http://182.252.131.40:9000/apiservice/1814";

    private String uriTransCrs = "http://transform-crs/api/crs/transform/";

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(publicLandValueService).build();
    }

    @Test
    public void testSearchBuildingPnuByTwoLocation() {
        String url = uriPblNtf
                +"?auth_key="+apiAuthKey
                +"&bbox="+TestConfiguration.BBOX;

        when(requestService.getHttpAsString(url
        )).thenReturn(TestConfiguration.OPEN_API_RESPONSE);

        assertEquals(TestConfiguration.OPEN_API_REPONSE_DATA_LIST.size() ,
                publicLandValueService.getF163List(TestConfiguration.OPEN_API_RESPONSE).size());
    }

    @Test
    public void testSearchBuildingValueByPnu() {
        String url = uriPblNtf
                +"?auth_key="+apiAuthKey
                +"&pnu="+TestConfiguration.PNU;
        when(requestService.getHttpAsString(url
        )).thenReturn(TestConfiguration.OPEN_API_RESPONSE);

        assertEquals(TestConfiguration.OPEN_API_REPONSE_DATA_LIST.size() ,
                publicLandValueService.getF163List(TestConfiguration.OPEN_API_RESPONSE).size());
    }

    @Test
    public void testSearchUnitValueByPnu() {
        String url = uriPblNtf
                +"?auth_key="+apiAuthKey
                +"&pnu="+TestConfiguration.PNU;
        when(requestService.getHttpAsString(url
        )).thenReturn(TestConfiguration.OPEN_API_RESPONSE);

        assertEquals(TestConfiguration.OPEN_API_REPONSE_DATA_LIST.size() ,
                publicLandValueService.getF163List(TestConfiguration.OPEN_API_RESPONSE).size());
        /*
        String url = uriPblNtf
                +"?auth_key="+apiAuthKey
                +"&pnu="+TestConfiguration.PNU;

        when(requestService.getHttpAsString(url
        )).thenReturn(TestConfiguration.OPEN_API_RESPONSE);

        when(publicLandValueService.transCrsToThreeDim(
                Matchers.any(),
                Matchers.any(),
                Matchers.any()
                )
        );
        //초기 주입
        ReflectionTestUtils.setField(publicLandValueService, "apiAuthKey", apiAuthKey);
        ReflectionTestUtils.setField(publicLandValueService, "uriPblNtf", uriPblNtf);

        assertEquals(TestConfiguration.OPEN_API_REPONSE_DATA_LIST,
                publicLandValueService.searchUnitValueByPnu(TestConfiguration.PNU));
        */
    }

    @Test
    public void testTransCrsToTwoDim() throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(TestConfiguration.TWODIM_LOCATION.toString());
        when(restTemplate.exchange(Matchers.any(),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))).thenReturn(new ResponseEntity(obj, HttpStatus.OK));
        ReflectionTestUtils.setField(publicLandValueService, "uriTransCrs", uriTransCrs);


        assertEquals(TestConfiguration.EPSG_NAME, publicLandValueService.
                transCrsToTwoDim(TestConfiguration.THREEDIM_LOCATION.getEllipsoid(),
                        TestConfiguration.THREEDIM_LOCATION.getLat(),
                        TestConfiguration.THREEDIM_LOCATION.getLng()).getEpsg()
        );
    }

    @Test
    public void testTransCrsToThreeDim() throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(TestConfiguration.THREEDIM_LOCATION.toString());
        when(restTemplate.exchange(Matchers.any(),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))).thenReturn(new ResponseEntity(obj, HttpStatus.OK));
        ReflectionTestUtils.setField(publicLandValueService, "uriTransCrs", uriTransCrs);


        assertEquals(TestConfiguration.ELLIPSOID_NAME, publicLandValueService.
                transCrsToThreeDim(TestConfiguration.TWODIM_LOCATION.getEpsg(),
                        TestConfiguration.TWODIM_LOCATION.getX(),
                        TestConfiguration.TWODIM_LOCATION.getY()).getEllipsoid()
        );

    }

    @Test
    public void testGetF163List() {

        String url = uriPblNtf
                +"?auth_key="+apiAuthKey
                +"&pnu="+TestConfiguration.PNU;

        when(requestService.getHttpAsString(url
        )).thenReturn(TestConfiguration.OPEN_API_RESPONSE);

        assertEquals(TestConfiguration.OPEN_API_REPONSE_DATA_LIST.size() ,publicLandValueService.getF163List(TestConfiguration.OPEN_API_RESPONSE).size());
    }


}