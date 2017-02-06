package com.sophia.service;

import org.springframework.data.domain.Page;

import com.sophia.domain.User;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.UserRepository;
import com.sophia.request.UserRequest;

public interface UserService extends JpaRepository<UserRepository>{
	
	/**
	 * 本地查询
	 */
	void nativeQuery();
	
 	Page<User> grid(UserRequest userParam);
}
