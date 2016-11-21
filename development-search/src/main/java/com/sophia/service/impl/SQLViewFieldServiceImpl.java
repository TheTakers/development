package com.sophia.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sophia.repository.SQLViewFieldRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.service.SQLViewFieldService;


@Service
@Transactional
public class SQLViewFieldServiceImpl  extends JpaRepositoryImpl<SQLViewFieldRepository> implements SQLViewFieldService {

}
