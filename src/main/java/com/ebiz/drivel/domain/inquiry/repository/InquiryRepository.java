package com.ebiz.drivel.domain.inquiry.repository;

import com.ebiz.drivel.domain.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

//    List<Inquiry> findByMemberId(Long memberId);
}
