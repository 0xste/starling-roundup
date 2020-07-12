package com.stefanomantini.starlingroundup.api.advice;

import com.stefanomantini.starlingroundup.api.model.ApiError;
import com.stefanomantini.starlingroundup.service.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// TODO the whole exception setup needs signficant refactoring
@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  ResponseEntity<ApiError> handleBusinessException(final BusinessException e) {
    return new ResponseEntity<>(
        ApiError.builder().error(e.getLocalizedMessage()).build(), HttpStatus.BAD_REQUEST);
  }
}
