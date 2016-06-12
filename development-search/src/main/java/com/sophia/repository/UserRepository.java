package com.sophia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sophia.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query(value="from User where ((username is not null) AND (username = ?1)) or  (account is not null AND account = ?2)",countQuery="select count(1) from User where (username is not null AND username = ?1) or  (account is not null AND account = ?2)")
	public Page<User> getByUsernameOrAccount(String username,String account,Pageable pageable);
}
