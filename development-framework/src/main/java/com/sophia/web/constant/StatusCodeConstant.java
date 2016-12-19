package com.sophia.web.constant;

/**
 * 状态码
 * @author zkning
 */
public enum StatusCodeConstant {
	
	SUCCESS(0,"处理成功"),
	
	SYSTEM_ERROR(1,"未知错误"),
	
	INVALID_REQUEST(2,"请求错误"),
	
	SERVICE_UNACCESSABLE(3,"服务器异常"),
	
	NEED_LOGIN(5,"未登录"),
	
	INSUFFICIENT_PRIVILEGES(6,"权限不足"),
	
	SESSION_EXPIRED(7,"session失效"),
	
	INVALID_ARGS(8,"参数异常"),
	
	USER_CALL_TIMEOUT(100,"用户请求超时"),
	
	USER_CALL_LIMITED(101,"请求错误"),
	
	SESSION_CALL_LIMITED(102,"请求错误"),
	
	IO_EXCEPTION(103,"请求错误"),
	
	SYSTEM_DATA_ERROR(104,"服务器数据异常");
	
	private Integer code;
	private String message;
	private StatusCodeConstant(Integer code ,String message){
		this.code = code;
		this.message = message;
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
