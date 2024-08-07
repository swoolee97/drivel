package com.ebiz.drivel.domain.meeting.api;

import com.ebiz.drivel.domain.meeting.exception.AlreadyRequestedJoinMeetingException;
import com.ebiz.drivel.domain.meeting.exception.MeetingJoinRequestNotFoundException;
import com.ebiz.drivel.domain.meeting.exception.MeetingNotFoundException;
import com.ebiz.drivel.global.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {MeetingController.class})
public class MeetingExceptionHandler {

    @ExceptionHandler(MeetingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMeetingNotFoundException(
            MeetingNotFoundException meetingNotFoundException) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .message(meetingNotFoundException.getMessage())
                .build());
    }

    @ExceptionHandler(MeetingJoinRequestNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMeetingJoinRequestNotFoundException(
            MeetingJoinRequestNotFoundException meetingJoinRequestNotFoundException) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .message(meetingJoinRequestNotFoundException.getMessage())
                .build());
    }

    @ExceptionHandler(AlreadyRequestedJoinMeetingException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyRequestedJoinMeetingException(
            AlreadyRequestedJoinMeetingException alreadyRequestedJoinMeetingException) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .message(alreadyRequestedJoinMeetingException.getMessage())
                .build());
    }
}
