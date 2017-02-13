package com.sophia.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PrePersist;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.sophia.AdminApplication;
import com.sophia.domain.SQLDefine;
import com.sophia.service.CodeTemplateService;
import com.sophia.service.MenuService;
import com.sophia.service.SqlIdJdbcService;
import com.sophia.service.UserService;

/**
 * @author zkning
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@SpringApplicationConfiguration(classes = AdminApplication.class)
public class JUnitTest {  
	@Autowired UserService userService;
	@Autowired MenuService menuService;
	@Autowired CodeTemplateService codeTemplateService;
	@Autowired SqlIdJdbcService sqlIdJdbcService;
	@PrePersist 

	@Test
	public void queryForSqlId(){
		Map<String,Object> paramMap = new HashMap<>();
		sqlIdJdbcService.get("20161116041618").queryForList(paramMap);
	}

	public void queryForJpa(){
		Specification querySpecifi = new Specification<SQLDefine>() {

			@Override
			public Predicate toPredicate(Root<SQLDefine> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(cb.like(root.get("nickname").as(String.class), "%"+"111"+"%"));
				Predicate[] p = new Predicate[predicates.size()];  
				//				return cb.and(predicates.toArray(p));

				/*CriteriaQuery*/
				Predicate p1 = cb.like(root.get("name").as(String.class), "%11%");  
				Predicate p2 = cb.equal(root.get("uuid").as(Integer.class),null);  
				Predicate p3 = cb.gt(root.get("age").as(Integer.class), 1);  

				//把Predicate应用到CriteriaQuery中去,因为还可以给CriteriaQuery添加其他的功能，比如排序、分组啥的  
				query.where(cb.and(p3,cb.or(p1,p2)));  
				//添加排序的功能  
				query.orderBy(cb.desc(root.get("uuid").as(Integer.class)));  
				return query.getRestriction();  
			}
		};
	}

	@Test
	public void createCode(){
		JSONObject json = new JSONObject();

		//类名
		json.put("beanName", "Menu");

		//表
		json.put("tableName", "tb_basic_menu");

		//作者
		json.put("author", "zkning");

		//注释
		json.put("comment", "菜单管理");

		//模块名
		json.put("module", "basic");

		//文件路径
		json.put("filepath", "D:/createCode/src/main/java/com/sophia");
		codeTemplateService.createCodeTemplate(json);
	}
}
