package com.sophia.api;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.sophia.response.Response;
import com.sophia.vo.UserParam;

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
    public Object permituser(@RequestBody @Valid UserParam userParam) {
        try {
        	return Response.SUCCESS();
        } catch (Exception e) {
        	return Response.FAILURE(e);
        }
    }

    /**
     *
     * @param userLuckParam
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object user(@RequestBody @Valid UserParam userLuckParam) {
    	 try {
         	return Response.SUCCESS();
         } catch (Exception e) {
         	return Response.FAILURE(e);
         }
    }
    
    /**
     * 通过JSON直接传值
     * @param order
     * @return
     */
    @RequestMapping(value = "/permit/verify", method = RequestMethod.POST ,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object verify(@RequestBody @Valid String order) {
		try{
			logger.info("请求参数:{}",order);
			return Response.SUCCESS(JSON.parseObject(order));
		}catch(Exception e){
			return Response.FAILURE(e);
		}
	}
}
