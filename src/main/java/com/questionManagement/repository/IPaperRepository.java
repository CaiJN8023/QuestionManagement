package com.questionManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.questionManagement.entity.Paper;

public interface IPaperRepository extends JpaRepository<Paper, Integer> {

	List<Paper> findAllByCreatorId(Integer creatorId);
	
	/**
	 * 
	 * Title: countByAnswererIdAndPaperIdAndStages
	 * Description: 查询该学生是否测评过该试卷
	 * @param answererId
	 * @param paperId
	 * @param unMark
	 * @param marked
	 * @return
	 * long
	 */
	@Query(value = "select count(pk_id) from tb_answer where idx_answerer_id = :answererId"
					+ " and idx_paper_id = :paperId and (stage = :unMark or stage = :marked)", nativeQuery=true)
	long countByAnswererIdAndPaperIdAndStages(@Param(value = "answererId") Integer answererId, 
			@Param(value = "paperId")Integer paperId, @Param(value = "unMark")String unMark, 
			@Param(value = "marked")String marked);
	
	List<Paper> findAllByInviteCode(String inviteCode);
}
