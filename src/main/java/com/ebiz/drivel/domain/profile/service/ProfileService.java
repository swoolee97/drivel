package com.ebiz.drivel.domain.profile.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.util.ProfileImageGenerator;
import com.ebiz.drivel.domain.onboarding.RegionRepository;
import com.ebiz.drivel.domain.onboarding.entity.Region;
import com.ebiz.drivel.domain.profile.dto.ProfileDTO;
import com.ebiz.drivel.domain.profile.dto.UpdateCarDTO;
import com.ebiz.drivel.domain.profile.dto.UpdateGenderDTO;
import com.ebiz.drivel.domain.profile.dto.UpdateNicknameDTO;
import com.ebiz.drivel.domain.profile.dto.UpdateRegionDTO;
import com.ebiz.drivel.global.service.S3Service;
import jakarta.persistence.EntityManager;
import java.io.IOException;
import java.util.List;
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
    private final EntityManager entityManager;
    private final RegionRepository regionRepository;

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
    public void updateCarInfo(UpdateCarDTO updateCarDTO) {
        Member member = userDetailsService.getMemberByContextHolder();
        member.updateCarModel(updateCarDTO.getCarModel());
        member.updateCarCareer(updateCarDTO.getCarCareer());
    }

    @Transactional
    public void updateGenderAndBirth(UpdateGenderDTO updateGenderDTO) {
        Member member = userDetailsService.getMemberByContextHolder();
        member.updateGender(updateGenderDTO.getGender());
        member.updateBirth(updateGenderDTO.getBirth());
    }

    @Transactional
    public void updateMemberRegion(UpdateRegionDTO updateRegionDTO) {
        Member member = userDetailsService.getMemberByContextHolder();
        member.getMemberRegions().clear();
        entityManager.flush();
        List<Region> regions = regionRepository.findAllById(updateRegionDTO.getRegionIds());
        member.updateRegion(regions);
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
