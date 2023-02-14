package com.cl.distancedemo.station;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Station {

    @Id
    @GeneratedValue
    private Long id;

    @Nationalized
    private String ds100;

    @Nationalized
    private String name;

    // Längengrad
    @Column(precision = 9, scale = 6)
    private BigDecimal longitude;

    //Breitengrad
    @Column(precision = 9, scale = 6)
    private BigDecimal latitude;
}
