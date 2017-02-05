package com.sophia.advice.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sophia.response.Response;

/**
 * @author zkning
 */
@ControllerAdvice
public class ControllerAdviceExceptionHandler extends ResponseEntityExceptionHandler {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(value = Exception.class)
	public ModelAndView error(Exception ex) {
		logger.error(ex.getMessage());
		return new ModelAndView("error/error", "error", ex);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Response<Object>> handleRuntimeException(RuntimeException ex,HttpServletRequest request){
		logger.error("系统运行时异常:"+request.getRequestURL(),ex);
		return new ResponseEntity<Response<Object>>(Response.FAILURE(ex), HttpStatus.OK);
	}
}

