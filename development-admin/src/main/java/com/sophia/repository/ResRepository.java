package com.sophia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sophia.domain.Res;

@Repository
public interface ResRepository extends JpaRepository<Res, String>{
	
	@Query("select r from Res r where name like %?1%")
	public List<Res> findByNameLike(String name);
}
