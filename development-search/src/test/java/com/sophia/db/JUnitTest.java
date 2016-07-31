package com.sophia.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sophia.Application;
import com.sophia.service.SQLIDService;
import com.sophia.service.UserService;

/**
 * @author zkning
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@SpringApplicationConfiguration(classes = Application.class)
public class JUnitTest {  
	
	@Autowired UserService userService;
	@Autowired SQLIDService sqlidService;
	
	NamedParameterJdbcTemplate npt;
	
	
	@Test
	public void testCase(){
//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("master");
//	    EntityManager em = factory.createEntityManager();
//	    Query query=em.createNativeQuery("select * from TB_AT_USER",User.class);
//	    List data = query.getResultList();
//	    for(Object object : data){
//	    	System.out.println(object);
//	    }
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("pid", 0);
		List<Map> data = sqlidService.queryForList("20160731040054", param, Map.class);
		for(Map map : data){
			System.out.println(map.get("pid"));
		}
	}
}
