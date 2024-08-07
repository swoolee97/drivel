package com.ebiz.drivel.domain.review.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.course.repository.CourseRepository;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.review.dto.AddReviewRequest;
import com.ebiz.drivel.domain.review.dto.ReviewDTO;
import com.ebiz.drivel.domain.review.entity.CourseReview;
import com.ebiz.drivel.domain.review.entity.QCourseReview;
import com.ebiz.drivel.domain.review.repository.ReviewRepository;
import com.ebiz.drivel.global.exception.CourseNotFoundException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserDetailsServiceImpl userDetailsService;
    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;
    private final JPAQueryFactory queryFactory;

    public CourseReview addReview(AddReviewRequest addReviewRequest) {
        Course course = courseRepository.findById(addReviewRequest.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException());
        CourseReview courseReview = CourseReview.builder()
                .comment(addReviewRequest.getComment())
                .rating(addReviewRequest.getRating())
                .course(course)
                .member(userDetailsService.getMemberByContextHolder())
                .build();
        return reviewRepository.save(courseReview);
    }

    public List<ReviewDTO> findMyReviews() {
        Member member = userDetailsService.getMemberByContextHolder();
        List<CourseReview> myCourseReviews = member.getCourseReviews();
        return myCourseReviews.stream()
                .map(ReviewDTO::from)
                .sorted(Comparator.comparingLong(ReviewDTO::getId).reversed())
                .collect(Collectors.toList());
    }

    public Page<ReviewDTO> findReviewsByCourse(Long id, Pageable pageable) {
        QCourseReview qReview = QCourseReview.courseReview;
        BooleanBuilder filterBuilder = ReviewQueryHelper.createQueryFilter(id);
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
                .stream().map(courseReview -> ReviewDTO.from(courseReview))
                .toList();

        return new PageImpl<>(reviews, pageable, totalCount);
    }

}
