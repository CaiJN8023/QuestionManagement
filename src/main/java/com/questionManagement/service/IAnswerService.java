package com.questionManagement.service;

import java.util.List;
import java.util.Map;

import com.questionManagement.entity.Answer;
import com.questionManagement.entity.dto.AnswerDTO;
import com.questionManagement.entity.dto.AnswerQuestionDTO;

/**
 * 
 * @author CJN
 * @date 2019年3月7日
 * Title: IAnswerService 
 * Description: Answer实体对应业务接口
 * 
 * 属于业务逻辑层，包含对应实体类所有业务操作方法
 * 若自定义查询，
 * 		请遵循以下规约
 * 
 * 1 ） 获取单个对象的方法用 get 做前缀。
 * 2 ） 获取多个对象的方法用 list 做前缀。
 * 3 ） 获取统计值的方法用 count 做前缀。
 * 4 ） 插入的方法用 save 做前缀。
 * 5 ） 删除的方法用 delete 做前缀。
 * 6 ） 修改的方法用 update 做前缀。
 */
public interface IAnswerService {

	void saveAll(List<Answer> answers);
	
	long countByAnswererId(Integer answererId);
	
	long countByPaperId(Integer paperId);
	
	List<Answer> listByPaperIdAndGroupByAnswerId(Integer paperId);
	
	List<AnswerDTO> getDTOListByList(List<Answer> answers);
	
	List<AnswerQuestionDTO> listAnswerQuestionDTOByAnswererIdAndPaperId(Integer answererId, Integer paperId);
	
	List<Answer> listByAnswererIdAndPaperId(Integer answererId, Integer paperId);
	
	void listKindsAnswerQuestionByAnswererIdAndPaperId(Integer answererId, Integer paperId, Map<Object, Object> map);
	
	List<Answer> findByAnswererIdAndGroupByPaperId(Integer answererId);
	
	int calculateTotalScoreByAnswererIdAndPaperId(Integer answererId, Integer paperId);
	
}
