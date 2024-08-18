package com.ebiz.drivel.domain.profile.api;

import com.ebiz.drivel.domain.block.BlockService;
import com.ebiz.drivel.domain.member.application.MemberService;
import com.ebiz.drivel.domain.profile.dto.ProfileDTO;
import com.ebiz.drivel.domain.profile.dto.UpdateCarDTO;
import com.ebiz.drivel.domain.profile.dto.UpdateGenderDTO;
import com.ebiz.drivel.domain.profile.dto.UpdateNicknameDTO;
import com.ebiz.drivel.domain.profile.dto.UpdateProfileDTO;
import com.ebiz.drivel.domain.profile.dto.UpdateRegionDTO;
import com.ebiz.drivel.domain.profile.service.ProfileService;
import com.ebiz.drivel.domain.report.service.ReportService;
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

    public static final String BLOCK_MEMBER_SUCCESS = "유저가 차단되었습니다.";
    public static final String UNBLOCK_MEMBER_SUCCESS = "유저의 차단이 해제되었습니다.";
    public static final String REPORT_PROFILE_SUCCESS = "유저가 신고되었습니다.";

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

}
