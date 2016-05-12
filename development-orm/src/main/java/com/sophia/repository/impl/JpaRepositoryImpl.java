package com.sophia.repository.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.data.repository.Repository;

import com.sophia.repository.JpaRepository;

/**
 * Created by Kim on 2015/9/21.
 */
public class JpaRepositoryImpl<I extends Repository<?, ?>> extends ApplicationObjectSupport implements JpaRepository<I> {

    private static final long serialVersionUID = 1L;

    private Class<?> clazz;

    public JpaRepositoryImpl() {
        Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        if (types[0] instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) types[0];
            clazz = (Class<?>) type.getRawType();
        } else {
            clazz = (Class<?>) types[0];
        }
    }
    
    @SuppressWarnings("unchecked")
    public I getRepository() {
        return (I) this.getApplicationContext().getBean(clazz);
    }
}