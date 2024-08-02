package com.ebiz.drivel.domain.profile.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.util.ProfileImageGenerator;
import com.ebiz.drivel.domain.profile.dto.ProfileDTO;
import com.ebiz.drivel.domain.profile.dto.UpdateNicknameDTO;
import com.ebiz.drivel.global.service.S3Service;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileService {

    @Value("${cloud.aws.s3.profileImageBucketName}")
    private String PROFILE_IMAGE_BUCKET_NAME;
    private final S3Service s3Service;
    private final ProfileImageGenerator profileImageGenerator;
    private final UserDetailsServiceImpl userDetailsService;

    public ProfileDTO getMyProfileDetails() {
        Member member = userDetailsService.getMemberByContextHolder();
        ProfileDTO profileDTO = ProfileDTO.from(member);
        return profileDTO;
    }

    @Transactional
    public void updateNicknameAndDescription(UpdateNicknameDTO updateNicknameDTO) {
        Member member = userDetailsService.getMemberByContextHolder();
        member.updateNickname(updateNicknameDTO.getNickname());
        member.updateDescription(updateNicknameDTO.getDescription());
    }

    @Transactional
    public void changeProfileImage(MultipartFile image) throws IOException {
        Member member = userDetailsService.getMemberByContextHolder();
        String newImagePath =
                image == null ? profileImageGenerator.getDefaultProfileImagePath() : uploadProfileImage(image);
        if (!member.hasDefaultProfileImage()) {
            deleteProfileImage(member.getImagePath());
        }
        member.updateProfileImagePath(newImagePath);
    }

    private String uploadProfileImage(MultipartFile image) throws IOException {
        return s3Service.uploadImageFile(image, PROFILE_IMAGE_BUCKET_NAME);
    }

    private void deleteProfileImage(String imagePath) {
        s3Service.deleteImage(imagePath, PROFILE_IMAGE_BUCKET_NAME);
    }

}
