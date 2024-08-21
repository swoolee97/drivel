package com.ebiz.drivel.domain.meeting.application;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.meeting.dto.MeetingNoticeDTO;
import com.ebiz.drivel.domain.meeting.dto.MeetingNoticeResponse;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.entity.MeetingNotice;
import com.ebiz.drivel.domain.meeting.exception.MeetingNotFoundException;
import com.ebiz.drivel.domain.meeting.exception.MeetingNoticeNotFoundException;
import com.ebiz.drivel.domain.meeting.repository.MeetingNoticeRepository;
import com.ebiz.drivel.domain.meeting.repository.MeetingRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetingNoticeService {

    private final UserDetailsServiceImpl userDetailsService;
    private final MeetingRepository meetingRepository;
    private final MeetingNoticeRepository meetingNoticeRepository;
    private final JPAQueryFactory queryFactory;

    public void addMeetingNotice(MeetingNoticeDTO meetingNoticeDTO) {
        Member member = userDetailsService.getMemberByContextHolder();
        Meeting meeting = meetingRepository.findById(meetingNoticeDTO.getMeetingId())
                .orElseThrow(() -> new MeetingNotFoundException("찾을 수 없는 모임입니다"));
        MeetingNotice meetingNotice = MeetingNotice.builder()
                .writer(member)
                .meeting(meeting)
                .content(meetingNoticeDTO.getContent())
                .build();
        meetingNoticeRepository.save(meetingNotice);
    }

    @Transactional
    public void deleteMeetingNotice(Long id) {
        MeetingNotice meetingNotice = meetingNoticeRepository.findById(id)
                .orElseThrow(() -> new MeetingNoticeNotFoundException("찾을 수 없는 공지입니다"));
        meetingNotice.delete();
    }

    public MeetingNoticeResponse getLatestMeetingNotice(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId).get();
        List<MeetingNotice> notices = meeting.getMeetingNotices();
        if (notices.isEmpty()) {
            return null;
        }
        MeetingNotice lastNotice = notices.get(notices.size() - 1);
        if (lastNotice.isDeleted()) {
            return null;
        }
        return MeetingNoticeResponse.from(lastNotice);
    }

}
