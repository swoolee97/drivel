package com.ebiz.drivel.domain.magazine.repository;

import com.ebiz.drivel.domain.magazine.entity.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagazineRepository extends JpaRepository<Magazine, Integer> {
}
