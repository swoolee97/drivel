package com.ebiz.drivel.domain.course.service;

import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.course.dto.CourseDTO;
import com.ebiz.drivel.domain.course.dto.CourseDetailDTO;
import com.ebiz.drivel.domain.course.dto.CourseTagDTO;
import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.course.entity.QCourse;
import com.ebiz.drivel.domain.course.repository.CourseRepository;
import com.ebiz.drivel.domain.course.service.CourseQueryHelper.OrderBy;
import com.ebiz.drivel.domain.festival.dto.FestivalInfoInterface;
import com.ebiz.drivel.domain.festival.service.FestivalService;
import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.entity.MemberRegion;
import com.ebiz.drivel.domain.member.entity.MemberStyle;
import com.ebiz.drivel.domain.member.entity.MemberTheme;
import com.ebiz.drivel.domain.member.entity.MemberTogether;
import com.ebiz.drivel.domain.onboarding.entity.Style;
import com.ebiz.drivel.domain.onboarding.entity.Together;
import com.ebiz.drivel.domain.place.dto.PlaceInterface;
import com.ebiz.drivel.domain.place.service.PlaceService;
import com.ebiz.drivel.domain.spot.application.SpotService;
import com.ebiz.drivel.domain.spot.dto.SpotInterface;
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
    private final SpotService spotService;
    private final CourseLikeService courseLikeService;
    private final FestivalService festivalService;
    private final PlaceService placeService;
    private final JPAQueryFactory queryFactory;

    public CourseDetailResponse getCourseDetail(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException());
        CourseDetailDTO courseDTO = CourseDetailDTO.from(course, courseLikeService.isCourseLikedByMember(course));
        List<ThemeDTO> themes = course.getCourseThemes().stream().map(ThemeDTO::from).collect(Collectors.toList());
        List<WaypointDTO> waypoints = course.getWaypoints().stream().map(WaypointDTO::from)
                .collect(Collectors.toList());

        List<FestivalInfoInterface> festivals = festivalService.getNearbyFestivalInfo(course);
        List<String> tags = getTagsByCourse(course);
        List<PlaceInterface> places = placeService.getPlacesNearByCourse(waypoints.get(0),
                waypoints.get(waypoints.size() - 1));
        List<SpotInterface> spots = spotService.getSpotsByCourse(waypoints.get(0), waypoints.get(waypoints.size() - 1));

        return CourseDetailResponse.builder()
                .themes(themes)
                .courseInfo(courseDTO)
                .waypoints(waypoints)
                .festivals(festivals)
                .regionName(course.getRegionName())
                .regionDescription(course.getRegionDescription())
                .places(places)
                .spots(spots)
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

    public Page<CourseDetailDTO> getFilteredCourses(Long regionId, Long themeId, Long styleId, Long togetherId,
                                                    OrderBy orderBy, Pageable pageable) {
        QCourse qCourse = QCourse.course;
        BooleanBuilder filterBuilder = new BooleanBuilder();
        Member member = userDetailsService.getMemberByContextHolder();
        System.out.println(member.getId());
        member.getMemberTogethers()
                .forEach(memberTogether -> System.out.println(memberTogether.getTogether().getDisplayName()));
        member.getMemberStyles().forEach(memberStyle -> System.out.println(memberStyle.getStyle().getDisplayName()));
        member.getMemberThemes().forEach(memberTheme -> System.out.println(memberTheme.getTheme().getDisplayName()));

        if (regionId != null) {
            CourseQueryHelper.addRegionFilter(List.of(regionId), qCourse, filterBuilder);
        }

        if (themeId != null) {
            CourseQueryHelper.addThemeFilter(List.of(themeId), qCourse, filterBuilder);
        }

        if (styleId != null) {
            CourseQueryHelper.addStyleFilter(List.of(styleId), qCourse, filterBuilder);
        }

        if (togetherId != null) {
            CourseQueryHelper.addTogetherFilter(List.of(togetherId), qCourse, filterBuilder);
        }

        OrderSpecifier<?> orderSpecifier = CourseQueryHelper.getOrderSpecifier(orderBy, qCourse, member);

        long totalCount = queryFactory.selectFrom(qCourse)
                .where(filterBuilder)
                .fetchCount();

        List<CourseDetailDTO> courses = queryFactory.selectFrom(qCourse)
                .where(filterBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier)
                .fetch()
                .stream().map(course -> CourseDetailDTO.from(course, courseLikeService.isCourseLikedByMember(course)))
                .toList();

        return new PageImpl<>(courses, pageable, totalCount);
    }

    public List<CourseDTO> getCoursesByMemberTheme() {
        Member member = userDetailsService.getMemberByContextHolder();

        QCourse course = QCourse.course;
        List<Long> themeIds = member.getMemberThemes().stream().map(MemberTheme::getTheme).map(Theme::getId).toList();
        List<Long> styleIds = member.getMemberStyles().stream().map(MemberStyle::getStyle).map(Style::getId).toList();
        List<Long> togetherIds = member.getMemberTogethers().stream().map(MemberTogether::getTogether)
                .map(Together::getId).toList();

        BooleanBuilder filterBuilder = CourseQueryHelper.createQueryFilter(themeIds, styleIds, togetherIds);

        List<CourseDTO> courses = queryFactory.selectFrom(course)
                .where(filterBuilder)
                .fetch()
                .stream().map(CourseDTO::from)
                .toList();

        courses = new ArrayList<>(courses);
        Collections.shuffle(courses, new Random());

        List<CourseDTO> randomCourses = courses.stream().limit(6).toList();

        return randomCourses;
    }

    public List<CourseTagDTO> getCoursesByMemberRegion() {
        Member member = userDetailsService.getMemberByContextHolder();
        List<MemberRegion> memberRegions = member.getMemberRegions();

        List<CourseTagDTO> tagCourses = new ArrayList<>();
        memberRegions.forEach(memberRegion -> {
            Long id = memberRegion.getRegion().getId();
            List<Course> courses = courseRepository.findCoursesByRegionId(id);
            Collections.shuffle(courses, new Random());
            List<CourseDTO> courseDTOs = courses.stream().limit(6)
                    .map(course -> CourseDTO.from(course, courseLikeService.isCourseLikedByMember(course)))
                    .toList();
            tagCourses.add(CourseTagDTO.from(courseDTOs, memberRegion.getRegion()));
        });
        return tagCourses;
    }

}
