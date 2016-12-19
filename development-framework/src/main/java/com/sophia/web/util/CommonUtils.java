package com.sophia.web.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.util.CollectionUtils;

import com.sophia.response.Response;
import com.sophia.web.constant.StatusCodeConstant;

/**
 * 工具
 * @author zkning
 */
public class CommonUtils {
	
	/**
	 * jsr303 校验
	 * @param bean
	 * @return
	 */
	public static <T> Response<?> verify(T bean) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		javax.validation.Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(bean);
		StringBuilder sb = new StringBuilder();
		if (!CollectionUtils.isEmpty(violations)) {
			for (ConstraintViolation<T> violation : violations) {
				String message = violation.getMessage();
				sb.append(violation.getPropertyPath().toString() + ":" + message + "; ");
				break;
			}
			return Response.FAILURE(StatusCodeConstant.INVALID_ARGS.getCode(),sb.toString());
		} else {
			return Response.SUCCESS();
		}
	}
}
