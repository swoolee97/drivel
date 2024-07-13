package com.ebiz.drivel.domain.course.api;

import com.ebiz.drivel.domain.course.dto.CourseDTO;
import com.ebiz.drivel.domain.course.dto.CourseResponse;
import com.ebiz.drivel.domain.course.service.CourseLikeService;
import com.ebiz.drivel.domain.course.service.CourseQueryHelper.OrderBy;
import com.ebiz.drivel.domain.course.service.CourseService;
import com.ebiz.drivel.domain.theme.dto.CourseThemeDTO;
import com.ebiz.drivel.domain.waypoint.dto.CourseDetailResponse;
import com.ebiz.drivel.global.dto.BaseResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    private final CourseLikeService courseLikeService;

    @GetMapping
    public ResponseEntity<Page<CourseDTO>> getCoursesInfo(@RequestParam(required = false) Long themeId,
                                                          @RequestParam(required = false) Long styleId,
                                                          @RequestParam(required = false) Long togetherId,
                                                          @RequestParam(required = false) OrderBy orderBy,
                                                          Pageable pageable) {
        Page<CourseDTO> courses = courseService.getFilteredCourses(themeId, styleId, togetherId, orderBy,
                pageable);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDetailResponse> getCourseDetail(@PathVariable Long id) {
        CourseDetailResponse courseDetailResponse = courseService.getCourseDetail(id);
        return ResponseEntity.ok(courseDetailResponse);
    }

    @PutMapping("/like/{courseId}")
    public ResponseEntity<BaseResponse> updateCourseLike(@PathVariable Long courseId) {
        courseLikeService.updateCourseLike(courseId);
        return ResponseEntity.ok(BaseResponse.builder().build());
    }

    @GetMapping("/liked")
    public ResponseEntity<CourseResponse> findLikedCourses() {
        List<CourseDTO> likedCourses = courseLikeService.findLikedCourses();
        return ResponseEntity.ok(CourseResponse.builder()
                .courses(likedCourses)
                .build());
    }


    @GetMapping("/my-theme")
    public ResponseEntity<List<CourseThemeDTO>> getHomeCoursesByThemes() {
        List<CourseThemeDTO> courseThemes = courseService.getCoursesByMemberTheme();
        return ResponseEntity.ok(courseThemes);
    }

}
