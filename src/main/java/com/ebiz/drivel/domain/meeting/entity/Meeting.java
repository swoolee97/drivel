package com.ebiz.drivel.domain.meeting.entity;

import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.meeting.dto.MeetingInfoResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "meeting")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @Size(min = 10, max = 30, message = "제목은 10자 이상 30자 이하로 적어주세요")
    private String title;

    @Column(name = "meeting_date")
    @FutureOrPresent(message = "미팅 날짜는 과거일 수 없습니다")
    private Date meetingDate;

    @Column(name = "description")
    @Size(min = 10, max = 30, message = "설명은 10자 이상 30자 이하로 적어주세요")
    private String description;

    @Column(name = "meeting_point")
    @Size(min = 3, max = 30, message = "집결지는 3자 이상 30자 이하로 적어주세요")
    private String meetingPoint;

    @OneToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "start_age")
    private Integer startAge;

    @Column(name = "end_age")
    private Integer endAge;

    @Setter
    @Column(name = "master_member_id")
    private Long masterMemberId;

    @Column(name = "car_model")
    private String carModel;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "min_car_career")
    @Min(value = 1, message = "1이상의 숫자만 가능합니다")
    @Max(value = 50, message = "50이하의 숫자만 가능합니다")
    private Integer minCarCareer;

    public static MeetingInfoResponse toMeetingInfo(Meeting meeting) {
        return MeetingInfoResponse.builder()
                .id(meeting.getId())
                .title(meeting.getTitle())
                .gender(meeting.getGender().getDisplayName())
                .startAge(meeting.getStartAge())
                .waypoints(meeting.getCourse().getWaypoints())
                .endAge(meeting.getEndAge())
                .carModel(meeting.getCarModel())
                .imagePath(meeting.getCourse().getImagePath())
                .minCarCareer(meeting.getMinCarCareer())
                .build();
    }

}
