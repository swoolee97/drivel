package com.ebiz.drivel.domain.course.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TestDTO {
    List<CourseJoinedDataDTO> courses;
}
