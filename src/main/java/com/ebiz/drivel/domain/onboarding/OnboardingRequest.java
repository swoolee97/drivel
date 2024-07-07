package com.ebiz.drivel.domain.onboarding;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;

@Getter
public class OnboardingRequest {
    @Size(min = 1, max = 3, message = "3개까지 선택 가능합니다")
    private List<Long> regionIds;
    @Size(min = 1, max = 3, message = "3개까지 선택 가능합니다")
    private List<Long> partnerIds;
    @Size(min = 1, max = 3, message = "3개까지 선택 가능합니다")
    private List<Long> themeIds;
    @Size(min = 1, max = 3, message = "3개까지 선택 가능합니다")
    private List<Long> styleIds;
}
