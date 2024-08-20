<<<<<<< HEAD
<<<<<<<< HEAD:src/main/java/com/ebiz/drivel/domain/report/dto/ReportMeetingDTO.java
package com.ebiz.drivel.domain.report.dto;
========
package com.ebiz.drivel.domain.meeting.dto;
>>>>>>>> base/drivel_second:src/main/java/com/ebiz/drivel/domain/report/ReportMeetingDTO.java

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportMeetingDTO {
    private Long targetId;
    private String summary;
    private String description;
}
=======
//package com.ebiz.drivel.domain.report.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class ReportMeetingDTO {
//    private Long meetingId;
//    private String reason;
//    private String details;
//}
>>>>>>> base/drivel_second
