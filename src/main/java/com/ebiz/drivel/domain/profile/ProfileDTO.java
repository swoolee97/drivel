package com.ebiz.drivel.domain.profile;

import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.onboarding.RegionDTO;
import com.ebiz.drivel.domain.onboarding.StyleDTO;
import com.ebiz.drivel.domain.onboarding.TogetherDTO;
import com.ebiz.drivel.domain.theme.dto.ThemeDTO;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProfileDTO {
    private String nickname;
    private String imagePath;
    private String carModel;
    private Integer carCareer;
    private String gender;
    private String description;
    private Date birth;
    private List<RegionDTO> regions;
    private List<StyleDTO> styles;
    private List<ThemeDTO> themes;
    private List<TogetherDTO> togethers;

    public static ProfileDTO from(Member member) {
        return ProfileDTO.builder()
                .nickname(member.getNickname())
                .imagePath(member.getImagePath())
                .carModel(member.getCarModel())
                .carCareer(member.getCarCareer())
                .gender(member.getGender().getDisplayName())
                .description(member.getDescription())
                .birth(member.getBirth())
                .regions(member.getMemberRegions().stream().map(RegionDTO::from).toList())
                .styles(member.getMemberStyles().stream().map(StyleDTO::from).toList())
                .themes(member.getMemberThemes().stream().map(ThemeDTO::from).toList())
                .togethers(member.getMemberTogethers().stream().map(TogetherDTO::from).toList())
                .build();
    }

}
