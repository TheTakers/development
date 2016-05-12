package com.sophia.config;
//package com.mishop.common.config;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//
///**
// * Created by Kim on 2015/9/16.
// */
//@Configuration
//public class MyBatisConfiguration {
//
//    protected Logger logger = LoggerFactory.getLogger(getClass());
//
//    public static final String MYBATIS_PACKAGE = "com.mishop.domain";
//
//    @Autowired
//    private DataSource dataSource;
//    @Autowired
//    private SqlSessionFactory sqlSessionFactory;
//
//    @Bean
//    @Scope("prototype")
//    public SqlSessionTemplate sqlSessionTemplate() {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory() {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        sqlSessionFactoryBean.setTypeAliasesPackage(MYBATIS_PACKAGE);
//        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis/Configuration.xml"));
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource[] resources = new Resource[0];
//        try {
//            resources = resolver.getResources("mybatis/**/*Mapper.xml");
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        }
//        sqlSessionFactoryBean.setMapperLocations(resources);
//        SqlSessionFactory sqlSessionFactory = null;
//        try {
//            sqlSessionFactory = sqlSessionFactoryBean.getObject();
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            System.exit(0);
//        }
//        return sqlSessionFactory;
//    }
//}
