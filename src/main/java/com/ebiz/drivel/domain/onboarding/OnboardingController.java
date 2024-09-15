package com.ebiz.drivel.domain.onboarding;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/onboarding")
public class OnboardingController {

    private final OnboardingService onboardingService;

    @PostMapping
    public void onboard(@Valid @RequestBody OnboardingRequest request) {
        onboardingService.saveOnboardingInfo(request);
    }

    @GetMapping
    public ResponseEntity<MeetingCreateCheckDTO> isOnboarded() {
        MeetingCreateCheckDTO meetingCreateCheckDTO = onboardingService.isOnboarded();
        return ResponseEntity.ok(meetingCreateCheckDTO);
    }

}
