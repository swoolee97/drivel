package com.ebiz.drivel.domain.course.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.course.dto.CourseDTO;
import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.course.entity.CourseLike;
import com.ebiz.drivel.domain.course.repository.CourseLikeRepository;
import com.ebiz.drivel.domain.course.repository.CourseRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.global.exception.CourseNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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
        CourseLike courseLike = courseLikeRepository.findByCourseAndMember(course, member);
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

    public List<CourseDTO> findLikedCourses() {
        Member member = userDetailsService.getMemberByContextHolder();
        List<CourseLike> courseLikes = member.getCourseLikes().stream()
                .sorted(Comparator.comparingLong(CourseLike::getId).reversed())
                .collect(Collectors.toList());
        return courseLikes.stream()
                .map(courseLike -> courseLike.getCourse())
                .map(CourseDTO::from)
                .collect(Collectors.toList());
    }

}
