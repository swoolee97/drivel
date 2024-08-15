package com.ebiz.drivel.domain.meeting.application;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.meeting.application.MeetingQueryHelper.OrderBy;
import com.ebiz.drivel.domain.meeting.dto.CreateMeetingRequest;
import com.ebiz.drivel.domain.meeting.dto.CreateMeetingResponse;
import com.ebiz.drivel.domain.meeting.dto.JoinRequestDecisionDTO;
import com.ebiz.drivel.domain.meeting.dto.MeetingConditionDTO;
import com.ebiz.drivel.domain.meeting.dto.MeetingDetailResponse;
import com.ebiz.drivel.domain.meeting.dto.MeetingInfoDTO;
import com.ebiz.drivel.domain.meeting.dto.MeetingInfoResponse;
import com.ebiz.drivel.domain.meeting.dto.MeetingJoinRequestDTO;
import com.ebiz.drivel.domain.meeting.dto.MeetingMasterInfoDTO;
import com.ebiz.drivel.domain.meeting.dto.MeetingMemberInfoDTO;
import com.ebiz.drivel.domain.meeting.dto.MeetingParticipantsInfoDTO;
import com.ebiz.drivel.domain.meeting.dto.UpcomingMeetingResponse;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.entity.MeetingJoinRequest;
import com.ebiz.drivel.domain.meeting.entity.MeetingJoinRequest.Status;
import com.ebiz.drivel.domain.meeting.entity.MeetingMember;
import com.ebiz.drivel.domain.meeting.entity.QMeeting;
import com.ebiz.drivel.domain.meeting.exception.AlreadyRequestedJoinMeetingException;
import com.ebiz.drivel.domain.meeting.exception.MeetingJoinRequestNotFoundException;
import com.ebiz.drivel.domain.meeting.exception.MeetingNotFoundException;
import com.ebiz.drivel.domain.meeting.repository.MeetingJoinRequestRepository;
import com.ebiz.drivel.domain.meeting.repository.MeetingRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.sse.Alert;
import com.ebiz.drivel.domain.sse.Alert.AlertCategory;
import com.ebiz.drivel.domain.sse.AlertDTO;
import com.ebiz.drivel.domain.sse.AlertRepository;
import com.ebiz.drivel.domain.sse.AlertService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

    private static final String MEETING_NOT_FOUND_EXCEPTION_MESSAGE = "찾을 수 없는 모임입니다";
    private static final String MEETING_CREATE_SUCCESS_MESSAGE = "모임이 생성되었습니다";

    private final MeetingRepository meetingRepository;
    private final MeetingMemberService meetingMemberService;
    private final UserDetailsServiceImpl userDetailsService;
    private final JPAQueryFactory queryFactory;
    private final AlertService alertService;
    private final AlertRepository alertRepository;
    private final MeetingJoinRequestRepository meetingJoinRequestRepository;

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
                        .status(getMemberParticipantStatus(meeting).toString())
                        .date(meeting.getMeetingDate())
                        .condition(meetingConditionDTO)
                        .masterInfo(meetingMasterInfoDTO)
                        .participantsInfo(meetingParticipantsInfo)
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
        BooleanBuilder filterBuilder = MeetingQueryHelper.createFilterBuilder(styleId, themeId, togetherId, age,
                carCareer, carModel,
                genderId, meeting);
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

    /*
     * 모임 가입 신청을 할 땐 모임장에게 요청을 보낸다.
     * 가입 신청을 할 땐 3개의 기능이 동작된다.
     * 1. SSE 알림 2. 푸시알림 3. 가입 신청 저장
     */
    @Transactional
    public void requestJoinMeeting(Long id) {
        Member member = userDetailsService.getMemberByContextHolder();
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException(MEETING_NOT_FOUND_EXCEPTION_MESSAGE));

        //이미 가입 요청했는지 확인
        if (meeting.isWaitingRequestMember(member)) {
            throw new MeetingJoinRequestNotFoundException("이미 가입 신청한 모임입니다");
        }

        // 이미 가입된 멤버인지 확인
        if (meeting.isAlreadyJoinedMember(member)) {
            throw new AlreadyRequestedJoinMeetingException("이미 가입된 모임입니다");
        }

        sendMeetingJoinRequestAlert(member, meeting);
        saveMeetingJoinRequest(member, meeting);
    }

    @Transactional
    public void acceptJoinMeeting(JoinRequestDecisionDTO request) {
        MeetingJoinRequest meetingJoinRequest = meetingJoinRequestRepository.findById(request.getId())
                .orElseThrow(() -> new MeetingJoinRequestNotFoundException("찾을 수 없는 요청입니다"));
        if (meetingJoinRequest.isAlreadyDecidedRequest()) {
            throw new MeetingJoinRequestNotFoundException("이미 처리가 된 요청입니다");
        }

        Meeting meeting = meetingJoinRequest.getMeeting();
        Member member = meetingJoinRequest.getMember();
        if (request.isAccepted()) {
            meetingMemberService.insertMeetingMember(meeting, member);
            meetingJoinRequest.accept();
            Alert alert = Alert.builder()
                    .meetingId(meeting.getId())
                    .receiverId(member.getId())
                    .alertCategory(AlertCategory.ACCEPTED)
                    .title("수락")
                    .content("가입되었습니다")
                    .build();
            alertRepository.save(alert);
            AlertDTO alertDTO = AlertDTO.from(alert);
            alertService.sendToClient(member.getId(), AlertCategory.ACCEPTED.toString(), alertDTO);
        } else {
            meetingJoinRequest.reject();
            Alert alert = Alert.builder()
                    .meetingId(meeting.getId())
                    .receiverId(member.getId())
                    .alertCategory(AlertCategory.ACCEPTED)
                    .title("거절")
                    .content("가입 신청이 거절되었습니다")
                    .build();
            alertRepository.save(alert);
            AlertDTO alertDTO = AlertDTO.from(alert);
            alertService.sendToClient(member.getId(), AlertCategory.REJECTED.toString(), alertDTO);
        }
    }

    private void sendMeetingJoinRequestAlert(Member member, Meeting meeting) {
        Long masterMemberId = meeting.getMasterMember().getId();

        Alert alert = Alert.builder()
                .meetingId(meeting.getId())
                .receiverId(masterMemberId)
                .alertCategory(AlertCategory.JOIN)
                .title("모임 가입 신청")
                .content(String.format("%s님이 모임 가입 신청을 했어요", member.getNickname()))
                .build();
        alert = alertRepository.save(alert);

        AlertDTO alertDTO = AlertDTO.from(alert);

        alertService.sendToClient(masterMemberId, AlertCategory.JOIN.toString(), alertDTO);
    }

    private void saveMeetingJoinRequest(Member member, Meeting meeting) {
        MeetingJoinRequest meetingJoinRequest = MeetingJoinRequest.builder()
                .meeting(meeting)
                .member(member)
                .status(Status.WAITING)
                .build();
        meetingJoinRequestRepository.save(meetingJoinRequest);
    }

    public List<MeetingJoinRequestDTO> getJoinRequests() {
        Member member = userDetailsService.getMemberByContextHolder();
        List<Meeting> meetings = meetingRepository.findByMasterMemberAndIsActiveIsTrue(member);
        List<MeetingJoinRequestDTO> requests = new ArrayList<>();
        meetings.forEach(meeting -> {
            List<MeetingJoinRequest> joinRequests = meeting.getJoinRequests().stream()
                    .filter(request -> !request.isAlreadyDecidedRequest()).toList();
            if (!joinRequests.isEmpty()) {
                List<Member> requestedMembers = joinRequests.stream().map(request -> request.getMember()).toList();
                requests.add(MeetingJoinRequestDTO.from(meeting, joinRequests));
            }
        });
        return requests;
    }
}
