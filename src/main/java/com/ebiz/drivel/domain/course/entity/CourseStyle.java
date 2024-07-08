package com.ebiz.drivel.domain.course.entity;

import com.ebiz.drivel.domain.onboarding.entity.Style;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "course_style")
public class CourseStyle {
    @EmbeddedId
    private CourseStyleId courseStyleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("styleId")
    @JoinColumn(name = "style_id", referencedColumnName = "id")
    private Style style;
}

