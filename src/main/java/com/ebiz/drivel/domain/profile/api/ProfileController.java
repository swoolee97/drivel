package com.ebiz.drivel.domain.profile.api;

import com.ebiz.drivel.domain.member.application.MemberService;
import com.ebiz.drivel.domain.profile.dto.BlockProfileDTO;
import com.ebiz.drivel.domain.profile.dto.ProfileDTO;
import com.ebiz.drivel.domain.profile.dto.ReportProfileDTO;
import com.ebiz.drivel.domain.profile.dto.UpdateCarDTO;
import com.ebiz.drivel.domain.profile.dto.UpdateGenderDTO;
import com.ebiz.drivel.domain.profile.dto.UpdateNicknameDTO;
import com.ebiz.drivel.domain.profile.dto.UpdateProfileDTO;
import com.ebiz.drivel.domain.profile.dto.UpdateRegionDTO;
import com.ebiz.drivel.domain.profile.service.BlockService;
import com.ebiz.drivel.domain.profile.service.ProfileService;
import com.ebiz.drivel.domain.profile.service.ReportService;
import jakarta.annotation.Nullable;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<ProfileDTO> getProfileById(@PathVariable Long id) {
        return memberService.getProfileById(id);
    }

    @PostMapping("/block")
    public ResponseEntity<String> blockUser(@RequestBody BlockProfileDTO blockProfileDTO) {
        blockService.blockUser(blockProfileDTO);
        return ResponseEntity.ok("유저가 차단되었습니다.");
    }

    @PostMapping("/unblock")
    public ResponseEntity<String> unblockUser(@RequestBody BlockProfileDTO blockProfileDTO) {
        blockService.unblockUser(blockProfileDTO.getMemberId(), blockProfileDTO.getBlockedUserId());
        return ResponseEntity.ok("유저의 차단이 해제되었습니다.");
    }

    @GetMapping("/{userId}/isBlocked/{blockedUserId}")
    public ResponseEntity<Boolean> isUserBlocked(@PathVariable Long userId, @PathVariable Long blockedUserId) {
        boolean isBlocked = blockService.isUserBlocked(userId, blockedUserId);
        return ResponseEntity.ok(isBlocked);
    }

    @PostMapping("/report")
    public ResponseEntity<String> reportProfile(@RequestBody ReportProfileDTO reportProfileDTO) {
        reportService.reportProfile(reportProfileDTO);
        return ResponseEntity.ok("유저가 신고되었습니다.");
    }
}
