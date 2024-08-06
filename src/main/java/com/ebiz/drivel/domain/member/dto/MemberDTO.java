package com.ebiz.drivel.domain.member.dto;

import com.ebiz.drivel.domain.meeting.entity.Gender;
import com.ebiz.drivel.domain.member.entity.Member;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class MemberDTO {
    private Long id;
    private String email;
    private String nickname;
    private String imagePath;
    private String role;
    private Date birth;
    private String carModel;
    private Gender gender;
    private String description;
    private List<MemberRegionDTO> regions;
    private List<MemberStyleDTO> styles;
    private List<MemberThemeDTO> themes;
    private List<MemberTogetherDTO> togethers;

    public static MemberDTO fromEntity(Member member) {
        return MemberDTO.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .imagePath(member.getImagePath())
                .role(member.getRole())
                .birth(member.getBirth())
                .carModel(member.getCarModel())
                .gender(member.getGender())
                .description(member.getDescription())
                .regions(member.getMemberRegions().stream()
                        .map(MemberRegionDTO::fromEntity)
                        .collect(Collectors.toList()))
                .themes(member.getMemberThemes().stream()
                        .map(MemberThemeDTO::fromEntity)
                        .collect(Collectors.toList()))
                .togethers(member.getMemberTogethers().stream()
                        .map(MemberTogetherDTO::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    public Member toEntity(){
        Member member = Member.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .imagePath(this.imagePath)
                .role(this.role)
                .birth(this.birth)
                .carModel(this.carModel)
                .gender(this.gender)
                .description(this.description)
                .build();

        member.setMemberRegions(this.regions.stream()
                .map(MemberRegionDTO::toEntity)
                .collect(Collectors.toList()));
        member.setMemberStyles(this.styles.stream()
                .map(MemberStyleDTO::toEntity)
                .collect(Collectors.toList()));

        member.setMemberThemes(this.themes.stream()
                .map(MemberThemeDTO::toEntity)
                .collect(Collectors.toList()));

        member.setMemberTogethers(this.togethers.stream()
                .map(MemberTogetherDTO::toEntity)
                .collect(Collectors.toList()));

        return member;
    }


}
