package com.questionManagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.questionManagement.entity.QuestionType;
import com.questionManagement.entity.Subject;
import com.questionManagement.entity.User;
import com.questionManagement.entity.dto.QuestionTypeDTO;
import com.questionManagement.repository.IQuestionTypeRepository;
import com.questionManagement.repository.IUserRepository;
import com.questionManagement.service.IQuestionTypeService;
import com.questionManagement.service.ISubjectService;

/**
 * 
 * @author CJN
 * @date 2019年3月7日
 * Title: QuestionTypeServiceImpl 
 * Description: QuestionType 实体业务接口实现类
 */
@Service
public class QuestionTypeServiceImpl implements IQuestionTypeService {

	@Autowired
	private IQuestionTypeRepository questionTypeRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private ISubjectService subjectService;
	
	
	@Override
	public List<QuestionType> listBySubjectId(Integer subjectId){
		return questionTypeRepository.findBySubjectId(subjectId);
	}

	@Override
	public List<QuestionTypeDTO> getDTOListByList(List<QuestionType> questionTypes){
		List<QuestionTypeDTO> typeDTOs = new ArrayList<QuestionTypeDTO>();
		for(QuestionType type : questionTypes) {
			User user = userRepository.getOne(type.getCreatorId());
			QuestionTypeDTO typeDTO = new QuestionTypeDTO(type, user.getName());
			typeDTOs.add(typeDTO);
		}
		
		return typeDTOs;
	}
	
	@Override
	public int countBySubjectId(Integer subjectId) {
		return questionTypeRepository.countBySubejctId(subjectId);
	}

	@Override
	public QuestionType getById(Integer id) {
		return questionTypeRepository.getOne(id);
	}
	
	@Override
	public QuestionTypeDTO getDTOById(Integer id) {
		QuestionType type = questionTypeRepository.getOne(id);
		String creatorName = userRepository.getOne(type.getCreatorId()).getName();
		return new QuestionTypeDTO(type, creatorName);
	}

	@Override
	public List<QuestionType> listAll() {
		return questionTypeRepository.findAll();
	}
	
	@Override
	public List<QuestionType> listByCreatorId(Integer id){
		return questionTypeRepository.findByCreatorId(id);
	}

	@Override
	public QuestionType save(QuestionType questionType) {
		return questionTypeRepository.save(questionType);
	}

	@Override
	public boolean deleteById(Integer id) {
		List<Subject> subjects = subjectService.listAll();
		List<String> idsList = new ArrayList<>();
		for(Subject sub : subjects) {
			idsList.add(sub.getTypeIds());
		}
		for(String ids : idsList) {
			if(ids.contains(id + "")) {
				return false;
			}
		}
		questionTypeRepository.deleteById(id);
		return true;
	}

	@Override
	public Integer getIdByName(String name) {
		return questionTypeRepository.findByName(name).getId();
	}

	
}
