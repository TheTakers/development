//package com.sophia.db;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.sohpia.domain.User;
//import com.sohpia.service.UserService;
//import com.sophia.Application;
//
///**
// *
// * @author zkning
// * @email ningzuokun@ppmoney.com
// */
//@RunWith(SpringJUnit4ClassRunner.class)  
//@SpringApplicationConfiguration(classes = Application.class)
//public class OrmUnitTest {  
//	
//	@Autowired UserService userService;
//	
//	@Test
//	public void testCase(){
//		String sql="select * from TB_AT_USER where account = :account";
//		Map<String,Object> param = new HashMap<String,Object>();
//		param.put("account", "luojuhe");
//		List<User> data = userService.getRepository().queryList(sql, User.class, param);
//		for(User u : data){
//			System.out.println(u.getAccount());
//		}
//	}
//}
