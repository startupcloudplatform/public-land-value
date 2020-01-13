package com.crossent.microservice.plvback.dto;

public class UnitValue {
    private String name;
    private String dong;
    private Integer stdYear;
    private Long unitArValue;

    public UnitValue(String name, String dong, Integer stdYear, Long unitArValue) {
        this.name = name;
        this.dong = dong;
        this.stdYear = stdYear;
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

    public Long getUnitArValue() {
        return unitArValue;
    }

    public void setUnitArValue(Long unitArValue) {
        this.unitArValue = unitArValue;
    }
}
