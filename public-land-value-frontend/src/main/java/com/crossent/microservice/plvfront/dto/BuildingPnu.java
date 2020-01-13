package com.crossent.microservice.plvfront.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BuildingPnu {

    private String name;
    private String dong;
    private String pnu;
    private int stdYear;

//    public BuildingPnu() {}
//
//    public BuildingPnu(String name, String dong, String pnu, int stdYear) {
//        this.name = name;
//        this.dong = dong;
//        this.pnu = pnu;
//        this.stdYear = stdYear;
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
//    public String getPnu() {
//        return pnu;
//    }
//
//    public void setPnu(String pnu) {
//        this.pnu = pnu;
//    }
//
//    public int getStdYear() {
//        return stdYear;
//    }
//
//    public void setStdYear(int stdYear) {
//        this.stdYear = stdYear;
//    }
}
