package com.ebiz.drivel.domain.waypoint.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CourseDetailResponse {
    List<WaypointDTO> waypoints;
}
