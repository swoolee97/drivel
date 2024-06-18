package com.ebiz.drivel.global.api;

import com.ebiz.drivel.global.dto.ErrorResponse;
import com.ebiz.drivel.global.exception.CourseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String INCLUDED_NULL_DATA_EXCEPTION_MESSAGE = "빈 값이 있습니다";
    private static final String WRONG_USER_EXCEPTION_MESSAGE = "이메일 혹은 비밀번호가 일치하지 않습니다";
    private static final String WRONG_REQUEST_EXCEPTION_MESSAGE = "잘못된 요청입니다";
    private static final String COURSE_NOT_FOUND_EXCEPTION_MESSAGE = "드라이브 코스를 찾을 수 없습니다";
    private static final String MAX_UPLOAD_SIZE_EXCEEDED_MESSAGE = "사진은 최대 10MB까지 업로드 가능합니다";

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity handleBadCredentialsException() {
        return new ResponseEntity(ErrorResponse.builder()
                .message(WRONG_USER_EXCEPTION_MESSAGE)
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleNullRequestException() {
        return new ResponseEntity(ErrorResponse.builder()
                .message(INCLUDED_NULL_DATA_EXCEPTION_MESSAGE)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity handleNotFoundException() {
        return new ResponseEntity(ErrorResponse.builder()
                .message(WRONG_REQUEST_EXCEPTION_MESSAGE)
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity handleCourseNotFoundException() {
        return new ResponseEntity(ErrorResponse.builder()
                .message(COURSE_NOT_FOUND_EXCEPTION_MESSAGE)
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity handleMaxUploadSizeExceededException() {
        return new ResponseEntity(ErrorResponse.builder()
                .message(MAX_UPLOAD_SIZE_EXCEEDED_MESSAGE)
                .build(), HttpStatus.BAD_REQUEST);
    }

}
