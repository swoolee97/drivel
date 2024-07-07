package com.ebiz.drivel.domain.member.repository;

import com.ebiz.drivel.domain.member.entity.MemberTheme;
import com.ebiz.drivel.domain.member.entity.MemberThemeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberThemeRepository extends JpaRepository<MemberTheme, MemberThemeId> {
}
