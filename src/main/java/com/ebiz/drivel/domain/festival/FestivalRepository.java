package com.ebiz.drivel.domain.festival;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FestivalRepository extends JpaRepository<Festival, Long> {
}
