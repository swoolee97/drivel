package com.ebiz.drivel.domain.meeting.entity;

import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.meeting.dto.MeetingInfoResponse;
import com.ebiz.drivel.domain.meeting.dto.MeetingMemberInfoDTO;
import com.ebiz.drivel.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
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

    @ManyToOne(fetch = FetchType.EAGER)
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_member_id", referencedColumnName = "id")
    private Member masterMember;

    @Column(name = "car_model")
    private String carModel;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "min_car_career")
    @Min(value = 1, message = "1이상의 숫자만 가능합니다")
    @Max(value = 50, message = "50이하의 숫자만 가능합니다")
    private Integer minCarCareer;

    @Column(name = "capacity")
    @Min(value = 2, message = "최소 두명부터 가능합니다")
    @Max(value = 20, message = "20명까지 가능합니다")
    private Integer capacity;

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
    private List<MeetingMember> meetingMembers;

    public static MeetingInfoResponse toMeetingInfo(Meeting meeting) {
        Course course = meeting.getCourse();
        return MeetingInfoResponse.builder()
                .meetingId(meeting.getId())
                .courseId(course.getId())
                .meetingTitle(meeting.getTitle())
                .courseTitle(course.getTitle())
                .gender(meeting.getGender().getDisplayName())
                .startAge(meeting.getStartAge())
                .waypoints(course.getWaypoints())
                .endAge(meeting.getEndAge())
                .carModel(meeting.getCarModel())
                .imagePath(course.getImagePath())
                .minCarCareer(meeting.getMinCarCareer())
                .build();
    }

    public List<MeetingMemberInfoDTO> getParticipantsInfo() {
        return meetingMembers.stream()
                .filter(meetingMember -> meetingMember.getIsActive())
                .map(meetingMember -> meetingMember.getMember())
                .map(MeetingMemberInfoDTO::from)
                .collect(Collectors.toList());
    }

}
