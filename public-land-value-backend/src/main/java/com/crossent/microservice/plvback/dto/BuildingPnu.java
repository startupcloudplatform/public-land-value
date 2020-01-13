package com.crossent.microservice.plvback.dto;

public class BuildingPnu {

    private String name;
    private String dong;
    private String pnu;
    private Integer stdYear;

    public BuildingPnu() {}

    public BuildingPnu(String name, String dong, String pnu, Integer stdYear) {
        this.name = name;
        this.dong = dong;
        this.pnu = pnu;
        this.stdYear = stdYear;
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

    public String getPnu() {
        return pnu;
    }

    public void setPnu(String pnu) {
        this.pnu = pnu;
    }

    public Integer getStdYear() {
        return stdYear;
    }

    public void setStdYear(Integer stdYear) {
        this.stdYear = stdYear;
    }
}
