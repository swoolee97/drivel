package com.ebiz.drivel.domain.meeting.application;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.meeting.dto.CreateMeetingRequest;
import com.ebiz.drivel.domain.meeting.dto.CreateMeetingResponse;
import com.ebiz.drivel.domain.meeting.dto.MeetingConditionDTO;
import com.ebiz.drivel.domain.meeting.dto.MeetingDetailResponse;
import com.ebiz.drivel.domain.meeting.dto.MeetingInfoDTO;
import com.ebiz.drivel.domain.meeting.dto.MeetingInfoResponse;
import com.ebiz.drivel.domain.meeting.dto.MeetingListRequest;
import com.ebiz.drivel.domain.meeting.dto.MeetingMasterInfoDTO;
import com.ebiz.drivel.domain.meeting.dto.MeetingMemberInfoDTO;
import com.ebiz.drivel.domain.meeting.dto.MeetingParticipantsInfoDTO;
import com.ebiz.drivel.domain.meeting.dto.UpcomingMeetingResponse;
import com.ebiz.drivel.domain.meeting.entity.Gender;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.entity.MeetingMember;
import com.ebiz.drivel.domain.meeting.exception.MeetingNotFoundException;
import com.ebiz.drivel.domain.meeting.repository.MeetingRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private static final String MEETING_NOT_FOUND_EXCEPTION_MESSAGE = "찾을 수 없는 모임입니다";
    private static final String MEETING_CREATE_SUCCESS_MESSAGE = "모임이 생성되었습니다";

    private final MeetingRepository meetingRepository;
    private final MeetingMemberService meetingMemberService;
    private final UserDetailsServiceImpl userDetailsService;

    @Transactional
    public CreateMeetingResponse createMeeting(CreateMeetingRequest createMeetingRequest) {
        Meeting meeting = insertMeeting(createMeetingRequest);
        meetingMemberService.insertMeetingMember(meeting);
        return CreateMeetingResponse.builder()
                .message(MEETING_CREATE_SUCCESS_MESSAGE)
                .courseId(meeting.getCourse().getId())
                .meetingId(meeting.getId())
                .build();
    }

    public Page<MeetingInfoResponse> getMeetings(MeetingListRequest meetingListRequest, Pageable pageable) {
        Page<Meeting> meetings = meetingRepository.findByCondition(
                meetingListRequest.getAge(),
                Gender.getGenderById(meetingListRequest.getGender()),
                meetingListRequest.getCarModel(), pageable);
        return meetings.map(Meeting::toMeetingInfo);
    }

    public Meeting insertMeeting(CreateMeetingRequest createMeetingRequest) {
        Meeting meeting = createMeetingRequest.toEntity();
        Member member = userDetailsService.getMemberByContextHolder();
        meeting.setMasterMember(member);
        return meetingRepository.save(meeting);
    }

    public MeetingDetailResponse getMeetingDetail(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException(MEETING_NOT_FOUND_EXCEPTION_MESSAGE));

        MeetingConditionDTO meetingConditionDTO = MeetingConditionDTO.from(meeting);
        MeetingMasterInfoDTO meetingMasterInfoDTO = MeetingMasterInfoDTO.from(meeting.getMasterMember());
        List<MeetingMemberInfoDTO> participantsInfo = meeting.getParticipantsInfo();
        MeetingParticipantsInfoDTO meetingParticipantsInfo = createParticipantsInfo(meeting, participantsInfo);

        return MeetingDetailResponse.builder()
                .meetingInfo(MeetingInfoDTO.builder()
                        .id(meeting.getId())
                        .title(meeting.getTitle())
                        .description(meeting.getDescription())
                        .date(meeting.getMeetingDate())
                        .condition(meetingConditionDTO)
                        .masterInfo(meetingMasterInfoDTO)
                        .participantsInfo(meetingParticipantsInfo)
                        .build())
                .build();
    }

    private MeetingParticipantsInfoDTO createParticipantsInfo(Meeting meeting,
                                                              List<MeetingMemberInfoDTO> participantsInfo) {
        return MeetingParticipantsInfoDTO.builder()
                .capacity(meeting.getCapacity())
                .membersInfo(participantsInfo)
                .build();
    }

    public List<UpcomingMeetingResponse> getUpcomingMeetings() {
        Member member = userDetailsService.getMemberByContextHolder();
        return member.getMeetingMembers().stream()
                .map(MeetingMember::getMeeting)
                .filter(Meeting::isUpcomingMeeting)
                .map(meeting -> UpcomingMeetingResponse.builder()
                        .id(meeting.getId())
                        .title(meeting.getTitle())
                        .meetingDate(meeting.getMeetingDate())
                        .build())
                .toList();
    }

}
