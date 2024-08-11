package com.ebiz.drivel.domain.profile.entity;

import com.ebiz.drivel.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Member profile;

    private String reason;
    private String details;

    @Builder
    public Report(Long id, Member profile, String reason, String details) {
        this.id = id;
        this.profile = profile;
        this.reason = reason;
        this.details = details;
    }
}
