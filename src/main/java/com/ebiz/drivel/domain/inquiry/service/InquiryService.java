package com.ebiz.drivel.domain.inquiry.service;

import com.ebiz.drivel.domain.inquiry.dto.InquiryDTO;
import com.ebiz.drivel.domain.inquiry.entity.Inquiry;
import com.ebiz.drivel.domain.inquiry.repository.InquiryRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.profile.exception.ProfileException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryRepository inquiryRepository;
    private final MemberRepository memberRepository;

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> ProfileException.userNotFound());
    }

    private Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("유저 이메일을 찾을 수 없습니다."))
                .getId();
    }

    @Transactional
    public InquiryDTO createInquiry(InquiryDTO inquiryDTO) {
        Long memberId = getCurrentMemberId();
        Member member = findMemberById(memberId);

        Inquiry inquiry = Inquiry.builder()
                .title(inquiryDTO.getTitle())
                .content(inquiryDTO.getContent())
                .member(member)
                .build();

        Inquiry savedInquiry = inquiryRepository.save(inquiry);

        return InquiryDTO.from(savedInquiry);
    }
}
