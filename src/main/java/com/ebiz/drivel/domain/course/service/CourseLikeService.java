package com.ebiz.drivel.domain.course.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.course.repository.CourseLikeRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseLikeService {

    private final CourseLikeRepository courseLikeRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public boolean isCourseLikedByMember(Course course) {
        Member member = userDetailsService.getMemberByContextHolder();
        return courseLikeRepository.findByCourseAndMember(course, member).isPresent();
    }

}
