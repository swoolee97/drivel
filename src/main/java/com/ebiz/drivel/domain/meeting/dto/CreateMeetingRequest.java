package com.ebiz.drivel.domain.meeting.dto;

import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.meeting.entity.Gender;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateMeetingRequest {
    @NotNull
    private String title;
    @NotNull
    private Date date;
    @NotNull
    private String description;
    @NotNull
    private String meetingPoint;
    @NotNull
    private Long courseId;
    @NotNull
    private Integer gender;
    @NotNull
    private Integer startAge;
    @NotNull
    private Integer endAge;
    @Nullable
    private String carModel;
    @Nullable
    private Integer minCarCareer;

    public Meeting toEntity() {
        Course course = Course.builder().id(courseId).build();

        return Meeting.builder()
                .title(getTitle())
                .meetingDate(getDate())
                .description(getDescription())
                .meetingPoint(getMeetingPoint())
                .course(course)
                .gender(Gender.getGenderById(getGender()))
                .startAge(getStartAge())
                .carModel(getCarModel())
                .minCarCareer(getMinCarCareer())
                .endAge(getEndAge()).build();
    }
}
