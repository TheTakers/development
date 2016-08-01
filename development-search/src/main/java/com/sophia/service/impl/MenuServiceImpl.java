package com.sophia.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
		return getRepository().save(menu).getId();
	}
	
	public List<String> getMenuPath(String id){
		String paths = getRepository().getPath(id);
		if(StringUtils.isNoneBlank(paths)){
			return  Arrays.asList(paths.split(","));
		}
		return null;
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
	
	@Override
	public Menu findById(String id) {
		return getRepository().findOne(id);
	}
	
	@Override
	public void delete(String id) {
		getRepository().delete(id);
	}
}
