package com.sophia.service.impl;

import org.springframework.stereotype.Service;

import com.sophia.repository.SQLViewFieldRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.service.SQLViewFieldService;

@Service
public class SQLViewFieldServiceImpl  extends JpaRepositoryImpl<SQLViewFieldRepository> implements SQLViewFieldService {

}
