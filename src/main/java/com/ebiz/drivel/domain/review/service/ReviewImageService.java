package com.ebiz.drivel.domain.review.service;

import com.ebiz.drivel.domain.review.entity.Review;
import com.ebiz.drivel.domain.review.entity.ReviewImage;
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

    public List<ReviewImage> addReviewImages(Review review, List<MultipartFile> images) {
        List<String> imagePaths = new ArrayList<>();
        if (images != null) {
            imagePaths = s3Service.uploadImageFiles(images, REVIEW_IMAGE_BUCKET_NAME);
        }
        List<ReviewImage> reviewImages = new ArrayList<>();
        imagePaths.forEach(imagePath -> {
            ReviewImage reviewImage = ReviewImage.builder()
                    .review(review)
                    .imagePath(imagePath)
                    .build();
            reviewImages.add(reviewImage);
        });

        return reviewImageRepository.saveAll(reviewImages);
    }

}
