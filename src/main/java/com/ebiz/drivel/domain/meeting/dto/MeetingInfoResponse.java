package com.ebiz.drivel.domain.meeting.dto;

import com.ebiz.drivel.domain.waypoint.entity.Waypoint;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MeetingInfoResponse {
    private Long id;
    private Boolean liked;
    private String title;
    private List<Waypoint> waypoints;
    private Integer startAge;
    private Integer endAge;
    private String gender;
    private String carModel;
    private String imagePath;
}
