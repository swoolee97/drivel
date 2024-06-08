package com.ebiz.drivel.domain.course.repository;

import com.ebiz.drivel.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
