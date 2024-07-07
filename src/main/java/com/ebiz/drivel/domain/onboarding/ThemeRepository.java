package com.ebiz.drivel.domain.onboarding;

import com.ebiz.drivel.domain.theme.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
}
