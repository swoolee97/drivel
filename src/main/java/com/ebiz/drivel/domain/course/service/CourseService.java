package com.ebiz.drivel.domain.course.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.course.entity.CourseLike;
import com.ebiz.drivel.domain.course.repository.CourseLikeRepository;
import com.ebiz.drivel.domain.course.repository.CourseRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.global.exception.CourseNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseLikeRepository courseLikeRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public Course findCourse(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException());
    }

    public void updateCourseLike(Long courseId) {
        Member member = userDetailsService.getMemberByContextHolder();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException());
        CourseLike courseLike = courseLikeRepository.findCourseLikeByCourseIdAndMemberId(courseId, member.getId());
        if (courseLike == null) {
            saveCourseLike(course, member);
            return;
        }
        deleteCourseLike(courseLike);
    }

    public void saveCourseLike(Course course, Member member) {
        courseLikeRepository.save(CourseLike.builder()
                .member(member)
                .course(course)
                .build());
    }

    public void deleteCourseLike(CourseLike courseLike) {
        courseLikeRepository.delete(courseLike);
    }

}
