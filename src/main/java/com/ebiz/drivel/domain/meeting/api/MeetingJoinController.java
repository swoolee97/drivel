package com.ebiz.drivel.domain.meeting.api;

import com.ebiz.drivel.domain.meeting.application.MeetingJoinService;
import com.ebiz.drivel.domain.meeting.dto.JoinRequestDTO;
import com.ebiz.drivel.domain.meeting.dto.JoinRequestDecisionDTO;
import com.ebiz.drivel.domain.meeting.dto.MeetingJoinRequestDTO;
import com.ebiz.drivel.global.dto.BaseResponse;
import java.util.List;
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
@RequestMapping("/meeting/join")
public class MeetingJoinController {

    private final MeetingJoinService meetingJoinService;

    @PostMapping
    public ResponseEntity<BaseResponse> joinMeeting(@RequestBody JoinRequestDTO joinRequestDTO) {
        meetingJoinService.requestJoinMeeting(joinRequestDTO.getId());
        return ResponseEntity.ok(BaseResponse.builder()
                .message("모임 가입 신청이 완료되었습니다")
                .build());
    }

    @DeleteMapping("/{meetingId}")
    public ResponseEntity<BaseResponse> cancelJoinMeeting(@PathVariable Long meetingId) {
        meetingJoinService.cancelJoinMeeting(meetingId);
        return ResponseEntity.ok(BaseResponse.builder()
                .message("모임 가입 신청이 취소되었습니다")
                .build());
    }

    @PostMapping("/accept")
    public void decideJoinMeeting(@RequestBody JoinRequestDecisionDTO joinRequestDecisionDTO) {
        meetingJoinService.acceptJoinMeeting(joinRequestDecisionDTO);
    }

    @GetMapping
    public ResponseEntity<List<MeetingJoinRequestDTO>> getJoinRequests() {
        List<MeetingJoinRequestDTO> joinRequests = meetingJoinService.getJoinRequests();
        return ResponseEntity.ok(joinRequests);
    }
}
