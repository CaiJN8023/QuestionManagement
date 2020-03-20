package com.questionManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.questionManagement.entity.QuestionType;

public interface IQuestionTypeRepository extends JpaRepository<QuestionType, Integer> {

	@Query(value = "select * from tb_question_type where idx_subject_id = :subjectId", nativeQuery = true)
	List<QuestionType> findBySubjectId(@Param(value = "subjectId") Integer subjectId);
	
	@Query(value = "select count(*) from tb_question_type where idx_subject_id = :subjectId", nativeQuery = true)
	int countBySubejctId(@Param(value = "subjectId") Integer subjectId);
	
	List<QuestionType> findByCreatorId(Integer creatorId);
	
	QuestionType findByName(String name);
	
}
