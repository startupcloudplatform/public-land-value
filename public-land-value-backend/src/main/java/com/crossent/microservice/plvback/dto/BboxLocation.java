package com.crossent.microservice.plvback.dto;

public class BboxLocation {
    private Double x1;
    private Double y1;
    private Double x2;
    private Double y2;

    private String epsg ;

    public BboxLocation(Double x1, Double y1, Double x2, Double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.epsg = "EPSG:5174";
    }

    public Double getX1() {
        return x1;
    }

    public Double getY1() {
        return y1;
    }

    public Double getX2() {
        return x2;
    }

    public Double getY2() {
        return y2;
    }

    @Override
    public String toString() {
        return x1 + "," +
                y1 + "," +
                x2 + "," +
                y2 + "," +
                epsg;
    }
}
