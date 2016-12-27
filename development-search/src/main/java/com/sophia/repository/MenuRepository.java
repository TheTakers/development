package com.sophia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sophia.domain.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String>{
	
	@Query("select m from Menu m where name like %?1%")
	public List<Menu> findByNameLike(String name);
}
