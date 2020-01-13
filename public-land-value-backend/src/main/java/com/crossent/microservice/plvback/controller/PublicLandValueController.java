package com.crossent.microservice.plvback.controller;

import com.crossent.microservice.plvback.dto.BuildingPnu;
import com.crossent.microservice.plvback.dto.BuildingValue;
import com.crossent.microservice.plvback.dto.UnitValue;
import com.crossent.microservice.plvback.service.PublicLandValueService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicLandValueController {

    @Autowired
    PublicLandValueService publicLandValueService;

    @ApiOperation("두 점을 통한 위경도 조회 API")
    @GetMapping(value = "/pnu/building")
    @ResponseStatus(HttpStatus.OK)
    public List<BuildingPnu> buildingPnuList(@RequestParam(name="inputEllipsoid", required = false) String inputEllipsoid,
                                                     @RequestParam(name="lat1", required = true) Double lat1,
                                                     @RequestParam(name="lng1", required = true) Double lng1,
                                                     @RequestParam(name="lat2", required = true) Double lat2,
                                                     @RequestParam(name="lng2", required = true) Double lng2){

        //return publicLandValueService.buildingPnuList(inputEllipsoid, lat1, lng1, lat2, lng2);
        return publicLandValueService.searchBuildingPnuByTwoLocation(inputEllipsoid, lat1, lng1, lat2, lng2);
    }


    @ApiOperation("pnu를 통한 건물 가격 정보 조회 API")
    @GetMapping(value = "/value/building")
    @ResponseStatus(HttpStatus.OK)
    public List<BuildingValue> searchBuildingValueByPnu(@RequestParam(name="pnu") String pnu){

        //pnu = "1165010700100180002";
        return publicLandValueService.searchBuildingValueByPnu(pnu);
    }

    @ApiOperation("pnu를 통한 평당 가격 정보 조회 API")
    @GetMapping(value = "/value/building/unit")
    @ResponseStatus(HttpStatus.OK)
    public List<UnitValue> searchUnitValueByPnu(@RequestParam(name="pnu") String pnu){

        //pnu = "1165010700100180002";
        return publicLandValueService.searchUnitValueByPnu(pnu);
    }



}
