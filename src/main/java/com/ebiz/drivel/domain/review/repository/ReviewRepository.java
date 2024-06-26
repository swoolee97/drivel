package com.ebiz.drivel.domain.review.repository;

import com.ebiz.drivel.domain.review.entity.CourseReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<CourseReview, Long> {
}
