package com.ebiz.drivel.domain.course.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class CourseTogetherId implements Serializable {
    private Long courseId;
    private Long togetherId;
}
