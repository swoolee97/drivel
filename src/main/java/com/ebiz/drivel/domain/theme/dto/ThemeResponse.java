package com.ebiz.drivel.domain.theme.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ThemeResponse {
    private List<ThemeDTO> themes;
}
