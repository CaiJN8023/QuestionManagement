package com.questionManagement.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.questionManagement.constant.MyPage;
import com.questionManagement.entity.QuestionType;
import com.questionManagement.entity.Subject;
import com.questionManagement.entity.dto.SubjectDTO;

/**
 * 
 * @author CJN
 * @date 2019年3月7日
 * Title: ISubjectService 
 * Description: Subject实体对应业务接口
 */
public interface ISubjectService {
	
	SubjectDTO getOneById(Integer id);

	List<SubjectDTO> getDTOListByList(List<Subject> subjects);
	
	List<Subject> listAll();
	
	List<SubjectDTO> listAllSubjectDTO();
	
	MyPage<SubjectDTO> getSubjectDTOPage(Page<Subject> subjectPage, Integer pageNum, Integer pageLimit);
	
	MyPage<SubjectDTO> getSubjectDTOPageSort(Page<Subject> subejct, Integer pageNum, Integer pageLimit);
	
	Page<Subject> getSubjectPage(Integer pageNum, Integer pageLimit);
	
	Page<Subject> getSubjectPageSort(Integer pageNum, Integer pageLimit);
	
	MyPage<SubjectDTO> getSubjectDTOPageByNameLike(String name, Integer pageNum, Integer pageLimit);
	
	int countAll();
	
	Subject save(Subject subject);
	
	boolean delete(Integer id);
	
	long countByCreatorId(Integer creatorId);
	
	List<Subject> listByCreatorId(Integer creatorId);
	
	List<Integer> getTypeIdsById(Integer id);
	
	List<QuestionType> getTypesById(Integer id);
	
}
