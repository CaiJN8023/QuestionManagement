package com.questionManagement.service;

import java.util.List;

import com.questionManagement.entity.QuestionType;
import com.questionManagement.entity.dto.QuestionTypeDTO;

/**
 * 
 * @author CJN
 * @date 2019年3月7日
 * Title: IQuestionTypeService 
 * Description: QuestionType实体对应业务接口
 */
public interface IQuestionTypeService {
	
	QuestionType getById(Integer id);
	
	QuestionTypeDTO getDTOById(Integer id);
	
	List<QuestionType> listAll();
	
	List<QuestionType> listByCreatorId(Integer id);
	
	List<QuestionTypeDTO> getDTOListByList(List<QuestionType> questionTypes);
	
	QuestionType save(QuestionType questionType);
	
	boolean deleteById(Integer id);

	List<QuestionType> listBySubjectId(Integer subjectId);
	
	int countBySubjectId(Integer subjectId);
	
	Integer getIdByName(String name);
}
