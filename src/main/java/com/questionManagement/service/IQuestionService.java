package com.questionManagement.service;

import java.util.List;

import com.questionManagement.entity.Question;
import com.questionManagement.entity.dto.QuestionDTO;

/**
 * 
 * @author CJN
 * @date 2019年3月7日
 * Title: IQuestionService 
 * Description: Question实体对应业务接口
 */
public interface IQuestionService {

	Question getById(Integer id);
	
	QuestionDTO getDTOById(Integer id);
	
	List<Question> listAll();
	
	List<QuestionDTO> getDTOListByList(List<Question> questions);
	
	List<QuestionDTO> getDTOsByCreatorId(Integer creatorId);
	
	List<QuestionDTO> getDTOsBySubjectId(Integer subjectId);
	
	Question save(Question question);
	
	boolean validateSimilarity(String content, Integer subjectId, Integer typeId, 
							Integer chapter, Integer difficult); 
	
	List<Question> getPaperQuestionList(int typeId, int typeScore, int minChapter, int maxChapter);
	
	List<Question> getByTypeIdAndScore(Integer typeId, Integer score);
	
	String getIdsByList(List<Question> questions);
	
	List<Question> getListByIds(String ids);
	
	String getTypeNameById(Integer id);
	
	long countBySubjectId(Integer subjectId);
	
	boolean deleteById(Integer id);
	
	List<String> getOptionContentsByOptions(Question question, List<String> options);
	
	void saveList(List<Question> questions);
}
