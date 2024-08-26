package com.ebiz.drivel.domain.meeting.application;

import com.ebiz.drivel.domain.meeting.dto.ParticipantSummaryDTO;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.entity.MeetingMember;
import com.ebiz.drivel.domain.meeting.entity.MeetingMemberId;
import com.ebiz.drivel.domain.meeting.repository.MeetingMemberRepository;
import com.ebiz.drivel.domain.meeting.util.AgeCalculator;
import com.ebiz.drivel.domain.member.entity.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingMemberService {

    private final MeetingMemberRepository meetingMemberRepository;

    public MeetingMember insertMeetingMember(Meeting meeting, Member member) {
        MeetingMemberId meetingMemberId = MeetingMemberId.builder()
                .meetingId(meeting.getId())
                .memberId(member.getId()).build();
        MeetingMember meetingMember = MeetingMember.builder()
                .meetingMemberId(meetingMemberId)
                .meeting(meeting)
                .isActive(true)
                .member(member).build();
        return meetingMemberRepository.save(meetingMember);
    }

    public Optional<MeetingMember> findMeetingMember(Meeting meeting, Member member) {
        return meetingMemberRepository.findByMeetingAndMemberAndIsActive(meeting, member, true);
    }

    //여기다 만들기
    public ParticipantSummaryDTO summaryParticipants(Meeting meeting) {
        List<Member> members = meeting.getMeetingMembers().stream()
                .filter(MeetingMember::getIsActive)
                .map(MeetingMember::getMember).toList();

        List<String> membersCarCareer = members.stream().map(Member::getCarCareer).map(String::valueOf).toList();
        List<String> membersCarModel = members.stream().map(Member::getCarModel).toList();
        List<String> membersAgeGroup = members.stream().map(Member::getBirth).map(AgeCalculator::getAgeGroup).toList();
        List<String> membersGender = members.stream().map(Member::getGender).map(gender -> {
            if (gender.getDisplayName() == null) {
                return "알수없음";
            }
            return gender.getDisplayName();
        }).toList();

        return ParticipantSummaryDTO.builder()
                .carCareer(summarizeMembers(membersCarCareer))
                .carModel(summarizeMembers(membersCarModel))
                .ageGroup(summarizeMembers(membersAgeGroup))
                .gender(summarizeMembers(membersGender))
                .build();
    }

    public HashMap<String, Integer> summarizeMembers(List<String> values) {
        HashMap<String, Integer> map = new HashMap<>();
        values.forEach(value -> {
            map.put(value, map.getOrDefault(value, 0) + 1);
        });

        return map;
    }
}