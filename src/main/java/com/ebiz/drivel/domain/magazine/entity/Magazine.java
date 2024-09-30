package com.ebiz.drivel.domain.magazine.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "magazine")
public class Magazine {

    @Id
    private Integer id;

    private String banner;

    private String cover;

    private String body;

}
