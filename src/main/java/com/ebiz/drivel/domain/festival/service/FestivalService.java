package com.ebiz.drivel.domain.festival.service;

import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.festival.dto.FestivalDetailResponse;
import com.ebiz.drivel.domain.festival.dto.FestivalInfoInterface;
import com.ebiz.drivel.domain.festival.entity.Festival;
import com.ebiz.drivel.domain.festival.exception.FestivalNotFoundException;
import com.ebiz.drivel.domain.festival.repository.FestivalRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FestivalService {
    private static final String FESTIVAL_NOT_FOUND_EXCEPTION_MESSAGE = "페스티벌 정보를 찾을 수 없습니다";
    private final FestivalRepository festivalRepository;

    public List<FestivalInfoInterface> getNearbyFestivalInfo(Course course) {
        return festivalRepository.findIdTitleFirstImagePathByDistance(
                course.getLatitude().doubleValue(), course.getLongitude().doubleValue());
    }

    public FestivalDetailResponse getFestivalInfo(String id) {
        Festival festival = festivalRepository.findById(id)
                .orElseThrow(() -> new FestivalNotFoundException(FESTIVAL_NOT_FOUND_EXCEPTION_MESSAGE));
        return FestivalDetailResponse.from(festival);
    }
}
