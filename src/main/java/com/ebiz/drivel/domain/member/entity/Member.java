package com.ebiz.drivel.domain.member.entity;

import static com.ebiz.drivel.domain.profile.ProfileConstant.DEFAULT_PROFILE_IMAGE_PREFIX;

import com.ebiz.drivel.domain.course.entity.CourseLike;
import com.ebiz.drivel.domain.meeting.entity.Gender;
import com.ebiz.drivel.domain.meeting.entity.MeetingMember;
import com.ebiz.drivel.domain.review.entity.CourseReview;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "role", columnDefinition = "USER")
    private String role;

    @Column(name = "birth")
    private Date birth;

    @Column(name = "car_model")
    private String carModel;

    @Column(name = "car_career")
    private Integer carCareer;

    @Column(name = "gender", columnDefinition = "NONE")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<CourseLike> courseLikes;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<CourseReview> courseReviews;

    @Size(min = 10, max = 50, message = "10자 이상 50자 이내로 적어주세요")
    private String description;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MeetingMember> meetingMembers;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberRegion> memberRegions;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberStyle> memberStyles;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberTheme> memberThemes;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberTogether> memberTogethers;

    public boolean hasDefaultProfileImage() {
        return imagePath.contains(DEFAULT_PROFILE_IMAGE_PREFIX);
    }

    public void updateProfileImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isOnboarded() {
        return memberRegions != null && memberStyles != null && memberThemes != null && memberTogethers != null;
    }

}
