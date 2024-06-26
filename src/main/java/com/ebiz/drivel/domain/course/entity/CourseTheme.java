package com.ebiz.drivel.domain.course.entity;

import com.ebiz.drivel.domain.theme.entity.Theme;
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
@Table(name = "course_theme")
public class CourseTheme {
    @EmbeddedId
    private CourseThemeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("themeId")
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    private Theme theme;

}
