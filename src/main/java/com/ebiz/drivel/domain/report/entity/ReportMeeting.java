<<<<<<< HEAD
<<<<<<<< HEAD:src/main/java/com/ebiz/drivel/domain/report/entity/ReportMeeting.java
package com.ebiz.drivel.domain.report.entity;

import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.member.entity.Member;
========
package com.ebiz.drivel.domain.meeting.entity;

>>>>>>>> base/drivel_second:src/main/java/com/ebiz/drivel/domain/report/ReportMeeting.java
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "report_meeting")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportMeeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_meeting_id", nullable = false)
    private Meeting meeting;

    private String summary;

    private String description;

}
=======
//package com.ebiz.drivel.domain.report.entity;
//
//import com.ebiz.drivel.domain.meeting.entity.Meeting;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Table(name = "report_meeting")
//@Getter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class ReportMeeting {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "meetingId", nullable = false)
//    private Meeting meeting;
//
//    private String reason;
//    private String details;
//
//}
>>>>>>> base/drivel_second
