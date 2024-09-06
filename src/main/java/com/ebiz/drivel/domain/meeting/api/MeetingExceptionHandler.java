package com.ebiz.drivel.domain.meeting.api;

import com.ebiz.drivel.domain.meeting.exception.AlreadyRequestedJoinMeetingException;
import com.ebiz.drivel.domain.meeting.exception.ForbiddenMeetingForbiddenException;
import com.ebiz.drivel.domain.meeting.exception.MeetingJoinRequestNotFoundException;
import com.ebiz.drivel.domain.meeting.exception.MeetingMemberNotFoundException;
import com.ebiz.drivel.domain.meeting.exception.MemberUnableToJoinMeetingException;
import com.ebiz.drivel.global.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {MeetingController.class, MeetingJoinController.class})
public class MeetingExceptionHandler {

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

    @ExceptionHandler(MeetingMemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMeetingMemberNotFoundException(
            MeetingMemberNotFoundException meetingMemberNotFoundException) {
        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .message(meetingMemberNotFoundException.getMessage())
                .build());
    }

    @ExceptionHandler(MemberUnableToJoinMeetingException.class)
    public ResponseEntity<ErrorResponse> handleMemberUnableToJoinMeetingException(
            MemberUnableToJoinMeetingException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(ForbiddenMeetingForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenMeetingHistoryException(ForbiddenMeetingForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.builder()
                        .message(e.getMessage())
                        .build());
    }

}
