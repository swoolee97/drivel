package com.ebiz.drivel.domain.review.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.course.dto.CourseReviewDTO;
import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.course.repository.CourseRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.review.dto.ReviewDTO;
import com.ebiz.drivel.domain.review.entity.CourseReview;
import com.ebiz.drivel.domain.review.entity.QCourseReview;
import com.ebiz.drivel.domain.review.exception.CourseReviewNotFoundException;
import com.ebiz.drivel.domain.review.repository.ReviewRepository;
import com.ebiz.drivel.global.exception.CourseNotFoundException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserDetailsServiceImpl userDetailsService;
    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;
    private final JPAQueryFactory queryFactory;

    public CourseReview addReview(Long courseId, Integer rating, String comment) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException());
        CourseReview courseReview = CourseReview.builder()
                .comment(comment)
                .rating(rating)
                .course(course)
                .member(userDetailsService.getMemberByContextHolder())
                .build();
        return reviewRepository.save(courseReview);
    }

    public List<ReviewDTO> findMyReviews() {
        Member member = userDetailsService.getMemberByContextHolder();
        List<CourseReview> myCourseReviews = member.getCourseReviews();
        return myCourseReviews.stream()
                .filter(courseReview -> !courseReview.isDeleted())
                .map(ReviewDTO::from)
                .sorted(Comparator.comparingLong(ReviewDTO::getId).reversed())
                .collect(Collectors.toList());
    }

    public CourseReviewDTO findReviewsByCourse(Long id, Pageable pageable) {
        QCourseReview qReview = QCourseReview.courseReview;
        Member member = userDetailsService.getMemberByContextHolder();
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException());

        BooleanBuilder filterBuilder = ReviewQueryHelper.createQueryFilter(id, member);
        OrderSpecifier<?> orderSpecifier = ReviewQueryHelper.getOrderSpecifier(qReview);

        long totalCount = queryFactory.selectFrom(qReview)
                .where(filterBuilder)
                .stream().count();

        List<ReviewDTO> reviews = queryFactory.selectFrom(qReview)
                .where(filterBuilder)
                .offset(pageable.getOffset())
                .orderBy(orderSpecifier)
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(courseReview -> ReviewDTO.from(courseReview))
                .toList();

        return CourseReviewDTO.builder()
                .averageRating(course.calculateAverageRating())
                .reviews(new PageImpl<>(reviews, pageable, totalCount))
                .build();
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        CourseReview courseReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CourseReviewNotFoundException("존재하지 않는 리뷰입니다"));
        courseReview.delete();
    }

}
