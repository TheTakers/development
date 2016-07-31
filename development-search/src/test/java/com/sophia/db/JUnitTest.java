package com.sophia.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sophia.Application;
import com.sophia.domain.User;
import com.sophia.service.UserService;

/**
 * @author zkning
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@SpringApplicationConfiguration(classes = Application.class)
public class JUnitTest {  
	
	@Autowired UserService userService;
	
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
		npt.q
	}
}
