package com.ebiz.drivel.domain.profile;

import jakarta.annotation.Nullable;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/image")
    public void changeProfileImage(@Nullable @RequestPart("image") MultipartFile image) throws IOException {
        profileService.changeProfileImage(image);
    }

    @GetMapping("/my")
    public ResponseEntity<ProfileDTO> getMyProfile() {
        ProfileDTO profileDTO = profileService.getMyProfileDetails();
        return ResponseEntity.ok(profileDTO);
    }

}
