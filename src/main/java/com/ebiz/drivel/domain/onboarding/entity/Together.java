package com.ebiz.drivel.domain.onboarding.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "together")
public class Together {

    @Id
    private Long id;

    @Column(name = "display_name")
    private String displayName;

}
