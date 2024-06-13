package com.ebiz.drivel.domain.festival.service;

import com.ebiz.drivel.domain.course.entity.Course;
import com.ebiz.drivel.domain.festival.dto.FestivalInfoInterface;
import com.ebiz.drivel.domain.festival.repository.FestivalRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FestivalService {

    private final FestivalRepository festivalRepository;

    public List<FestivalInfoInterface> getNearbyFestivals(Course course) {
        return festivalRepository.findIdTitleFirstImagePathByDistance(
                course.getLatitude().doubleValue(), course.getLongitude().doubleValue());
    }
}
