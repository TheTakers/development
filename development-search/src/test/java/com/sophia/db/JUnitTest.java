package com.sophia.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sophia.Application;
import com.sophia.service.MenuService;
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
	@Autowired MenuService menuService;
	
	@Test
	public void testCase(){
//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("master");
//	    EntityManager em = factory.createEntityManager();
//	    Query query=em.createNativeQuery("select * from TB_AT_USER",User.class);
//	    List data = query.getResultList();
//	    for(Object object : data){
//	    	System.out.println(object);
//	    }
//		Map<String,Object> mp=new HashMap<String, Object>();
//		mp.put("pid", 0);
//		List<Map<String,Object>> data = sqlidService.queryForList("20160731040054",mp);
//		for(Map map : data){
//			System.out.println(map.get("pid"));
//		}
	}
}
