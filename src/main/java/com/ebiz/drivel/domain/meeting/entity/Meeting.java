package com.ebiz.drivel.domain.meeting.entity;

import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.meeting.dto.MeetingMemberInfoDTO;
import com.ebiz.drivel.domain.member.entity.Member;
import com.google.api.services.storage.Storage.Projects.HmacKeys.Create;
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
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "meeting")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @Size(min = 1, max = 30, message = "제목은 1자 이상 30자 이하로 적어주세요")
    private String title;

    @Column(name = "meeting_date")
    @FutureOrPresent(groups = {Create.class}, message = "미팅 날짜는 과거일 수 없습니다")
    private Date meetingDate;

    @Column(name = "description")
    @Size(min = 1, max = 30, message = "설명은 1자 이상 30자 이하로 적어주세요")
    private String description;

    @Column(name = "meeting_point")
    @Size(min = 1, max = 30, message = "집결지는 1자 이상 30자 이하로 적어주세요")
    private String meetingPoint;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MeetingStatus status;

    @Column(name = "min_car_career")
    @Min(value = 1, message = "경력은 1이상의 숫자만 가능합니다")
    @Max(value = 50, message = "경력은 50이하의 숫자만 가능합니다")
    private Integer minCarCareer;

    @Column(name = "capacity")
    @Min(value = 2, message = "제한인원은 최소 두명입니다")
    @Max(value = 20, message = "제한인원은 최대 20명입니다")
    private Integer capacity;

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
    private List<MeetingMember> meetingMembers;

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
    private List<MeetingJoinRequest> joinRequests;

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
    private List<MeetingNotice> meetingNotices;

    public Long countParticipants() {
        return meetingMembers.stream().filter(MeetingMember::getIsActive).count();
    }

    public List<MeetingMemberInfoDTO> getParticipantsInfo() {
        return meetingMembers.stream()
                .filter(meetingMember -> meetingMember.getIsActive())
                .map(meetingMember -> meetingMember.getMember())
                .map(MeetingMemberInfoDTO::from)
                .collect(Collectors.toList());
    }

    public boolean isWaitingRequestMember(Member member) {
        return joinRequests.stream().anyMatch(
                joinRequest -> joinRequest.getMember().equals(member) && !joinRequest.isAlreadyDecidedRequest());
    }

    public boolean isUpcomingMeeting() {
        return meetingDate.after(Date.from(Instant.now()));
    }

    public boolean isAlreadyJoinedMember(Member member) {
        return meetingMembers.stream()
                .anyMatch(meetingMember -> meetingMember.getIsActive() && meetingMember.getMember().equals(member));
    }

    public void delete() {
        this.status = MeetingStatus.DELETED;
    }

    public void end() {
        this.status = MeetingStatus.INACTIVE;
    }

    public boolean isActive() {
        return this.status.equals(MeetingStatus.ACTIVE);
    }

    public boolean isFull() {
        return capacity <= meetingMembers.stream().filter(MeetingMember::getIsActive).count();
    }

    public boolean isDeleted() {
        return this.status.equals(MeetingStatus.DELETED);
    }

    public enum MeetingStatus {
        ACTIVE, INACTIVE, DELETED
    }

}
