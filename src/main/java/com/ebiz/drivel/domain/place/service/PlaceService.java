package com.ebiz.drivel.domain.place.service;

import com.ebiz.drivel.domain.place.dto.PlaceDTO;
import com.ebiz.drivel.domain.place.dto.PlaceInterface;
import com.ebiz.drivel.domain.place.entity.Place;
import com.ebiz.drivel.domain.place.repository.PlaceRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public List<PlaceInterface> getPlacesNearByCourse(BigDecimal latitude, BigDecimal longitude) {
        return placeRepository.findPlacesNearby(latitude.doubleValue(), longitude.doubleValue());
    }

    public PlaceDTO getPlaceDetail(Long id) {
        Place place = placeRepository.findById(id).orElseThrow();
        return PlaceDTO.from(place);
    }

}
