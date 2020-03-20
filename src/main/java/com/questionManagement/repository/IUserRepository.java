package com.questionManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.questionManagement.entity.User;

/**
 * 
 * @author CJN
 * @date 2019年3月6日
 * Title: IUserRepository 
 * Description: User实体类对应Dao层，操作数据库，继承JpaRepository可以使用较多正常查询
 * 若需自定义查询，则以 findBy 作为前缀
 */
public interface IUserRepository extends JpaRepository<User, Integer> {

	User findByAccountAndPassword(String account, String password);
	
	@Query(value = "select * from tb_user where uk_name like %:name% limit :start, :count", nativeQuery = true)
	List<User> findByNameLike(@Param(value = "name") String name, @Param(value = "start") int start, 
			@Param(value = "count")int count);
	
	long countByAccount(String account);
}
