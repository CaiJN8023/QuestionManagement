package com.questionManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.questionManagement.entity.RoleAuthority;

/**
 * 
 * @author CJN
 * @date 2019年3月7日
 * Title: IRoleAuthorityRepository 
 * Description: RoleAuthority实体类对应Dao层，操作数据库，继承JpaRepository可以使用较多正常查询
 * 若需自定义查询，则以 findBy 作为前缀
 */
public interface IRoleAuthorityRepository extends JpaRepository<RoleAuthority, Integer> {

	List<RoleAuthority> findByRoleId(Integer roleId);
	
	@Modifying
	@Query(value = "delete from tb_role_authority where role_id=:roleId", nativeQuery=true)
	void deleteByRoleId(@Param(value = "roleId")Integer roleId);
	
}
