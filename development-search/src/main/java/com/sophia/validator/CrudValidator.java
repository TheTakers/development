package com.sophia.validator;

import java.util.Map;

import com.sophia.request.CrudRequest;

public interface CrudValidator {
	
	public void filter(Class<? extends CrudRequest> request,Map<String, Object> resultMap);
	
	public void callback(Object request,Object bean,Map<String, Object> resultMap);
}
