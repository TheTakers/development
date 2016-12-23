package com.sophia.response;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.sophia.web.constant.StatusCodeConstant;

/**
 * 响应
 * @author zkning
 */
public class Response<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer code;
	private String message;
	private T result;
	
	public Response(Integer code,String message){
		this.code = code;
		this.message = message;
	}
	
	public Response(Integer code,String message,T result){
		this.code = code;
		this.message = message;
		this.result = result;
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
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	
	/**
	 * 检查是否成功
	 * @return
	 */
	public Boolean checkSuccess(){
		return StatusCodeConstant.SUCCESS.equals(this.code);
	}
	/**
	 * toJsonString
	 * @return
	 */
	public String toJsonString(){
		return JSONObject.toJSONString(this);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Response getInstance(Integer code,String message,Object result){
		return new Response(code, message, result);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Response getInstance(Integer code,String message){
		return new Response(code, message, null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Response SUCCESS(){
		return new Response(StatusCodeConstant.SUCCESS.code, StatusCodeConstant.SUCCESS.message, null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Response SUCCESS(Object result){
		return new Response(StatusCodeConstant.SUCCESS.code, StatusCodeConstant.SUCCESS.message, result);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Response FAILURE(){
		return new Response(StatusCodeConstant.SYSTEM_ERROR.code, StatusCodeConstant.SYSTEM_ERROR.message, null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Response FAILURE(Object result){
		return new Response(StatusCodeConstant.SYSTEM_ERROR.code, StatusCodeConstant.SYSTEM_ERROR.message, result);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Response FAILURE(Integer code ,String message){
		return new Response(code, message, null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Response SYSTEMEXCEPTION(Exception ex){
		return new Response(StatusCodeConstant.SYSTEM_ERROR.code, StatusCodeConstant.SYSTEM_ERROR.message, ex);
	}
}
