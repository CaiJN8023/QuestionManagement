package com.questionManagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.questionManagement.constant.MyPage;
import com.questionManagement.entity.QuestionType;
import com.questionManagement.entity.Subject;
import com.questionManagement.entity.User;
import com.questionManagement.entity.dto.SubjectDTO;
import com.questionManagement.repository.ISubjectRepository;
import com.questionManagement.repository.IUserRepository;
import com.questionManagement.service.IQuestionService;
import com.questionManagement.service.IQuestionTypeService;
import com.questionManagement.service.ISubjectService;

/**
 * 
 * @author CJN
 * @date 2019年3月7日
 * Title: SubjectServiceImpl 
 * Description: Subject 实体对应业务接口实现类
 */
@Service
public class SubjectServiceImpl implements ISubjectService {

	@Autowired
	private ISubjectRepository subjectRepository;
	
	@Autowired
	private IQuestionTypeService questionTypeService;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IQuestionService questionService; 
	
	@Override
	public SubjectDTO getOneById(Integer id) {
		Subject subject = subjectRepository.getOne(id);
		String[] typeIdStrs = subject.getTypeIds().split(",");
		User user = userRepository.getOne(subject.getCreatorId());
		String typeNames = "";
		for(int i = 0; i < typeIdStrs.length; i++) {
			int typeId = Integer.parseInt(typeIdStrs[i]);
			QuestionType type = questionTypeService.getById(typeId);
			if(i == 0) {
				typeNames += type.getName();
			}else {
				typeNames += ("，" + type.getName());
			}
		}
		return new SubjectDTO(subject, user.getName(), typeNames);
	}
	
	@Override
	public List<SubjectDTO> getDTOListByList(List<Subject> subjects){
		List<SubjectDTO> subjectDTOs = new ArrayList<SubjectDTO>();
		for(Subject subject : subjects) {
			SubjectDTO subjectDTO = this.getOneById(subject.getId());
			subjectDTOs.add(subjectDTO);
		}
		
		return subjectDTOs;
	}
	
	@Override
	public List<Subject> listAll(){
		return subjectRepository.findAll();
	}
	
	@Override
	public List<SubjectDTO> listAllSubjectDTO() {
		return this.getDTOListByList(this.listAll());
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Subject> getSubjectPage(Integer pageNum, Integer pageLimit) {
		Pageable pageable = PageRequest.of(pageNum - 1, pageLimit);
		return subjectRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Subject> getSubjectPageSort(Integer pageNum, Integer pageLimit) {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(pageNum, pageLimit, sort);
		return subjectRepository.findAll(pageable);
	}

	@Override
	public MyPage<SubjectDTO> getSubjectDTOPage(Page<Subject> subjectPage, Integer pageNum, Integer pageLimit) {
		// 从SubjectPage中获取需要数据
		List<Subject> subjects = subjectPage.getContent();
		int totalPages = subjectPage.getTotalPages();
		long totalElements = subjectPage.getTotalElements();
		
		// 处理数据存入 SubjectDTOs 中
		List<SubjectDTO> subjectDTOs = this.getDTOListByList(subjects);
		
		// 将数据全部封装到Map中
		MyPage<SubjectDTO> subjectDTOPage = new MyPage<SubjectDTO>(subjectDTOs, totalPages, totalElements);
		return subjectDTOPage;
	}

	@Override
	public MyPage<SubjectDTO> getSubjectDTOPageSort(Page<Subject> subjectPage, Integer pageNum, Integer pageLimit) {
		return null;
	}

	@Override
	public MyPage<SubjectDTO> getSubjectDTOPageByNameLike(String name, Integer pageNum, Integer pageLimit) {
		int start = (pageNum - 1) * pageLimit;
		int count = pageLimit;
		List<Subject> subjects = subjectRepository.findByNameLike(name, start, count);
		int totalElements = subjects.size();
		int totalPages = 0;
		if(totalElements % MyPage.PAGELIMIT == 0) {
			totalPages = totalElements / MyPage.PAGELIMIT;
		}else {
			totalPages = totalElements / MyPage.PAGELIMIT + 1;
		}
		return new MyPage<SubjectDTO>(this.getDTOListByList(subjects), totalElements, totalPages);
	}

	@Override
	public int countAll() {
		return (int) subjectRepository.count();
	}
	
	@Override
	public Subject save(Subject subject) {
		return subjectRepository.save(subject);
	}

	@Override
	public boolean delete(Integer id) {
		long count = questionService.countBySubjectId(id);
		if(count > 0) {
			return false;
		}else {
			subjectRepository.deleteById(id);
			return true;
		}
	}

	@Override
	public long countByCreatorId(Integer creatorId) {
		return subjectRepository.countByCreatorId(creatorId);
	}
	@Override
	public List<Subject> listByCreatorId(Integer creatorId){
		return subjectRepository.findAllByCreatorId(creatorId);
	}

	@Override
	public List<Integer> getTypeIdsById(Integer id) {
		Subject subject = subjectRepository.getOne(id);
		String[] typeIdsStr = subject.getTypeIds().split(",");
		List<Integer> typeIds = new ArrayList<Integer>();
		for(String typeIdStr : typeIdsStr) {
			int typeId = Integer.parseInt(typeIdStr);
			typeIds.add(typeId);
		}
		
		return typeIds;
	}

	@Override
	public List<QuestionType> getTypesById(Integer id) {
		List<QuestionType> types = new ArrayList<QuestionType>();
		List<Integer> typeIds = this.getTypeIdsById(id);
		for(Integer typeId : typeIds) {
			QuestionType type = questionTypeService.getById(typeId);
			types.add(type);
		}
		return types;
	}

}
