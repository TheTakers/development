package com.sophia.repository;

import java.io.Serializable;

import org.springframework.data.repository.Repository;

/**
 * Created by Kim on 2015/9/21.
 */
public interface JpaRepository<I extends Repository<?, ?>> extends Serializable {

    I getRepository();

}