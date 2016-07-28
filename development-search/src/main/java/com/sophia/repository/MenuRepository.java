package com.sophia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sophia.domain.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String>{

}
