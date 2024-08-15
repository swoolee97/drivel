package com.ebiz.drivel.domain.inquiry.dto;

import com.ebiz.drivel.domain.inquiry.entity.Inquiry;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InquiryDTO {

    private Long id;
    private String title;
    private String content;

    public static InquiryDTO from(Inquiry inquiry) {
        return InquiryDTO.builder()
                .id(inquiry.getId())
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .build();
    }
}
