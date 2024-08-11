package com.ebiz.drivel.domain.onboarding.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public class Tag {
    @Id
    private Long id;

    @Column(name = "display_name")
    private String displayName;
}
