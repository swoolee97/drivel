package com.ebiz.drivel.domain.course.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.course.dto.CourseDTO;
import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.course.entity.CourseTheme;
import com.ebiz.drivel.domain.course.entity.QCourse;
import com.ebiz.drivel.domain.course.repository.CourseRepository;
import com.ebiz.drivel.domain.course.service.CourseQueryHelper.OrderBy;
import com.ebiz.drivel.domain.festival.dto.FestivalInfoInterface;
import com.ebiz.drivel.domain.festival.service.FestivalService;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.entity.MemberTheme;
import com.ebiz.drivel.domain.review.dto.ReviewDTO;
import com.ebiz.drivel.domain.theme.dto.CourseThemeDTO;
import com.ebiz.drivel.domain.theme.dto.ThemeDTO;
import com.ebiz.drivel.domain.theme.entity.Theme;
import com.ebiz.drivel.domain.waypoint.dto.CourseDetailResponse;
import com.ebiz.drivel.domain.waypoint.dto.WaypointDTO;
import com.ebiz.drivel.global.exception.CourseNotFoundException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final CourseLikeService courseLikeService;
    private final FestivalService festivalService;
    private final JPAQueryFactory queryFactory;

    public CourseDetailResponse getCourseDetail(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException());
        CourseDTO courseDTO = CourseDTO.from(course, courseLikeService.isCourseLikedByMember(course));
        List<ThemeDTO> themes = course.getCourseThemes().stream().map(ThemeDTO::from).collect(Collectors.toList());
        List<WaypointDTO> waypoints = course.getWaypoints().stream().map(WaypointDTO::from)
                .collect(Collectors.toList());
        int reviewCount = course.countReviews();
        double averageRating = course.calculateAverageRating();
        List<ReviewDTO> reviews = course.getCourseReviews().stream().map(ReviewDTO::from)
                .sorted(Comparator.comparingLong(ReviewDTO::getId).reversed())
                .collect(Collectors.toList());
        List<FestivalInfoInterface> festivals = festivalService.getNearbyFestivalInfo(course);
        List<String> tags = getTagsByCourse(course);
        return CourseDetailResponse.builder()
                .themes(themes)
                .courseInfo(courseDTO)
                .waypoints(waypoints)
                .reviewCount(reviewCount)
                .averageRating(averageRating)
                .reviews(reviews)
                .festivals(festivals)
                .regionName(course.getRegionName())
                .regionDescription(course.getRegionDescription())
                .tags(tags)
                .build();
    }

    private List<String> getTagsByCourse(Course course) {
        List<String> styles = course.getCourseStyles().stream()
                .map(courseStyle -> courseStyle.getStyle().getDisplayName()).collect(Collectors.toList());
        List<String> themes = course.getCourseThemes().stream()
                .map(courseTheme -> courseTheme.getTheme().getDisplayName()).collect(Collectors.toList());
        List<String> togethers = course.getCourseTogethers().stream()
                .map(courseTogether -> courseTogether.getTogether().getDisplayName()).collect(Collectors.toList());

        List<String> tags = new ArrayList<>();
        tags.addAll(styles);
        tags.addAll(themes);
        tags.addAll(togethers);

        return tags;
    }

    public Page<CourseDTO> getFilteredCourses(Long themeId, Long styleId, Long togetherId, OrderBy orderBy,
                                              Pageable pageable) {
        QCourse course = QCourse.course;
        BooleanBuilder filterBuilder = CourseQueryHelper.createQueryFilter(themeId, styleId, togetherId);

        OrderSpecifier<?> orderSpecifier = CourseQueryHelper.getOrderSpecifier(orderBy, course);

        long totalCount = queryFactory.selectFrom(course)
                .where(filterBuilder)
                .fetchCount();

        List<CourseDTO> courses = queryFactory.selectFrom(course)
                .where(filterBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier)
                .fetch()
                .stream().map(CourseDTO::from)
                .toList();

        return new PageImpl<>(courses, pageable, totalCount);
    }

    public List<CourseThemeDTO> getCoursesByMemberTheme() {
        Member member = userDetailsService.getMemberByContextHolder();
        List<Theme> themes = member.getMemberThemes().stream().map(MemberTheme::getTheme).toList();
        List<CourseThemeDTO> courseThemes = new ArrayList<>();
        for (Theme theme : themes) {
            courseThemes.add(CourseThemeDTO.builder()
                    .theme(ThemeDTO.from(theme))
                    .courses(getRandomCoursesByTheme(theme))
                    .build());
        }
        return courseThemes;
    }

    private List<CourseDTO> getRandomCoursesByTheme(Theme theme) {
        List<CourseTheme> courseThemes = theme.getCourseThemes();
        Collections.shuffle(courseThemes, new Random());

        return courseThemes.stream().limit(3)
                .map(CourseTheme::getCourse)
                .map(course -> CourseDTO.from(course, courseLikeService.isCourseLikedByMember(course)))
                .collect(Collectors.toList());
    }

}
