package com.ebiz.drivel.domain.meeting.application;

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
import com.ebiz.drivel.domain.meeting.dto.UpcomingMeetingResponse;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.entity.MeetingMember;
import com.ebiz.drivel.domain.meeting.entity.QMeeting;
import com.ebiz.drivel.domain.meeting.exception.MeetingNotFoundException;
import com.ebiz.drivel.domain.meeting.repository.MeetingRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.sse.Alert;
import com.ebiz.drivel.domain.sse.Notification;
import com.ebiz.drivel.domain.sse.NotificationDTO;
import com.ebiz.drivel.domain.sse.NotificationRepository;
import com.ebiz.drivel.domain.sse.SseService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
    private final SseService sseService;
    private final NotificationRepository notificationRepository;

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

    @Transactional
    public void requestJoinMeeting(Long id) {
        Member member = userDetailsService.getMemberByContextHolder();
        Long masterMemberId = meetingRepository.findById(id)
                .orElseThrow(() -> new MeetingNotFoundException(MEETING_NOT_FOUND_EXCEPTION_MESSAGE))
                .getMasterMember().getId();

        Notification notification = Notification.builder()
                .receiverId(masterMemberId)
                .category(Alert.JOIN)
                .title("모임 가입 신청")
                .content(String.format("%s님이 모임 가입 신청을 했어요", member.getNickname()))
                .build();

        notification = notificationRepository.save(notification);

        NotificationDTO notificationDTO = NotificationDTO.from(notification);

        sseService.sendToClient(masterMemberId, Alert.JOIN.toString(), notificationDTO);
    }

}
