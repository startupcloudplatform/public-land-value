package com.crossent.microservice.plvback.dto;

public class BuildingValue {

    private String name;
    private String dong;
    private Integer stdYear;
    private Double latitude;
    private Double longitude;
    private Long avgValue;
    private Long totalValue;
    private Long unitArValue;

    public BuildingValue(String name, String dong,
                         Integer stdYear, Double latitude,
                         Double longitude, Long avgValue,
                         Long totalValue, Long unitArValue) {
        this.name = name;
        this.dong = dong;
        this.stdYear = stdYear;
        this.latitude = latitude;
        this.longitude = longitude;
        this.avgValue = avgValue;
        this.totalValue = totalValue;
        this.unitArValue = unitArValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public Integer getStdYear() {
        return stdYear;
    }

    public void setStdYear(Integer stdYear) {
        this.stdYear = stdYear;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(Long avgValue) {
        this.avgValue = avgValue;
    }

    public Long getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Long totalValue) {
        this.totalValue = totalValue;
    }

    public Long getUnitArValue() {
        return unitArValue;
    }

    public void setUnitArValue(Long unitArValue) {
        this.unitArValue = unitArValue;
    }
}
