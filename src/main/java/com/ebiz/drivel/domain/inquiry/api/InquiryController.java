package com.ebiz.drivel.domain.inquiry.api;

import com.ebiz.drivel.domain.inquiry.dto.InquiryDTO;
import com.ebiz.drivel.domain.inquiry.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inquiries")
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;

    @PostMapping
    public ResponseEntity<InquiryDTO> createInquiry(@RequestBody InquiryDTO inquiryDTO) {
        InquiryDTO createdInquiry = inquiryService.createInquiry(inquiryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInquiry);
    }
}
