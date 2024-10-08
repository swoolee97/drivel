package com.ebiz.drivel.domain.review.service;

import com.ebiz.drivel.domain.block.BlockMember;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.review.entity.QCourseReview;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import java.util.List;

public class ReviewQueryHelper {

    public static BooleanBuilder createQueryFilter(Long id, Member member) {
        BooleanBuilder filterBuilder = new BooleanBuilder();
        QCourseReview review = QCourseReview.courseReview;

        addCourseIdFilter(review, filterBuilder, id);
        addDeletedReviewFilter(review, filterBuilder);
        addBlockFilter(review, member, filterBuilder);

        return filterBuilder;
    }

    public static void addCourseIdFilter(QCourseReview review, BooleanBuilder filterBuilder, Long id) {
        BooleanBuilder courseIdBuilder = new BooleanBuilder();
        courseIdBuilder.and(review.course.id.eq(id));
        filterBuilder.and(courseIdBuilder);
    }

    public static void addDeletedReviewFilter(QCourseReview review, BooleanBuilder filterBuilder) {
        BooleanBuilder deletedReviewBuilder = new BooleanBuilder();
        deletedReviewBuilder.and(review.isDeleted.isFalse());
        filterBuilder.and(deletedReviewBuilder);
    }

    public static void addBlockFilter(QCourseReview review, Member member, BooleanBuilder filterBuilder) {
        List<Member> blockedMembers = member.getBlockMembers().stream().map(BlockMember::getTargetMember).toList();
        filterBuilder.and(review.member.notIn(blockedMembers));
    }

    public static OrderSpecifier<?> getOrderSpecifier(QCourseReview review) {
        return review.id.desc();
    }

}
