package com.ebiz.drivel.domain.course.service;

import com.ebiz.drivel.domain.course.entity.QCourse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;

public class CourseQueryHelper {

    public static BooleanBuilder createQueryFilter(Long themeId, Long styleId, Long togetherId) {
        BooleanBuilder filterBuilder = new BooleanBuilder();
        QCourse course = QCourse.course;

        addThemeFilter(themeId, course, filterBuilder);
        addStyleFilter(styleId, course, filterBuilder);
        addTogetherFilter(togetherId, course, filterBuilder);

        return filterBuilder;
    }

    public static void addThemeFilter(Long themeId, QCourse course, BooleanBuilder filterBuilder) {
        if (themeId != null) {
            filterBuilder.and(course.courseThemes.any().theme.id.eq(themeId));
        }
    }

    public static void addStyleFilter(Long styleId, QCourse course, BooleanBuilder filterBuilder) {
        if (styleId != null) {
            filterBuilder.and(course.courseStyles.any().style.id.eq(styleId));
        }
    }

    public static void addTogetherFilter(Long togetherId, QCourse course, BooleanBuilder filterBuilder) {
        if (togetherId != null) {
            filterBuilder.and(course.courseTogethers.any().together.id.eq(togetherId));
        }
    }

    public static OrderSpecifier<?> getOrderSpecifier(OrderBy orderBy, QCourse course) {
        if (orderBy == null) {
            return course.id.desc();
        }
        switch (orderBy) {
            case LATEST:
                return course.id.desc();
            default:
                return course.id.desc();
        }
    }

    public enum OrderBy {
        LATEST, RECOMMEND;
    }

}
