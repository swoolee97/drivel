package com.ebiz.drivel.domain.meeting.application;

import com.ebiz.drivel.domain.block.BlockMember;
import com.ebiz.drivel.domain.meeting.entity.Gender;
import com.ebiz.drivel.domain.meeting.entity.Meeting.MeetingStatus;
import com.ebiz.drivel.domain.meeting.entity.QMeeting;
import com.ebiz.drivel.domain.member.entity.Member;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MeetingQueryHelper {

    private static final String WILDCARD_FORMAT = "%%%s%%";

    public static BooleanBuilder createFilterBuilder(
            Long styleId, Long themeId, Long togetherId, Integer age, Integer carCareer, String carModel,
            Integer genderId, Member member, QMeeting meeting) {
        BooleanBuilder filterBuilder = new BooleanBuilder();

        // 조건 필터링
        addStyleFilter(styleId, meeting, filterBuilder);
        addThemeFilter(themeId, meeting, filterBuilder);
        addTogetherFilter(togetherId, meeting, filterBuilder);
        addAgeFilter(age, meeting, filterBuilder);
        addMinCarCareerFilter(carCareer, meeting, filterBuilder);
        addCarModelFilter(carModel, meeting, filterBuilder);
        addGenderFilter(genderId, meeting, filterBuilder);
        addActiveFilter(meeting, filterBuilder);
        addBlockFilter(meeting, member, filterBuilder);
        addDateFilter(meeting, filterBuilder);

        return filterBuilder;
    }

    private static void addStyleFilter(Long styleId, QMeeting meeting, BooleanBuilder filterBuilder) {
        if (styleId != null) {
            filterBuilder.and(meeting.course.courseStyles.any().style.id.eq(styleId));
        }
    }

    private static void addThemeFilter(Long themeId, QMeeting meeting, BooleanBuilder filterBuilder) {
        if (themeId != null) {
            filterBuilder.and(meeting.course.courseThemes.any().theme.id.eq(themeId));
        }
    }

    private static void addTogetherFilter(Long togetherId, QMeeting meeting, BooleanBuilder filterBuilder) {
        if (togetherId != null) {
            filterBuilder.and(meeting.course.courseTogethers.any().together.id.eq(togetherId));
        }
    }

    private static void addAgeFilter(Integer age, QMeeting meeting, BooleanBuilder filterBuilder) {
        if (age != null) {
            filterBuilder.and(meeting.startAge.isNull().or(meeting.startAge.loe(age).and(meeting.endAge.goe(age))));
        }
    }

    private static void addMinCarCareerFilter(Integer carCareer, QMeeting meeting, BooleanBuilder filterBuilder) {
        if (carCareer != null) {
            filterBuilder.and(meeting.minCarCareer.isNull().or(meeting.minCarCareer.loe(carCareer)));
        }
    }

    private static void addCarModelFilter(String carModel, QMeeting meeting, BooleanBuilder filterBuilder) {
        if (carModel != null) {
            filterBuilder.and(meeting.carModel.like(String.format(WILDCARD_FORMAT, carModel)));
        }
    }

    private static void addGenderFilter(Integer genderId, QMeeting meeting, BooleanBuilder filterBuilder) {
        if (genderId != null) {
            filterBuilder.and(meeting.gender.eq(Gender.NONE).or(meeting.gender.eq(Gender.getGenderById(genderId))));
        }
    }

    private static void addActiveFilter(QMeeting meeting, BooleanBuilder filterBuilder) {
        filterBuilder.and(meeting.status.eq(MeetingStatus.ACTIVE));
    }

    public static void addBlockFilter(QMeeting meeting, Member member, BooleanBuilder filterBuilder) {
        List<Member> blockedMembers = member.getBlockMembers().stream().map(BlockMember::getTargetMember).toList();
        filterBuilder.and(meeting.masterMember.notIn(blockedMembers));
    }

    public static void addDateFilter(QMeeting meeting, BooleanBuilder filterBuilder) {
        filterBuilder.and(meeting.meetingDate.after(Date.from(Instant.now())));
    }

    public static OrderSpecifier<?> getOrderSpecifier(OrderBy orderBy, QMeeting meeting) {
        if (orderBy == null) {
            return meeting.id.desc();
        }
        switch (orderBy) {
            case LATEST:
                return meeting.id.desc();
            default:
                return meeting.id.desc();
        }
    }

    public enum OrderBy {
        LATEST, RECOMMEND;
    }
}
