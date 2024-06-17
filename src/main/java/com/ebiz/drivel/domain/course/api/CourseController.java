package com.ebiz.drivel.domain.course.api;

import com.ebiz.drivel.domain.course.dto.CourseDTO;
import com.ebiz.drivel.domain.course.dto.CourseResponse;
import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.course.service.CourseLikeService;
import com.ebiz.drivel.domain.course.service.CourseService;
import com.ebiz.drivel.domain.festival.dto.FestivalInfoInterface;
import com.ebiz.drivel.domain.festival.service.FestivalService;
import com.ebiz.drivel.domain.review.dto.ReviewDTO;
import com.ebiz.drivel.domain.theme.dto.ThemeDTO;
import com.ebiz.drivel.domain.waypoint.dto.CourseDetailResponse;
import com.ebiz.drivel.domain.waypoint.dto.WaypointDTO;
import com.ebiz.drivel.global.dto.BaseResponse;
import java.util.Comparator;
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
    private final FestivalService festivalService;
    private final CourseLikeService courseLikeService;

    @GetMapping("/{id}")
    public ResponseEntity<CourseDetailResponse> findWaypointsByCourse(@PathVariable Long id) {
        Course course = courseService.findCourse(id);
        boolean liked = courseLikeService.isCourseLikedByMember(course);
        CourseDTO courseDTO = CourseDTO.from(course);
        List<ThemeDTO> themes = course.getCourseThemes().stream().map(ThemeDTO::from).collect(Collectors.toList());
        List<WaypointDTO> waypoints = course.getWaypoints().stream().map(WaypointDTO::from)
                .collect(Collectors.toList());
        int reviewCount = course.countReviews();
        double averageRating = course.calculateAverageRating();
        List<ReviewDTO> reviews = course.getReviews().stream().map(ReviewDTO::from)
                .sorted(Comparator.comparingLong(ReviewDTO::getId).reversed())
                .collect(Collectors.toList());
        List<FestivalInfoInterface> festivals = festivalService.getNearbyFestivalInfo(course);
        return ResponseEntity.ok(CourseDetailResponse.builder()
                .liked(liked)
                .themes(themes)
                .courseInfo(courseDTO)
                .waypoints(waypoints)
                .reviewCount(reviewCount)
                .averageRating(averageRating)
                .reviews(reviews)
                .festivals(festivals)
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
