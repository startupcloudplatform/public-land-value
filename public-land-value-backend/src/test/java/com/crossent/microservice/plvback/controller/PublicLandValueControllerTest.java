package com.crossent.microservice.plvback.controller;

import com.crossent.microservice.plvback.service.PublicLandValueService;
import com.crossent.microservice.plvback.service.RequestService;
import com.crossent.microservice.plvback.configuration.TestConfiguration;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PublicLandValueControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    private PublicLandValueController publicLandValueController;

    @Mock
    private PublicLandValueService publicLandValueService;

    @Value("${pblntf.api.key:}")
    private String apiAuthKey;

    @Value("${pblntf.api.uri:}")
    private String uriPblNtf;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(publicLandValueController).build();
    }

    @Test
    public void buildingPnuList() throws Exception {
        when(publicLandValueService.searchBuildingPnuByTwoLocation(
                TestConfiguration.ELLIPSOID_NAME,
                TestConfiguration.LAT1,
                TestConfiguration.LNG1,
                TestConfiguration.LAT2,
                TestConfiguration.LNG2
        )).thenReturn(TestConfiguration.BUILDING_PNU_LISTS);

        mockMvc.perform(
                get("http://plv-back-landtesttwo.paastaxpert.co.kr/api/public/pnu/building?inputEllipsoid="
                        +TestConfiguration.ELLIPSOID_NAME
                                +"&lat1="+TestConfiguration.LAT1
                                +"&lng1="+TestConfiguration.LNG1
                        +"&lat2="+TestConfiguration.LAT2
                        +"&lng2="+TestConfiguration.LNG2).contentType(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andDo(print());
    }

    @Test
    public void searchBuildingValueByPnu() throws Exception {
        when(publicLandValueService.searchBuildingValueByPnu(
                TestConfiguration.PNU
        )).thenReturn(TestConfiguration.BUILDING_VALUE_LISTS);

        mockMvc.perform(
                get("http://plv-back-landtesttwo.paastaxpert.co.kr/api/public/value/building?pnu="
                        +TestConfiguration.PNU).contentType(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andDo(print());
    }

    @Test
    public void searchUnitValueByPnu() throws Exception {
        when(publicLandValueService.searchUnitValueByPnu(
                TestConfiguration.PNU
        )).thenReturn(TestConfiguration.BUILDING_UNIT_VALUE_LISTS);
        mockMvc.perform(
                get("http://plv-back-landtesttwo.paastaxpert.co.kr/api/public/value/building/unit?pnu="
                        +TestConfiguration.PNU).contentType(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andDo(print());
    }
}