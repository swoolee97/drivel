package com.ebiz.drivel.domain.profile.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateGenderDTO {
    private Integer gender;
    private Date birth;
}
