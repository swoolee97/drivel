package com.ebiz.drivel.domain.waypoint.entity;

import com.ebiz.drivel.domain.course.entity.Course;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;

@Entity
@Getter
@Table(name = "waypoint")
public class Waypoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "order")
    private int order;

    @Column(name = "latitude", nullable = false, precision = 12, scale = 10)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 13, scale = 10)
    private BigDecimal longitude;

    @Column(name = "description")
    private String description;
}
