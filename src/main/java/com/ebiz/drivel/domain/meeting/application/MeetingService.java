package com.ebiz.drivel.domain.meeting.application;

import static com.ebiz.drivel.domain.meeting.MeetingConstants.MEETING_NOT_FOUND_EXCEPTION_MESSAGE;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.meeting.application.MeetingQueryHelper.OrderBy;
import com.ebiz.drivel.domain.meeting.dto.CreateMeetingRequest;
import com.ebiz.drivel.domain.meeting.dto.CreateMeetingResponse;
import com.ebiz.drivel.domain.meeting.dto.MeetingConditionDTO;
import com.ebiz.drivel.domain.meeting.dto.MeetingDetailResponse;
import com.ebiz.drivel.domain.meeting.dto.MeetingInfoDTO;
import com.ebiz.drivel.domain.meeting.dto.MeetingInfoResponse;
import com.ebiz.drivel.domain.meeting.dto.MeetingMasterInfoDTO;
import com.ebiz.drivel.domain.meeting.dto.MeetingMemberInfoDTO;
import com.ebiz.drivel.domain.meeting.dto.MeetingParticipantsInfoDTO;
import com.ebiz.drivel.domain.meeting.dto.ParticipantSummaryDTO;
import com.ebiz.drivel.domain.meeting.dto.UpcomingMeetingResponse;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.entity.MeetingMember;
import com.ebiz.drivel.domain.meeting.entity.QMeeting;
import com.ebiz.drivel.domain.meeting.exception.AlreadyInactiveMeetingException;
import com.ebiz.drivel.domain.meeting.exception.MeetingMemberNotFoundException;
import com.ebiz.drivel.domain.meeting.exception.MeetingNotFoundException;
import com.ebiz.drivel.domain.meeting.exception.NotMasterMemberException;
import com.ebiz.drivel.domain.meeting.repository.MeetingRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.push.dto.PushType;
import com.ebiz.drivel.domain.push.entity.FcmToken;
import com.ebiz.drivel.domain.push.repository.FcmTokenRepository;
import com.ebiz.drivel.domain.push.service.PushService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private static final String MEETING_CREATE_SUCCESS_MESSAGE = "모임이 생성되었습니다";

    private final MeetingRepository meetingRepository;
    private final MeetingMemberService meetingMemberService;
    private final UserDetailsServiceImpl userDetailsService;
    private final JPAQueryFactory queryFactory;
    private final PushService pushService;
    private final FcmTokenRepository fcmTokenRepository;

    @Transactional
    public CreateMeetingResponse createMeeting(CreateMeetingRequest createMeetingRequest) {
        Meeting meeting = insertMeeting(createMeetingRequest);
        Member member = userDetailsService.getMemberByContextHolder();
        meetingMemberService.insertMeetingMember(meeting, member);
        return CreateMeetingResponse.builder()
                .message(MEETING_CREATE_SUCCESS_MESSAGE)
                .courseId(meeting.getCourse().getId())
                .meetingId(meeting.getId())
                .build();
    }

    public Meeting insertMeeting(CreateMeetingRequest createMeetingRequest) {
        Meeting meeting = createMeetingRequest.toEntity();
        Member member = userDetailsService.getMemberByContextHolder();
        meeting.setMasterMember(member);
        return meetingRepository.save(meeting);
    }

    @Transactional
    public void deleteMeeting(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException(MEETING_NOT_FOUND_EXCEPTION_MESSAGE));
        meeting.delete();
        meeting.getMeetingMembers().forEach(meetingMember -> meetingMember.inActive());
    }

    public MeetingDetailResponse getMeetingDetail(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException(MEETING_NOT_FOUND_EXCEPTION_MESSAGE));

        MeetingConditionDTO meetingConditionDTO = MeetingConditionDTO.from(meeting);
        MeetingMasterInfoDTO meetingMasterInfoDTO = MeetingMasterInfoDTO.from(meeting.getMasterMember());
        List<MeetingMemberInfoDTO> participantsInfo = meeting.getParticipantsInfo();
        MeetingParticipantsInfoDTO meetingParticipantsInfo = createParticipantsInfo(meeting, participantsInfo);
        ParticipantSummaryDTO participantSummaryDTO = meetingMemberService.summaryParticipants(meeting);

        return MeetingDetailResponse.builder()
                .meetingInfo(MeetingInfoDTO.builder()
                        .id(meeting.getId())
                        .title(meeting.getTitle())
                        .description(meeting.getDescription())
                        .status(getMemberParticipantStatus(meeting).toString())
                        .date(meeting.getMeetingDate())
                        .condition(meetingConditionDTO)
                        .masterInfo(meetingMasterInfoDTO)
                        .participantsInfo(meetingParticipantsInfo)
                        .summary(participantSummaryDTO)
                        .build())
                .build();
    }

    private MeetingMember.Status getMemberParticipantStatus(Meeting meeting) {
        Member member = userDetailsService.getMemberByContextHolder();
        if (meeting.isAlreadyJoinedMember(member)) {
            return MeetingMember.Status.JOINED;
        }
        if (meeting.isWaitingRequestMember(member)) {
            return MeetingMember.Status.WAITING;
        }
        return MeetingMember.Status.NONE;
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
                .filter(MeetingMember::getIsActive)
                .map(MeetingMember::getMeeting)
                .filter(Meeting::isUpcomingMeeting)
                .sorted(Comparator.comparing(meeting -> meeting.getMeetingDate()))
                .map(meeting -> UpcomingMeetingResponse.builder()
                        .meetingId(meeting.getId())
                        .title(meeting.getTitle())
                        .meetingDate(meeting.getMeetingDate())
                        .courseId(meeting.getCourse().getId())
                        .build())
                .toList();
    }

    public Page<MeetingInfoResponse> getFilteredMeetings(Long styleId, Long themeId, Long togetherId, Integer age,
                                                         Integer carCareer,
                                                         String carModel, Integer genderId, OrderBy orderBy,
                                                         Pageable pageable) {
        QMeeting meeting = QMeeting.meeting;
        Member member = userDetailsService.getMemberByContextHolder();
        BooleanBuilder filterBuilder = MeetingQueryHelper.createFilterBuilder(styleId, themeId, togetherId, age,
                carCareer, carModel,
                genderId, member, meeting);
        OrderSpecifier<?> orderSpecifier = MeetingQueryHelper.getOrderSpecifier(orderBy, meeting);

        long totalCount = queryFactory.selectFrom(meeting)
                .where(filterBuilder)
                .fetchCount();

        List<MeetingInfoResponse> meetings = queryFactory.selectFrom(meeting)
                .where(filterBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier)
                .fetch()
                .stream().map(MeetingInfoResponse::from)
                .collect(Collectors.toList());

        return new PageImpl<>(meetings, pageable, totalCount);
    }

    @Transactional
    public void leaveMeeting(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException(MEETING_NOT_FOUND_EXCEPTION_MESSAGE));
        Member member = userDetailsService.getMemberByContextHolder();
        MeetingMember meetingMember = meetingMemberService.findMeetingMember(meeting, member)
                .orElseThrow(() -> new MeetingMemberNotFoundException("잘못된 요청입니다"));
        meetingMember.inActive();
    }

    @Transactional
    public void endMeeting(Long id) {
        Member member = userDetailsService.getMemberByContextHolder();
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException(MEETING_NOT_FOUND_EXCEPTION_MESSAGE));
        if (!meeting.getMasterMember().equals(member)) {
            throw new NotMasterMemberException();
        }

        if (!meeting.isActive()) {
            throw new AlreadyInactiveMeetingException();
        }

        meeting.end();
        List<Member> members = meeting.getMeetingMembers().stream()
                .filter(MeetingMember::getIsActive)
                .map(MeetingMember::getMember)
                .toList();
        String title = "모임 완료";
        String body = String.format("%s모임의 유저를 평가해주세요", meeting.getTitle());
        Map<String, String> data = new HashMap<>();
        data.put("type", PushType.FEEDBACK.name());
        data.put("meetingId", meeting.getId().toString());

        members.forEach((activeMember) -> {
            FcmToken token = fcmTokenRepository.findByMemberId(activeMember.getId()).orElse(null);
            if (token != null) {
                try {
                    pushService.sendPushMessage(title, body, data, token.getToken());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
