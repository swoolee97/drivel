package com.ebiz.drivel.domain.course.entity;

import com.ebiz.drivel.domain.onboarding.entity.Together;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "course_together")
public class CourseTogether {
    @EmbeddedId
    private CourseTogetherId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("togetherId")
    @JoinColumn(name = "together_id", referencedColumnName = "id")
    private Together together;
}
