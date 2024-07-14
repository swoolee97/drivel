package com.ebiz.drivel.domain.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.course.dto.CourseDTO;
import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.course.entity.CourseLike;
import com.ebiz.drivel.domain.course.repository.CourseLikeRepository;
import com.ebiz.drivel.domain.course.repository.CourseRepository;
import com.ebiz.drivel.domain.course.service.CourseLikeService;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.global.exception.CourseNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CourseLikeTest {
    @Mock
    private CourseLikeRepository courseLikeRepository;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseLikeService courseLikeService;

    @Mock
    private Member member;

    @Mock
    private Course course;
    private CourseLike courseLike;

    @BeforeEach
    public void setUp() {
        courseLike = CourseLike.builder()
                .id(1L)
                .course(course)
                .member(member)
                .build();

        when(userDetailsService.getMemberByContextHolder()).thenReturn(member);
    }

    @Test
    public void 좋아요_했을_때_테스트() {
        when(courseLikeRepository.findByCourseAndMember(course, member)).thenReturn(null);
        assertFalse(courseLikeService.isCourseLikedByMember(course));
    }

    @Test
    public void 좋아요_안했을_때_테스트() {
        when(courseLikeRepository.findByCourseAndMember(course, member)).thenReturn(null);
        assertFalse(courseLikeService.isCourseLikedByMember(course));
    }

    @Test
    public void 내가_좋아요한_코스_찾기_테스트() {
        when(member.getCourseLikes()).thenReturn(List.of(courseLike));
        when(course.countReviews()).thenReturn(1);

        List<CourseDTO> result = courseLikeService.findLikedCourses();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(course.getId(), result.get(0).getId());
    }

    @Test
    public void 좋아요_누르기_테스트() {
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(courseLikeRepository.findByCourseAndMember(course, member)).thenReturn(null);

        courseLikeService.updateCourseLike(course.getId());

        verify(courseLikeRepository, times(1)).save(any(CourseLike.class));
    }

    @Test
    public void 좋아요_없애기_테스트() {
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(courseLikeRepository.findByCourseAndMember(course, member)).thenReturn(courseLike);

        courseLikeService.updateCourseLike(course.getId());

        verify(courseLikeRepository, times(1)).delete(courseLike);
    }

    @Test
    public void 없는_코스에_좋아요_할_때_테스트() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseLikeService.updateCourseLike(1L));
    }
}
