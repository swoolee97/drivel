package com.ebiz.drivel.domain.spot.application;

import com.ebiz.drivel.domain.spot.dto.SpotInterface;
import com.ebiz.drivel.domain.spot.repository.SpotRepository;
import com.ebiz.drivel.domain.waypoint.dto.WaypointDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpotService {

    private final SpotRepository spotRepository;

    public List<SpotInterface> getSpotsByCourse(WaypointDTO firstWaypoint, WaypointDTO lastWaypoint) {
        return spotRepository.findSpotsNearby(firstWaypoint.getLatitude().doubleValue(),
                firstWaypoint.getLongitude().doubleValue(),
                lastWaypoint.getLatitude().doubleValue(),
                lastWaypoint.getLongitude().doubleValue());
    }
}
