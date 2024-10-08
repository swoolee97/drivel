package com.ebiz.drivel.domain.member.entity;

import static com.ebiz.drivel.domain.profile.constant.ProfileConstant.DEFAULT_PROFILE_IMAGE_PREFIX;

import com.ebiz.drivel.domain.block.BlockMember;
import com.ebiz.drivel.domain.course.entity.CourseLike;
import com.ebiz.drivel.domain.meeting.entity.Gender;
import com.ebiz.drivel.domain.meeting.entity.MeetingMember;
import com.ebiz.drivel.domain.onboarding.entity.Region;
import com.ebiz.drivel.domain.onboarding.entity.Style;
import com.ebiz.drivel.domain.onboarding.entity.Together;
import com.ebiz.drivel.domain.review.entity.CourseReview;
import com.ebiz.drivel.domain.theme.entity.Theme;
import jakarta.persistence.CascadeType;
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

    private String description;

    @Column(name = "is_profile_locked", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isProfileLocked;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isDeleted;

    @Column(name = "score", columnDefinition = "DOUBLE DEFAULT 30.0")
    private double score;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MeetingMember> meetingMembers;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberRegion> memberRegions;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberStyle> memberStyles;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberTheme> memberThemes;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberTogether> memberTogethers;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<BlockMember> blockMembers;

    public boolean hasDefaultProfileImage() {
        return imagePath.contains(DEFAULT_PROFILE_IMAGE_PREFIX);
    }

    public void updateProfileImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isOnboarded() {
        return memberRegions != null && memberStyles != null && memberThemes != null && memberTogethers != null &&
                !memberRegions.isEmpty() && !memberStyles.isEmpty() && !memberThemes.isEmpty()
                && !memberTogethers.isEmpty();
    }

    public boolean isUnableToJoinMeeting() {
        return description == null || carModel == null || carCareer == null || gender == Gender.NONE;
    }

    public void updateNickname(String nickname) {
        if (nickname == null) {
            return;
        }
        this.nickname = nickname;
    }

    public void updateDescription(String description) {
        if (description == null) {
            return;
        }
        this.description = description;
    }

    public void updateCarModel(String carModel) {
        if (carModel == null) {
            return;
        }
        this.carModel = carModel;
    }

    public void updateCarCareer(Integer carCareer) {
        if (carCareer == null) {
            return;
        }
        this.carCareer = carCareer;
    }

    public void updateGender(Integer gender) {
        if (gender == null) {
            return;
        }
        this.gender = Gender.getGenderById(gender);
    }

    public void updateBirth(Date birth) {
        if (birth == null) {
            return;
        }
        this.birth = birth;
    }

    public void updateRegion(List<Region> regions) {
        regions.forEach(region -> memberRegions.add(
                MemberRegion.builder()
                        .memberRegionId(new MemberRegionId(this.id, region.getId()))
                        .member(this)
                        .region(region)
                        .build()));
    }

    // 메서드 신규 구현
    public void updateStyles(List<Style> styles) {
        styles.forEach(style -> memberStyles.add(
                MemberStyle.builder()
                        .memberStyleId(new MemberStyleId(this.id, style.getId()))
                        .member(this)
                        .style(style)
                        .build()));
    }

    public void updateTheme(List<Theme> themes) {
        themes.forEach(theme -> memberThemes.add(
                MemberTheme.builder()
                        .memberThemeId(new MemberThemeId(this.id, theme.getId()))
                        .member(this)
                        .theme(theme)
                        .build()));
    }

    public void updateTogether(List<Together> togethers) {
        togethers.forEach(together -> memberTogethers.add(
                MemberTogether.builder()
                        .memberTogetherId(new MemberTogetherId(this.id, together.getId()))
                        .member(this)
                        .together(together)
                        .build()));
    }

    public void updateScore(double difference) {
        score += difference;
    }

    public void lockProfile() {
        isProfileLocked = !isProfileLocked;
    }

    public void delete() {
        isDeleted = true;
    }

    public void updatePassword(String newPassword) {
        if (this.password == null) {
            return;
        }
        this.password = newPassword;
    }

}
