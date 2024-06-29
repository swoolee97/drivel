package com.ebiz.drivel.domain.member.entity;

import com.ebiz.drivel.domain.theme.entity.Theme;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "member_theme")
public class MemberTheme {
    @EmbeddedId
    private MemberThemeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("themeId")
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    private Theme theme;

}
