package com.ebiz.drivel.domain.review.service;

import com.ebiz.drivel.domain.review.entity.QCourseReview;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;

public class ReviewQueryHelper {

    public static BooleanBuilder createQueryFilter(Long id) {
        BooleanBuilder filterBuilder = new BooleanBuilder();
        QCourseReview review = QCourseReview.courseReview;

        addCourseIdFilter(review, filterBuilder, id);

        return filterBuilder;
    }

    public static void addCourseIdFilter(QCourseReview review, BooleanBuilder filterBuilder, Long id) {
        BooleanBuilder courseIdBuilder = new BooleanBuilder();
        courseIdBuilder.and(review.course.id.eq(id));
        filterBuilder.and(courseIdBuilder);
    }

    public static OrderSpecifier<?> getOrderSpecifier(QCourseReview review) {
        return review.id.desc();
    }

}
