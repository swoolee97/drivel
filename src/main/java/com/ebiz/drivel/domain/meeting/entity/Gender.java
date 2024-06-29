package com.ebiz.drivel.domain.meeting.entity;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Gender {
    NONE(0, null),
    MALE(1, "남자"),
    FEMALE(2, "여자");

    private final Integer id;
    private final String displayName;

    public static Gender getGenderById(Integer id) {
        return Arrays.stream(Gender.values())
                .filter(gender -> gender.id == id)
                .findFirst()
                .orElse(Gender.NONE);
    }

}
