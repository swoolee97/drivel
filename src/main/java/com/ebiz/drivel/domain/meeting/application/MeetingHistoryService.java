package com.ebiz.drivel.domain.meeting.application;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.meeting.dto.MeetingHistoryDTO;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.entity.Meeting.MeetingStatus;
import com.ebiz.drivel.domain.meeting.entity.MeetingMember;
import com.ebiz.drivel.domain.meeting.exception.ForbiddenMeetingForbiddenException;
import com.ebiz.drivel.domain.meeting.repository.MeetingRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.exception.MemberNotFoundException;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingHistoryService {
    private final UserDetailsServiceImpl userDetailsService;
    private final MeetingRepository meetingRepository;
    private final MemberRepository memberRepository;

    public List<MeetingHistoryDTO> getCreatedMeetings() {
        Member member = userDetailsService.getMemberByContextHolder();
        List<Meeting> createdMeetings = meetingRepository.findMeetingsByMasterMemberAndStatus
                (member, MeetingStatus.ACTIVE);
        return createdMeetings.stream().map(MeetingHistoryDTO::from).toList();
    }

    public List<MeetingHistoryDTO> getCreatedMeetings(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("찾을 수 없는 유저입니다"));
        if (member.isProfileLocked()) {
            throw new ForbiddenMeetingForbiddenException("금지된 모임 히스토리");
        }
        List<Meeting> createdMeetings = meetingRepository.findMeetingsByMasterMemberAndStatus
                (member, MeetingStatus.ACTIVE);
        return createdMeetings.stream().map(MeetingHistoryDTO::from).toList();
    }

    public List<MeetingHistoryDTO> getParticipatingMeetings() {
        Member member = userDetailsService.getMemberByContextHolder();
        return member.getMeetingMembers().stream()
                .filter(MeetingMember::getIsActive)
                .map(MeetingMember::getMeeting)
                .filter(meeting -> meeting.isActive())
                .map(MeetingHistoryDTO::from)
                .toList();
    }

    public List<MeetingHistoryDTO> getParticipatingMeetings(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("찾을 수 없는 유저입니다"));
        if (member.isProfileLocked()) {
            throw new ForbiddenMeetingForbiddenException("금지된 모임 히스토리");
        }
        return member.getMeetingMembers().stream()
                .filter(MeetingMember::getIsActive)
                .map(MeetingMember::getMeeting)
                .filter(meeting -> meeting.isActive())
                .map(MeetingHistoryDTO::from)
                .toList();
    }
}
