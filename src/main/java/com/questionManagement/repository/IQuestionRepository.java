package com.questionManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.questionManagement.entity.Question;

public interface IQuestionRepository extends JpaRepository<Question, Integer> {

	List<Question> findAllByCreatorId(Integer creatorId);
	
	List<Question> findAllBySubjectId(Integer subjectId);
	
	List<Question> findAllBySubjectIdAndTypeIdAndChapterAndDifficulty(Integer subjectId, Integer typeId, 
											Integer chapter, Integer difficulty);
	
	@Query(value = "select * from tb_question where idx_type_id = :typeId and score = :typeScore "
					+ "and chapter between :minChapter and :maxChapter", nativeQuery = true)
	List<Question> findByParams(@Param(value = "typeId")int typeId, @Param(value = "typeScore")int typeScore, 
							@Param(value = "minChapter")int minChapter, @Param(value = "maxChapter")int maxChapter);
	
	List<Question> findAllByTypeIdAndScore(Integer typeId, Integer score);
	
	long countBySubjectId(Integer subjectId);
	
	@Query(value = "select * from tb_question where find_in_set(pk_id, :ids)>0 order by difficulty;", nativeQuery = true)
	List<Question> findAllByIdOrderByDifficulty(@Param(value = "ids")String ids);
}
