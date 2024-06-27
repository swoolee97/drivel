package com.ebiz.drivel.domain.meeting;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Gender {
    ANY(0, "제한없음"),
    MALE(1, "남자"),
    FEMALE(2, "여자");

    private final int id;
    private final String displayName;
}
