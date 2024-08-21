package com.ebiz.drivel.domain.meeting.api;

import com.ebiz.drivel.domain.meeting.application.MeetingNoticeService;
import com.ebiz.drivel.domain.meeting.dto.MeetingNoticeDTO;
import com.ebiz.drivel.domain.meeting.dto.MeetingNoticeResponse;
import com.ebiz.drivel.global.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeting")
public class MeetingNoticeController {

    private final MeetingNoticeService meetingNoticeService;

    @PostMapping("/notice")
    public ResponseEntity<BaseResponse> addMeetingNotice(@RequestBody @Valid MeetingNoticeDTO meetingNoticeDTO) {
        meetingNoticeService.addMeetingNotice(meetingNoticeDTO);
        return ResponseEntity.ok(BaseResponse.builder()
                .message("공지가 등록되었습니다")
                .build());
    }

    @DeleteMapping("/notice/{id}")
    public ResponseEntity<BaseResponse> deleteMeetingNotice(@PathVariable Long id) {
        meetingNoticeService.deleteMeetingNotice(id);
        return ResponseEntity.ok(BaseResponse.builder()
                .message("공지가 삭제되었습니다")
                .build());
    }

    @GetMapping("/{id}/notice")
    public ResponseEntity<MeetingNoticeResponse> getMeetingNotices(@PathVariable Long id) {
        MeetingNoticeResponse noticeResponse = meetingNoticeService.getLatestMeetingNotice(id);
        return ResponseEntity.ok(noticeResponse);
    }
}
