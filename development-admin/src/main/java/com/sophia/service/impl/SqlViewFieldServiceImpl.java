package com.sophia.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sophia.repository.SqlViewFieldRepository;
import com.sophia.repository.impl.JpaRepositoryImpl;
import com.sophia.service.SqlViewFieldService;


@Service
@Transactional
public class SqlViewFieldServiceImpl  extends JpaRepositoryImpl<SqlViewFieldRepository> implements SqlViewFieldService {

}
