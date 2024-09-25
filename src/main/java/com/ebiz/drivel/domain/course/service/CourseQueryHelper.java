package com.ebiz.drivel.domain.course.service;

import com.ebiz.drivel.domain.course.entity.QCourse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import java.util.List;

public class CourseQueryHelper {

    public static BooleanBuilder createQueryFilter(List<Long> themeIds, List<Long> styleIds, List<Long> togetherIds) {
        BooleanBuilder filterBuilder = new BooleanBuilder();
        QCourse course = QCourse.course;

        addThemeFilter(themeIds, course, filterBuilder);
        addStyleFilter(styleIds, course, filterBuilder);
        addTogetherFilter(togetherIds, course, filterBuilder);

        return filterBuilder;
    }

    public static void addRegionFilter(List<Long> regionIds, QCourse course, BooleanBuilder filterBuilder) {
        if (regionIds != null && !regionIds.isEmpty()) {
            BooleanBuilder regionBuilder = new BooleanBuilder();
            for (Long regionId : regionIds) {
                regionBuilder.or(course.regionId.eq(regionId));
            }
            filterBuilder.and(regionBuilder);
        }
    }

    public static void addThemeFilter(List<Long> themeIds, QCourse course, BooleanBuilder filterBuilder) {
        if (themeIds != null && !themeIds.isEmpty()) {
            BooleanBuilder themeBuilder = new BooleanBuilder();
            for (Long themeId : themeIds) {
                themeBuilder.or(course.courseThemes.any().theme.id.eq(themeId));
            }
            filterBuilder.and(themeBuilder);
        }
    }

    public static void addStyleFilter(List<Long> styleIds, QCourse course, BooleanBuilder filterBuilder) {
        if (styleIds != null && !styleIds.isEmpty()) {
            BooleanBuilder styleBuilder = new BooleanBuilder();
            for (Long styleId : styleIds) {
                styleBuilder.or(course.courseStyles.any().style.id.eq(styleId));
            }
            filterBuilder.and(styleBuilder);
        }
    }

    public static void addTogetherFilter(List<Long> togetherIds, QCourse course, BooleanBuilder filterBuilder) {
        if (togetherIds != null && !togetherIds.isEmpty()) {
            BooleanBuilder togetherBuilder = new BooleanBuilder();
            for (Long togetherId : togetherIds) {
                togetherBuilder.or(course.courseTogethers.any().together.id.eq(togetherId));
            }
            filterBuilder.and(togetherBuilder);
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
