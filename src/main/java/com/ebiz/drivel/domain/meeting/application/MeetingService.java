package com.ebiz.drivel.domain.meeting.application;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.meeting.dto.CreateMeetingRequest;
import com.ebiz.drivel.domain.meeting.dto.MeetingInfoResponse;
import com.ebiz.drivel.domain.meeting.dto.MeetingListRequest;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.repository.MeetingRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingMemberService meetingMemberService;
    private final UserDetailsServiceImpl userDetailsService;

    @Transactional
    public MeetingInfoResponse createMeeting(CreateMeetingRequest createMeetingRequest) {
        Meeting meeting = insertMeeting(createMeetingRequest);
        meetingMemberService.insertMeetingMember(meeting);
        return Meeting.toMeetingInfo(meeting);
    }

    public Page<MeetingInfoResponse> getMeetings(MeetingListRequest meetingListRequest, Pageable pageable) {
        Page<Meeting> meetings = meetingRepository.findByCondition(
                meetingListRequest.getAge(),
                meetingListRequest.getGender(),
                meetingListRequest.getCarModel(), pageable);
        return meetings.map(Meeting::toMeetingInfo);
    }

    public Meeting insertMeeting(CreateMeetingRequest createMeetingRequest) {
        Meeting meeting = createMeetingRequest.toEntity();
        Member member = userDetailsService.getMemberByContextHolder();
        meeting.setMasterMemberId(member.getId());
        return meetingRepository.save(meeting);
    }

}
