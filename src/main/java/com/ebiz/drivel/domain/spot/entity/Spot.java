package com.ebiz.drivel.domain.spot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "spot")
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String address;

    @Column(name = "first_image")
    private String firstImage;

    @Column(name = "second_image")
    private String secondImage;

    private BigDecimal longitude;

    private BigDecimal latitude;

}
