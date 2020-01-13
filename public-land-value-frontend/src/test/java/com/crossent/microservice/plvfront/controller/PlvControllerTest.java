package com.crossent.microservice.plvfront.controller;

import com.crossent.microservice.plvfront.configuration.TestConfiguration;
import com.crossent.microservice.plvfront.service.PublicLandValueService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlvControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    private PublicLandValueController publicLandValueController;

    @Mock
    private PublicLandValueService publicLandValueService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(publicLandValueController).build();
    }

    @Test
    public void buildingPnuList() throws Exception {

        when(publicLandValueService.buildingPnuList(TestConfiguration.QUERY_ELLIPSOID,TestConfiguration.QUERY_LATITUDE1,TestConfiguration.QUERY_LONGITUDE1,
                TestConfiguration.QUERY_LATITUDE2,TestConfiguration.QUERY_LONGITUDE2)).thenReturn(TestConfiguration.setBuildingPnuData());

        mockMvc.perform(get("/api/pblntf/pnu/building?inputEllipsoid="+TestConfiguration.QUERY_ELLIPSOID+"&lat1="+TestConfiguration.QUERY_LATITUDE1
                +"&lng1="+TestConfiguration.QUERY_LONGITUDE1+"&lat2="+TestConfiguration.QUERY_LATITUDE2+"&lng2="+TestConfiguration.QUERY_LONGITUDE2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }


    @Test
    public void searchBuildingValueByPnu() throws Exception {

        when(publicLandValueService.searchBuildingValueByPnu(TestConfiguration.QUERY_PNU)).thenReturn(TestConfiguration.setBuildingValueData());

        mockMvc.perform(get("/api/pblntf/value/building?pnu="+TestConfiguration.QUERY_PNU).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }


    @Test
    public void searchUnitValueByPnu() throws Exception {

        when(publicLandValueService.searchUnitValueByPnu(TestConfiguration.QUERY_PNU)).thenReturn(TestConfiguration.setUnitValueData());

        mockMvc.perform(get("/api/pblntf/value/building/unit?pnu="+TestConfiguration.QUERY_PNU).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

}