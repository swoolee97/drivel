package com.ebiz.drivel.domain.onboarding;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.entity.MemberRegion;
import com.ebiz.drivel.domain.member.entity.MemberRegionId;
import com.ebiz.drivel.domain.member.entity.MemberStyle;
import com.ebiz.drivel.domain.member.entity.MemberStyleId;
import com.ebiz.drivel.domain.member.entity.MemberTheme;
import com.ebiz.drivel.domain.member.entity.MemberThemeId;
import com.ebiz.drivel.domain.member.entity.MemberTogether;
import com.ebiz.drivel.domain.member.entity.MemberTogetherId;
import com.ebiz.drivel.domain.member.repository.MemberRegionRepository;
import com.ebiz.drivel.domain.member.repository.MemberStyleRepository;
import com.ebiz.drivel.domain.member.repository.MemberThemeRepository;
import com.ebiz.drivel.domain.member.repository.MemberTogetherRepository;
import com.ebiz.drivel.domain.onboarding.entity.Region;
import com.ebiz.drivel.domain.onboarding.entity.Style;
import com.ebiz.drivel.domain.onboarding.entity.Together;
import com.ebiz.drivel.domain.onboarding.exception.RegionNotFoundException;
import com.ebiz.drivel.domain.onboarding.exception.StyleNotFoundException;
import com.ebiz.drivel.domain.onboarding.exception.ThemeNotFoundException;
import com.ebiz.drivel.domain.onboarding.exception.TogetherNotFoundException;
import com.ebiz.drivel.domain.theme.entity.Theme;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OnboardingService {

    private final UserDetailsServiceImpl userDetailsService;
    private final ThemeRepository themeRepository;
    private final RegionRepository regionRepository;
    private final StyleRepository styleRepository;
    private final TogetherRepository togetherRepository;
    private final MemberThemeRepository memberThemeRepository;
    private final MemberRegionRepository memberRegionRepository;
    private final MemberTogetherRepository memberTogetherRepository;
    private final MemberStyleRepository memberStyleRepository;

    @Transactional
    public void saveOnboardingInfo(OnboardingRequest request) {
        Member member = userDetailsService.getMemberByContextHolder();
        saveMemberThemeInfo(member, request.getThemeIds());
        saveMemberRegionInfo(member, request.getRegionIds());
        saveMemberStyleInfo(member, request.getStyleIds());
        saveMemberTogetherInfo(member, request.getPartnerIds());
    }

    public void saveMemberThemeInfo(Member member, List<Long> themeIds) {
        Long memberId = member.getId();
        List<MemberTheme> memberThemes = themeIds.stream()
                .map(themeId -> {
                    Theme theme = themeRepository.findById(themeId)
                            .orElseThrow(() -> new ThemeNotFoundException("찾을 수 없는 테마입니다"));
                    return new MemberTheme(new MemberThemeId(memberId, themeId), member, theme);
                }).collect(Collectors.toList());
        memberThemeRepository.saveAll(memberThemes);
    }

    public void saveMemberRegionInfo(Member member, List<Long> regionIds) {
        Long memberId = member.getId();
        List<MemberRegion> memberRegions = regionIds.stream()
                .map(regionId -> {
                    Region region = regionRepository.findById(regionId)
                            .orElseThrow(() -> new RegionNotFoundException("찾을 수 없는 지역입니다"));
                    return new MemberRegion(new MemberRegionId(memberId, regionId), member, region);
                }).collect(Collectors.toList());
        memberRegionRepository.saveAll(memberRegions);
    }

    public void saveMemberStyleInfo(Member member, List<Long> styleIds) {
        Long memberId = member.getId();
        List<MemberStyle> memberStyles = styleIds.stream()
                .map(styleId -> {
                    Style style = styleRepository.findById(styleId)
                            .orElseThrow(() -> new StyleNotFoundException("찾을 수 없는 스타일입니다"));
                    return new MemberStyle(new MemberStyleId(memberId, styleId), member, style);
                }).collect(Collectors.toList());
        memberStyleRepository.saveAll(memberStyles);
    }

    public void saveMemberTogetherInfo(Member member, List<Long> togetherIds) {
        Long memberId = member.getId();
        List<MemberTogether> memberTogethers = togetherIds.stream()
                .map(partnerId -> {
                    Together together = togetherRepository.findById(partnerId)
                            .orElseThrow(() -> new TogetherNotFoundException("찾을 수 없는 Together입니다"));
                    return new MemberTogether(new MemberTogetherId(memberId, partnerId), member, together);
                }).collect(Collectors.toList());
        memberTogetherRepository.saveAll(memberTogethers);
    }

}
