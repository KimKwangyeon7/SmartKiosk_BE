package com.iMbank.iMbank.global.exception;

import com.iMbank.iMbank.global.common.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<Message<Void>> globalException(GlobalException e) {
        log.error("글로벌 관련 오류: {}", e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(Message.fail(null, e.getErrorCode().getErrorMessage()));
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Message<String>> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Message.fail("403", "권한이 없습니다."));
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<Message<String>> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Message.fail("403", "권한이 없습니다."));
    }
}
