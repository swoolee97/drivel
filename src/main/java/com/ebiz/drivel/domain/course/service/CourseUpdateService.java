package com.ebiz.drivel.domain.course.service;

import com.ebiz.drivel.domain.course.dto.CourseJoinedDataDTO;
import com.ebiz.drivel.domain.course.dto.TestDTO;
import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.course.repository.CourseRepository;
import com.ebiz.drivel.global.service.S3Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourseUpdateService {

    @Value("${cloud.aws.s3.courseDataBucketName}")
    private String COURSE_DATA_BUCKET_NAME;
    private static final String COURSE_DATA_FILE_NAME = "course-data";

    private final CourseRepository courseRepository;
    private final S3Service s3Service;

    @Transactional
    @Scheduled(cron = "0 0 4 * * ?")
    public void updateCourseData() throws JsonProcessingException {
        List<Course> courses = courseRepository.findAll();

        List<CourseJoinedDataDTO> joinedCourses = courses.stream().map(course ->
                CourseJoinedDataDTO.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .imagePath(course.getImagePath())
                        .rating(course.calculateAverageRating())
                        .reviewCount(course.countReviews())
                        .waypoints(course.generateWaypointString())
                        .build()).collect(Collectors.toList());
        TestDTO testDTO = TestDTO.builder()
                .courses(joinedCourses)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String courseJsonData = objectMapper.writeValueAsString(testDTO);
        s3Service.uploadJsonToS3(COURSE_DATA_BUCKET_NAME, courseJsonData, COURSE_DATA_FILE_NAME);
        log.info("코스 데이터 업데이트 완료");
    }

}
