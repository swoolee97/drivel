package com.ebiz.drivel.domain.review.service;

import com.ebiz.drivel.domain.review.entity.CourseReview;
import com.ebiz.drivel.domain.review.entity.CourseReviewImage;
import com.ebiz.drivel.domain.review.repository.ReviewImageRepository;
import com.ebiz.drivel.global.service.S3Service;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReviewImageService {

    private final S3Service s3Service;
    private final ReviewImageRepository reviewImageRepository;
    @Value("${cloud.aws.s3.reviewImageBucketName}")
    private String REVIEW_IMAGE_BUCKET_NAME;

    public List<CourseReviewImage> addReviewImages(CourseReview courseReview, List<MultipartFile> images) {
        List<String> imagePaths = new ArrayList<>();
        if (images != null) {
            imagePaths = s3Service.uploadImageFiles(images, REVIEW_IMAGE_BUCKET_NAME);
        }
        List<CourseReviewImage> courseReviewImages = new ArrayList<>();
        imagePaths.forEach(imagePath -> {
            CourseReviewImage courseReviewImage = CourseReviewImage.builder()
                    .courseReview(courseReview)
                    .imagePath(imagePath)
                    .build();
            courseReviewImages.add(courseReviewImage);
        });

        return reviewImageRepository.saveAll(courseReviewImages);
    }

}
