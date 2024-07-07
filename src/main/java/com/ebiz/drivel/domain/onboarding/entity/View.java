package com.ebiz.drivel.domain.onboarding.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "view")
public class View {

    @Id
    private Long id;

    @Column(name = "display_name")
    private String displayName;

}
