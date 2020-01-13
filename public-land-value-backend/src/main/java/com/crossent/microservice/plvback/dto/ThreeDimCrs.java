package com.crossent.microservice.plvback.dto;

public class ThreeDimCrs {
    private String ellipsoid;
    private Double lat;
    private Double lng;

    public ThreeDimCrs() {
    }

    public ThreeDimCrs(String ellipsoid, Double lat, Double lng) {
        this.ellipsoid = ellipsoid;
        this.lat = lat;
        this.lng = lng;
    }

    public String getEllipsoid() {
        return ellipsoid;
    }

    public void setEllipsoid(String ellipsoid) {
        this.ellipsoid = ellipsoid;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "{" +
                "\"ellipsoid\":\"" + ellipsoid + '\"' +
                ", \"lat\":\"" + lat + '\"' +
                ", \"lng\":\"" + lng + '\"' +
                '}';
    }
}
