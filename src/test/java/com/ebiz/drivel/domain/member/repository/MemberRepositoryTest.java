package com.ebiz.drivel.domain.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.ebiz.drivel.BaseRepositoryTest;
import com.ebiz.drivel.domain.member.entity.Member;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

class MemberRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TestEntityManager entityManager;

    private Member member;

    @BeforeEach
    void setUp() {
        // given
        member = Member.builder()
                .email("exists@example.com")
                .password("exists_password")
                .nickname("exists_nickname")
                .build();
        entityManager.persist(member);
        entityManager.flush();
    }

    @Test
    void 이메일로_존재하는_멤버_찾는_메서드() {
        // when
        Optional<Member> found = memberRepository.findMemberByEmail(member.getEmail());

        // then
        assertThat(found.isPresent());
        assertThat(found.get().getEmail().equals("exists@example.com"));
    }

    @Test
    void 이메일로_존재하지_않는_멤버_찾는_메서드() {
        // when
        Optional<Member> found = memberRepository.findMemberByEmail("nonexistent@example.com");

        // then
        assertThat(found.isEmpty());
    }

    @Test
    void 닉네임으로_존재하는_멤버_찾는_메서드() {
        // when
        boolean exists = memberRepository.existsByNickname("exists_nickname");

        // then
        assertThat(exists).isTrue();
    }
}
