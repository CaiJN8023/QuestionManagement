package com.questionManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.questionManagement.entity.Role;

public interface IRoleRepository extends JpaRepository<Role, Integer> {

	Role findByNameLike(String name);
	
}
