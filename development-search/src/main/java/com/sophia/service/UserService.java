package com.sophia.service;

import com.sophia.repository.JpaRepository;
import com.sophia.repository.UserRepository;

public interface UserService extends JpaRepository<UserRepository>{
	
	/**
	 * 本地查询
	 */
	public void nativeQuery();
}
