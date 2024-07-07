package com.ebiz.drivel.onboarding.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "with")
public class With {

    @Id
    private Long id;

    @Column(name = "display_name")
    private String displayName;

}