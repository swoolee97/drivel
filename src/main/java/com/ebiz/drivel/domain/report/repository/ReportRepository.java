package com.ebiz.drivel.domain.report.repository;

import com.ebiz.drivel.domain.report.entity.ReportMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportMember, Long> {
}
