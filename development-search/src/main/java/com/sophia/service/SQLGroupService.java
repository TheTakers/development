package com.sophia.service;

import com.sophia.domain.SQLGroup;
import com.sophia.repository.JpaRepository;
import com.sophia.repository.SQLGroupRepository;

public interface SQLGroupService extends JpaRepository<SQLGroupRepository>{
	
	public String save(SQLGroup sqlGroup);
}
