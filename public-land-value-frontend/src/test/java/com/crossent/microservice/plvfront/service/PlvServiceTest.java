package com.crossent.microservice.plvfront.service;

import com.crossent.microservice.plvfront.PlvFrontApplication;
import com.crossent.microservice.plvfront.configuration.TestConfiguration;
import com.crossent.microservice.plvfront.dto.BuildingPnu;
import com.crossent.microservice.plvfront.dto.BuildingValue;
import com.crossent.microservice.plvfront.dto.UnitValue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlvFrontApplication.class)
public class PlvServiceTest {

    MockMvc mockMvc;

    @InjectMocks
    private PublicLandValueService publicLandValueService;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(publicLandValueService).build();
    }

    @Test
    public void buildingPnuList(){

        Mockito.when(restTemplate.exchange(
//                Matchers.eq("http://apigateway/plv-back/api/public/value/building?pnu"+TestConfiguration.QUERY_PNU),
//                Matchers.anyString(),
                Matchers.anyObject(),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))
        ).thenReturn(new ResponseEntity(TestConfiguration.setBuildingPnuData(), HttpStatus.OK));

        List<BuildingPnu> result = publicLandValueService.buildingPnuList(TestConfiguration.QUERY_ELLIPSOID, TestConfiguration.QUERY_LATITUDE1, TestConfiguration.QUERY_LONGITUDE1,
                TestConfiguration.QUERY_LATITUDE2, TestConfiguration.QUERY_LONGITUDE2);
        Assert.assertEquals(result, TestConfiguration.setBuildingPnuData());
        Assert.assertFalse(result.isEmpty());

    }

    @Test
    public void searchBuildingValueByPnu() {
        Mockito.when(restTemplate.exchange(
                Matchers.any(),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))
        ).thenReturn(new ResponseEntity(TestConfiguration.setBuildingValueData(), HttpStatus.OK));

        List<BuildingValue> result = publicLandValueService.searchBuildingValueByPnu(TestConfiguration.QUERY_PNU);
        Assert.assertEquals(result, TestConfiguration.setBuildingValueData());
        Assert.assertFalse(result.isEmpty());
    }

    @Test
    public void searchUnitValueByPnu(){

        Mockito.when(restTemplate.exchange(
                Matchers.any(),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))
        ).thenReturn(new ResponseEntity(TestConfiguration.setUnitValueData(), HttpStatus.OK));

        List<UnitValue> result = publicLandValueService.searchUnitValueByPnu(TestConfiguration.QUERY_PNU);
        Assert.assertEquals(result, TestConfiguration.setUnitValueData());
        Assert.assertFalse(result.isEmpty());
    }
}
