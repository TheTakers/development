package com.sophia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sophia.domain.CodeTemplate;


@Repository
public interface CodeTemplateRepository extends JpaRepository<CodeTemplate, String>{
}
