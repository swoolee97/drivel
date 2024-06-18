package com.ebiz.drivel.domain.member.util;

import static com.ebiz.drivel.domain.profile.ProfileConstant.DEFAULT_PROFILE_IMAGE_PREFIX;
import static com.ebiz.drivel.domain.profile.ProfileConstant.DEFAULT_PROFILE_IMAGE_SUFFIX;

import jakarta.annotation.PostConstruct;
import java.util.Random;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProfileImageGenerator {

    @Value("${default.image.address}")
    private String profileImagePath;
    private static String PROFILE_IMAGE_PATH;

    @PostConstruct
    private void init() {
        PROFILE_IMAGE_PATH = profileImagePath;
    }

    public static String getDefaultProfileImagePath() {
        Random random = new Random();
        String randomNumber = String.valueOf(random.nextInt(3) + 1);
        return PROFILE_IMAGE_PATH + DEFAULT_PROFILE_IMAGE_PREFIX + randomNumber + DEFAULT_PROFILE_IMAGE_SUFFIX;
    }

}
