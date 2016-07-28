package com.sophia.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
}
