package com.ebiz.drivel.domain.course.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CourseJoinedDataDTO {
    private Long id;
    private String title;
    private String imagePath;
    private String address;
    private String region;
    private double rating;
    private int reviewCount;
    private String waypoints;
}
