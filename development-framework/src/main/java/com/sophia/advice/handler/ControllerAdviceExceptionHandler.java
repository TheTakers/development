package com.sophia.advice.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sophia.exception.RestException;

/**
 * Created by Kim on 2015/9/15.
 */
@ControllerAdvice
public class ControllerAdviceExceptionHandler extends ResponseEntityExceptionHandler {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = Exception.class)
    public ModelAndView error(Exception ex) {
        logger.error(ex.getMessage());
        return new ModelAndView("error/error", "error", ex);
    }

    @ExceptionHandler(value = {RestException.class})
    public final ResponseEntity<String> handleException(RestException ex, WebRequest request) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

