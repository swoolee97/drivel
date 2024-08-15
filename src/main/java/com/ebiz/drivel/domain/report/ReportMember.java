<<<<<<<< HEAD:src/main/java/com/ebiz/drivel/domain/report/entity/ReportMember.java
package com.ebiz.drivel.domain.report.entity;
========
package com.ebiz.drivel.domain.report;
>>>>>>>> 5e5fb8b (refactor: 신고/차단 기능 수정):src/main/java/com/ebiz/drivel/domain/report/ReportMember.java

import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.profile.service.JSONConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_member_id", nullable = false)
    private Member targetMember;

    @Column(name = "reason", columnDefinition = "JSON")
    @Convert(converter = JSONConverter.class)
    private List<String> reason;
}
