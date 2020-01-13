package com.crossent.microservice.plvfront.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BuildingValue {

    private String name;
    private String dong;
    private Integer stdYear;
    private Double latitude;
    private Double longitude;
    private Long avgValue;
    private Long totalValue;
    private Long unitArValue;

//    public BuildingValue() {}
//
//    public BuildingValue(String name, String dong, int stdYear, double latitude, double longitude, long avgValue, long totalValue, long unitArValue) {
//        this.name = name;
//        this.dong = dong;
//        this.stdYear = stdYear;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.avgValue = avgValue;
//        this.totalValue = totalValue;
//        this.unitArValue = unitArValue;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDong() {
//        return dong;
//    }
//
//    public void setDong(String dong) {
//        this.dong = dong;
//    }
//
//    public int getStdYear() {
//        return stdYear;
//    }
//
//    public void setStdYear(int stdYear) {
//        this.stdYear = stdYear;
//    }
//
//    public double getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }
//
//    public double getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//    }
//
//    public Long getAvgValue() {
//        return avgValue;
//    }
//
//    public void setAvgValue(Long avgValue) {
//        this.avgValue = avgValue;
//    }
//
//    public Long getTotalValue() {
//        return totalValue;
//    }
//
//    public void setTotalValue(Long totalValue) {
//        this.totalValue = totalValue;
//    }
//
//    public Long getUnitArValue() {
//        return unitArValue;
//    }
//
//    public void setUnitArValue(Long unitArValue) {
//        this.unitArValue = unitArValue;
//    }
}
