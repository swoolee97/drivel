<<<<<<<< HEAD:src/main/java/com/ebiz/drivel/domain/report/dto/ReportMemberDTO.java
package com.ebiz.drivel.domain.report.dto;
========
package com.ebiz.drivel.domain.report;
>>>>>>>> 5e5fb8b (refactor: 신고/차단 기능 수정):src/main/java/com/ebiz/drivel/domain/report/ReportMemberDTO.java


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportMemberDTO {
    private Long targetMemberId;
    private List<String> descriptions;
}
