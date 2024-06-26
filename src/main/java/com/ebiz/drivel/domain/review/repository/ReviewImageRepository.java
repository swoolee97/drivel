package com.ebiz.drivel.domain.review.repository;

import com.ebiz.drivel.domain.review.entity.CourseReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewImageRepository extends JpaRepository<CourseReviewImage, Long> {
}
