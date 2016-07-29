package com.sophia.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.sophia.domain.Menu;
import com.sophia.repository.MenuRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.service.MenuService;

@Service
public class MenuServiceImpl extends JpaRepositoryImpl<MenuRepository> implements MenuService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public String save(Menu menu){
		
		//生成GROUP PATH
		return getRepository().save(menu).getId();
	}


	@Override
	public List<Menu> getTreeData() {
		List<Menu> data = getRepository().findAll();
		List<Menu> menuData = new ArrayList<>();
		for(Menu menu : data){
			if(menu.getPid() .equals( "0" )){
				menuData.add(menu);
			}
		}
		formatTreeData(menuData, data);
		System.out.println(JSONObject.toJSONString(menuData));
		return menuData;
	}
	
	public void formatTreeData(List<Menu> tree,List<Menu> data){
		
		if(!CollectionUtils.isEmpty(tree)){
			 
			for(Menu item : tree){
			 
				for(Menu menu : data){
					if(item.getId().equals(menu.getPid())){
						item.getChild().add(menu);
					}
				}
				formatTreeData(item.getChild(), data);
			}
		}
	}
}
