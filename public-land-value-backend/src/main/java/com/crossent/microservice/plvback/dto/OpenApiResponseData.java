package com.crossent.microservice.plvback.dto;

public class OpenApiResponseData {
    private String name;
    private String dong;
    private Integer year;
    private String pnu;

    private Double x;
    private Double y;

    private Long avrPublicValue;
    private Long totalPublicValue;
    private Long unitArValue;

    public OpenApiResponseData(String name, String dong, Integer year, String pnu, Double x, Double y, Long avrPublicValue, Long totalPublicValue, Long unitArValue) {
        this.name = name;
        this.dong = dong;
        this.year = year;
        this.pnu = pnu;
        this.x = x;
        this.y = y;
        this.avrPublicValue = avrPublicValue;
        this.totalPublicValue = totalPublicValue;
        this.unitArValue = unitArValue;
    }

    public String getName() {
        return name;
    }

    public String getDong() {
        return dong;
    }

    public Integer getYear() {
        return year;
    }

    public String getPnu() {
        return pnu;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Long getAvrPublicValue() {
        return avrPublicValue;
    }

    public Long getTotalPublicValue() {
        return totalPublicValue;
    }

    public Long getUnitArValue() {
        return unitArValue;
    }
}
