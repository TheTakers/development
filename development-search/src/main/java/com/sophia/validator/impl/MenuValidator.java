package com.sophia.validator.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.sophia.request.CrudRequest;
import com.sophia.validator.CrudValidator;

@Component
public class MenuValidator implements CrudValidator {

	@Override
	public void filter(Class<? extends CrudRequest> request, Map<String, Object> resultMap) {
		// TODO Auto-generated method stub

	}

	@Override
	public void callback(Object request, Object bean, Map<String, Object> resultMap) {
		// TODO Auto-generated method stub

	}

}
