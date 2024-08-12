package com.ebiz.drivel.domain.meeting.service;

import com.ebiz.drivel.domain.meeting.dto.ReportMeetingDTO;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.entity.ReportMeeting;
import com.ebiz.drivel.domain.meeting.exception.MeetingNotFoundException;
import com.ebiz.drivel.domain.meeting.repository.MeetingRepository;
import com.ebiz.drivel.domain.meeting.repository.ReportMeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReportMeetingService {
    private final ReportMeetingRepository reportMeetingRepository;
    private final MeetingRepository meetingRepository;

    @Transactional
    public void reportMeeting(ReportMeetingDTO reportMeetingDTO) {
        Long meetingId = reportMeetingDTO.getMeetingId();

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> MeetingNotFoundException.of("모임을 찾을 수 없습니다."));

        ReportMeeting reportMeeting = ReportMeeting.builder()
                .meeting(meeting)
                .reason(reportMeetingDTO.getReason())
                .details(reportMeetingDTO.getDetails())
                .build();

        reportMeetingRepository.save(reportMeeting);
    }
}