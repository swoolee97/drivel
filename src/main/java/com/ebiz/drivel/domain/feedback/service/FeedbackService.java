package com.ebiz.drivel.domain.feedback.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.feedback.entity.Feedback;
import com.ebiz.drivel.domain.feedback.enums.BaseFeedback;
import com.ebiz.drivel.domain.feedback.enums.NegativeFeedback;
import com.ebiz.drivel.domain.feedback.enums.PositiveFeedback;
import com.ebiz.drivel.domain.feedback.exception.DuplicateFeedbackException;
import com.ebiz.drivel.domain.feedback.exception.EmptyFeedbackException;
import com.ebiz.drivel.domain.feedback.repository.FeedbackRepository;
import com.ebiz.drivel.domain.meeting.entity.Meeting;
import com.ebiz.drivel.domain.meeting.repository.MeetingRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final MemberRepository memberRepository;
    private final MeetingRepository meetingRepository;

    public double calculateScore(List<? extends BaseFeedback> positiveFeedbacks) {
        return positiveFeedbacks.stream()
                .mapToDouble(BaseFeedback::getScore)
                .sum();
    }

    @Transactional
    public void saveFeedbacks(boolean isPositive, Long meetingId, Long targetMemberId,
                              List<Integer> feedbackIds) {

        if (feedbackIds == null || feedbackIds.isEmpty()) {
            throw new EmptyFeedbackException();
        }

        Member member = userDetailsService.getMemberByContextHolder();
        Member targetMember = memberRepository.findById(targetMemberId).get();
        Meeting meeting = meetingRepository.findById(meetingId).get();

        if (feedbackRepository.existsByMemberAndTargetMemberAndMeeting(member, targetMember, meeting)) {
            throw new DuplicateFeedbackException();
        }

        List<? extends BaseFeedback> feedbacks;
        double difference = 0;
        if (isPositive) {
            feedbacks = savePositiveFeedbacks(member, targetMember, meeting, feedbackIds);
        } else {
            feedbacks = saveNegativeFeedbacks(member, targetMember, meeting, feedbackIds);
        }
        difference = calculateScore(feedbacks);

        member.updateScore(difference);
    }

    public List<? extends BaseFeedback> savePositiveFeedbacks(Member member, Member targetMember, Meeting meeting,
                                                              List<Integer> feedbackIds) {
        List<PositiveFeedback> positiveFeedbackEnums = feedbackIds.stream()
                .map(PositiveFeedback::findById)
                .toList();

        List<Feedback> positiveFeedbacks = positiveFeedbackEnums.stream()
                .map(positiveFeedback -> Feedback.builder()
                        .isPositive(true)
                        .member(member)
                        .targetMember(targetMember)
                        .meeting(meeting)
                        .build())
                .toList();
        feedbackRepository.saveAll(positiveFeedbacks);
        return positiveFeedbackEnums;
    }

    public List<? extends BaseFeedback> saveNegativeFeedbacks(Member member, Member targetMember, Meeting meeting,
                                                              List<Integer> feedbackIds) {
        List<NegativeFeedback> negativeFeedbackEnums = feedbackIds.stream()
                .map(NegativeFeedback::findById)
                .toList();
        List<Feedback> negativeFeedbacks = negativeFeedbackEnums.stream()
                .map(positiveFeedback -> Feedback.builder()
                        .isPositive(false)
                        .member(member)
                        .targetMember(targetMember)
                        .meeting(meeting)
                        .build())
                .toList();
        feedbackRepository.saveAll(negativeFeedbacks);
        return negativeFeedbackEnums;
    }

    public List<Long> findMyFeedbackMembers(Long meetingId) {
        Member member = userDetailsService.getMemberByContextHolder();
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        List<Feedback> feedbacks = feedbackRepository.findByMemberAndMeeting(member, meeting);
        return feedbacks.stream()
                .map(Feedback::getTargetMember)
                .distinct()
                .map(Member::getId)
                .toList();
    }

}
