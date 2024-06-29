package com.ebiz.drivel.domain.member.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class MemberThemeId implements Serializable {
    private Long memberId;
    private Long themeId;
}
