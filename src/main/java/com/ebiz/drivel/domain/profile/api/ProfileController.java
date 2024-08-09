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
    public ResponseEntity<?> blockUser(@PathVariable Long userid, @RequestBody BlockProfileDTO blockProfileDTO){
        blockService.blockUser(userid, blockProfileDTO);
        return ResponseEntity.ok().body("유저가 차단되었습니다.");
    }

    @DeleteMapping("/{userId}/isBlcoked/{blockedUserId}")
    public ResponseEntity<?> isUserBlocked(@PathVariable Long userId, @PathVariable Long blockedUserId){
        boolean isBlocked = blockService.isUserBlocked(userId, blockedUserId);
        return ResponseEntity.ok().body(isBlocked);
    }

    @PostMapping("/{profileId}/report")
    public ResponseEntity<?> reportProfile(@PathVariable Long profileId, @RequestBody ReportProfileDTO reportProfileDTO){
        reportService.reportProfile(profileId, reportProfileDTO);
        return ResponseEntity.ok().body("유저를 신고하였습니다.");
    }
}
