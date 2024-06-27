package com.ebiz.drivel.domain.meeting.application;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.meeting.dto.CreateMeetingRequest;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public void createMeeting(CreateMeetingRequest createMeetingRequest) {
        Meeting meeting = createMeetingRequest.toEntity();
        meeting.setMasterMemberId(userDetailsService.getMemberByContextHolder().getId());
        meetingRepository.save(meeting);
    }
}
