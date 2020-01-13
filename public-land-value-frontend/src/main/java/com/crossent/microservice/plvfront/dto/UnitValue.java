package com.crossent.microservice.plvfront.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnitValue {

    private String name;
    private String dong;
    private Integer stdYear;
    private Long unitArValue;

}
