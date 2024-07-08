package com.ebiz.drivel.domain.course.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Embeddable
@Getter
@EqualsAndHashCode
public class CourseStyleId implements Serializable {
    private Long courseId;
    private Long styleId;
}
