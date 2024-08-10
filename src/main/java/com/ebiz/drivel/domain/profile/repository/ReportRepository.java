package com.ebiz.drivel.domain.profile.repository;

import com.ebiz.drivel.domain.profile.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
