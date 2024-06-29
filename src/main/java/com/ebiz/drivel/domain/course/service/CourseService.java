package com.ebiz.drivel.domain.course.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.course.dto.CourseDTO;
import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.course.entity.CourseTheme;
import com.ebiz.drivel.domain.course.repository.CourseRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.entity.MemberTheme;
import com.ebiz.drivel.domain.theme.dto.CourseThemeDTO;
import com.ebiz.drivel.domain.theme.dto.ThemeDTO;
import com.ebiz.drivel.domain.theme.entity.Theme;
import com.ebiz.drivel.global.exception.CourseNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final CourseLikeService courseLikeService;

    public Course findCourse(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException());
    }

    public List<CourseThemeDTO> getCoursesByTheme() {
        Member member = userDetailsService.getMemberByContextHolder();
        List<Theme> themes = member.getMemberThemes().stream().map(MemberTheme::getTheme).toList();
        List<CourseThemeDTO> courseThemes = new ArrayList<>();
        for (Theme theme : themes) {
            courseThemes.add(CourseThemeDTO.builder()
                    .theme(ThemeDTO.from(theme))
                    .courses(getRandomCoursesByTheme(theme))
                    .build());
        }
        return courseThemes;
    }

    private List<CourseDTO> getRandomCoursesByTheme(Theme theme) {
        List<CourseTheme> courseThemes = theme.getCourseThemes();
        Collections.shuffle(courseThemes, new Random());

        return courseThemes.stream().limit(3)
                .map(CourseTheme::getCourse)
                .map(course -> CourseDTO.from(course, courseLikeService.isCourseLikedByMember(course)))
                .collect(Collectors.toList());
    }

}
