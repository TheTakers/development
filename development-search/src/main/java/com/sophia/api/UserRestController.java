package com.sophia.api;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.sophia.request.UserRequest;
import com.sophia.web.constant.Constant;

/**
 * Created by Kim on 2015/9/11.
 */
@RestController
@RequestMapping(value = "/api")
public class UserRestController extends BaseController {
  
    /**
     * @param userParam
     * @return
     */
    @RequestMapping(value = "/permit/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> permituser(@RequestBody @Valid UserRequest userParam) {
  
        try {
            
            return responseOk("");
        } catch (Exception e) {
            return responseError(Constant.FAILURE_MESSAGE, e);
        }
    }

    /**
     *
     * @param userLuckParam
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> user(@RequestBody @Valid UserRequest userLuckParam) {
     
        try {
            return responseOk(Constant.SUCCESS_MESSAGE);
        } catch (Exception e) {
            return responseError(Constant.FAILURE_MESSAGE, e);
        }
    }
    
    /**
     * 通过JSON直接传值
     * @param order
     * @return
     */
    @RequestMapping(value = "/permit/verify", method = RequestMethod.POST ,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object>verify(@RequestBody @Valid String order) {
	 
		try{
			logger.info("请求参数:{}",order);
			return responseOk(Constant.SUCCESS_MESSAGE,JSON.parseObject(order));
		}catch(Exception e){
			 return  responseError(Constant.FAILURE_MESSAGE, e);
		}
	}
}
