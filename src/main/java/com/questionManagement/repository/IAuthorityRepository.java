package com.questionManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.questionManagement.entity.Authority;

public interface IAuthorityRepository extends JpaRepository<Authority, Integer> {

	List<Authority> findByParentId(Integer parentId);
	
}
