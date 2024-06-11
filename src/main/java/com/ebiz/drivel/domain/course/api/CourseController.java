package com.ebiz.drivel.domain.course.api;

import com.ebiz.drivel.domain.course.dto.CourseDTO;
import com.ebiz.drivel.domain.course.dto.CourseResponse;
import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.course.service.CourseService;
import com.ebiz.drivel.domain.waypoint.dto.CourseDetailResponse;
import com.ebiz.drivel.domain.waypoint.dto.WaypointDTO;
import com.ebiz.drivel.global.dto.BaseResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<CourseDetailResponse> findWaypointsByCourse(@PathVariable Long id) {
        Course course = courseService.findCourse(id);
        List<WaypointDTO> waypoints = course.getWaypoints().stream().map(WaypointDTO::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CourseDetailResponse.builder()
                .waypoints(waypoints)
                .build());
    }

    @PutMapping("/like/{courseId}")
    public ResponseEntity<BaseResponse> updateCourseLike(@PathVariable Long courseId) {
        courseService.updateCourseLike(courseId);
        return ResponseEntity.ok(BaseResponse.builder().build());
    }

    @GetMapping("/liked")
    public ResponseEntity<CourseResponse> findLikedCourses() {
        List<CourseDTO> likedCourses = courseService.findLikedCourses();
        return ResponseEntity.ok(CourseResponse.builder()
                .courses(likedCourses)
                .build());
    }

}
