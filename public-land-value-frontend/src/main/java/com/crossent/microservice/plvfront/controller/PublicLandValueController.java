package com.crossent.microservice.plvfront.controller;

import com.crossent.microservice.plvfront.dto.*;



import com.crossent.microservice.plvfront.service.PublicLandValueService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/api/pblntf")
public class PublicLandValueController {

    private static Logger logger = LoggerFactory.getLogger(PublicLandValueController.class);

    @Autowired
    private PublicLandValueService publicLandValueService;

    @ApiOperation("타원체와 위경도를 통한 건물 정보 조회 API")
    @RequestMapping(value = "/pnu/building", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<BuildingPnu> buildingPnuList(@RequestParam(name="inputEllipsoid", required = false) final String inputEllipsoid,
                                  @RequestParam(name="lat1") final Double lat1,
                                  @RequestParam(name="lng1") final Double lng1,
                                  @RequestParam(name="lat2") final Double lat2,
                                  @RequestParam(name="lng2") final Double lng2){

        return publicLandValueService.buildingPnuList(inputEllipsoid, lat1, lng1, lat2, lng2);
    }

    @ApiOperation("pnu를 통한 건물 정보 조회 API")
    @RequestMapping(value = "/value/building", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<BuildingValue> searchBuildingValueByPnu(@RequestParam(name="pnu") final String pnu){

        return publicLandValueService.searchBuildingValueByPnu(pnu);
    }

    @ApiOperation("pnu를 통한 평당 가격 정보 조회 API")
    @RequestMapping(value = "/value/building/unit", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<UnitValue> searchUnitValueByPnu(@RequestParam(name="pnu") final String pnu){

        return publicLandValueService.searchUnitValueByPnu(pnu);
    }

}
