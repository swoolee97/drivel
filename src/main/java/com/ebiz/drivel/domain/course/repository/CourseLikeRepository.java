package com.ebiz.drivel.domain.course.repository;

import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.course.entity.CourseLike;
import com.ebiz.drivel.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseLikeRepository extends JpaRepository<CourseLike, Long> {
    CourseLike findByCourseAndMember(Course course, Member member);
}
