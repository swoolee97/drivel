package com.ebiz.drivel.domain.place.service;

import com.ebiz.drivel.domain.place.dto.PlaceDTO;
import com.ebiz.drivel.domain.place.dto.PlaceInterface;
import com.ebiz.drivel.domain.place.entity.Place;
import com.ebiz.drivel.domain.place.repository.PlaceRepository;
import com.ebiz.drivel.domain.waypoint.dto.WaypointDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public List<PlaceInterface> getPlacesNearByCourse(WaypointDTO firstWaypoint, WaypointDTO lastWaypoint) {
        return placeRepository.findPlacesNearby(firstWaypoint.getLatitude().doubleValue(),
                firstWaypoint.getLongitude().doubleValue(),
                lastWaypoint.getLatitude().doubleValue(),
                lastWaypoint.getLongitude().doubleValue());
    }

    public PlaceDTO getPlaceDetail(Long id) {
        Place place = placeRepository.findById(id).orElseThrow();
        return PlaceDTO.from(place);
    }

}
