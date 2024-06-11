package com.ebiz.drivel.domain.waypoint.dto;

import com.ebiz.drivel.domain.course.dto.CourseDTO;
import com.ebiz.drivel.domain.review.dto.ReviewDTO;
import com.ebiz.drivel.domain.theme.dto.ThemeDTO;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CourseDetailResponse {
    private boolean liked;
    private CourseDTO courseInfo;
    private List<ThemeDTO> themes;
    private List<WaypointDTO> waypoints;
    private List<ReviewDTO> reviews;
}
