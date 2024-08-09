package com.ebiz.drivel.domain.profile.api;

import com.ebiz.drivel.domain.member.application.MemberService;
import com.ebiz.drivel.domain.profile.dto.*;
import com.ebiz.drivel.domain.profile.entity.Block;
import com.ebiz.drivel.domain.profile.service.BlockService;
import com.ebiz.drivel.domain.profile.service.ProfileService;
import com.ebiz.drivel.domain.profile.service.ReportService;
import jakarta.annotation.Nullable;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final MemberService memberService;
    private final BlockService blockService;
    private final ReportService reportService;

    @PostMapping("/image")
    public void changeProfileImage(@Nullable @RequestPart("image") MultipartFile image) throws IOException {
        profileService.changeProfileImage(image);
    }

    @GetMapping("/my")
    public ResponseEntity<ProfileDTO> getMyProfile() {
        ProfileDTO profileDTO = profileService.getMyProfileDetails();
        return ResponseEntity.ok(profileDTO);
    }

    @PatchMapping("/nickname")
    public ResponseEntity<Void> updateNicknameAndDescription(@RequestBody UpdateNicknameDTO updateNicknameDTO) {
        profileService.updateNicknameAndDescription(updateNicknameDTO);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/car")
    public ResponseEntity<Void> updateCarInfo(@RequestBody UpdateCarDTO updateCarDTO) {
        profileService.updateCarInfo(updateCarDTO);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/gender")
    public ResponseEntity<Void> updateGenderAndBirth(@RequestBody UpdateGenderDTO updateGenderDTO) {
        profileService.updateGenderAndBirth(updateGenderDTO);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/region")
    public ResponseEntity<Void> updateRegion(@RequestBody UpdateRegionDTO updateRegionDTO) {
        profileService.updateMemberRegion(updateRegionDTO);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/profileUpdate")
    public ResponseEntity<Void> updateProfile(@RequestBody UpdateProfileDTO updateProfileDTO) {
        profileService.updateMemberProfile(updateProfileDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfileById(@PathVariable Long id){
        return memberService.getProfileById(id);
    }

    @PostMapping("/{userid}/block")
    public ResponseEntity<String> blockUser(@PathVariable Long userId, @RequestBody BlockProfileDTO blockProfileDTO) {
        try {
            blockService.blockUser(userId, blockProfileDTO);
            return ResponseEntity.ok("유저가 차단되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("차단에 실패했습니다: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}/isBlocked/{blockedUserId}")
    public ResponseEntity<Boolean> isUserBlocked(@PathVariable Long userId, @PathVariable Long blockedUserId) {
        try {
            boolean isBlocked = blockService.isUserBlocked(userId, blockedUserId);
            return ResponseEntity.ok(isBlocked);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PostMapping("/{profileId}/report")
    public ResponseEntity<String> reportProfile(@PathVariable Long profileId, @RequestBody ReportProfileDTO reportProfileDTO) {
        try {
            reportService.reportProfile(profileId, reportProfileDTO);
            return ResponseEntity.ok("유저가 신고되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("신고 처리에 실패했습니다: " + e.getMessage());
        }
    }
}
