package com.ebiz.drivel.domain.course.repository;

import com.ebiz.drivel.domain.course.entity.CourseLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseLikeRepository extends JpaRepository<CourseLike, Long> {
    CourseLike findCourseLikeByCourseIdAndMemberId(Long courseId, Long memberId);
}
