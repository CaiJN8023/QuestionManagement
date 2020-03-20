package com.questionManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.questionManagement.entity.Subject;

/**
 * 
 * @author CJN
 * @date 2019年3月10日
 * Title: ISubjectRepository 
 * Description: Subject 实体对应DAO层
 */
public interface ISubjectRepository extends JpaRepository<Subject, Integer> {

	@Query(value = "select * from tb_subject where uk_name like %:name% limit :start, :count", nativeQuery = true)
	List<Subject> findByNameLike(@Param(value = "name") String name, @Param(value = "start") int start, 
								@Param(value = "count")int count);
	
	@Query(value = "select * from tb_subject where idx_creator_id = :creatorId", nativeQuery = true)
	List<Subject> findAllByCreatorId(@Param(value = "creatorId") Integer creatorId);
	
	@Query(value = "select count(*) from tb_subject where idx_creator_id = :userId", nativeQuery = true)
	long countByCreatorId(@Param(value = "userId") Integer userId);
}
