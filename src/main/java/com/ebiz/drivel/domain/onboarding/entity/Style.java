package com.ebiz.drivel.domain.onboarding.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "style")
public class Style extends Tag {
    
}
