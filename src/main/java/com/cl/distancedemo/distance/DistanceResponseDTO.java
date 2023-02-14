package com.cl.distancedemo.distance;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistanceResponseDTO {

    private String from;

    private String to;

    private Long distance;

    private String unit;
}
