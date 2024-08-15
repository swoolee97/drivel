package com.ebiz.drivel.domain.report;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportMember, Long> {
}
