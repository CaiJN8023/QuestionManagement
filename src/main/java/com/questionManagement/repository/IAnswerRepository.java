package com.questionManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.questionManagement.entity.Answer;

public interface IAnswerRepository extends JpaRepository<Answer, Integer> {

	long countByAnswererId(Integer answererId);
	
	long countByPaperId(Integer paperId);
	
	@Query(value = "select * from tb_answer where idx_paper_id = :paperId group by idx_answerer_id;", 
			nativeQuery = true)
	List<Answer> findByPaperIdAndGroupByAnswererId(@Param(value = "paperId")Integer paperId);
	
	List<Answer> findByPaperId(Integer paperId);
	
	List<Answer> findByAnswererIdAndPaperId(Integer answererId, Integer paperId);
	
	@Query(value = "select * from tb_answer where idx_answerer_id = :answererId group by idx_paper_id;",
			nativeQuery = true)
	List<Answer> findByAnswererIdAndGroupByPaperId(@Param(value = "answererId") Integer answererId);
	
	@Query(value = "select SUM(score) from tb_answer where idx_answerer_id = :answererId and idx_paper_id = :paperId",
			nativeQuery = true)
	int calculateTotalScoreByAnswererIdAndPaperId(@Param(value = "answererId") Integer answererId, 
			@Param(value = "paperId") Integer paperId);
}
