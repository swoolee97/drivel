package com.ebiz.drivel.domain.meeting.dto;

import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantSummaryDTO {

    private HashMap<String, Integer> carCareer;
    private HashMap<String, Integer> carModel;
    private HashMap<String, Integer> ageGroup;
    private HashMap<String, Integer> gender;


}
