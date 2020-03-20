package com.questionManagement.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.questionManagement.constant.Similarity;
import com.questionManagement.constant.fieldEnum.AnswerEnum;
import com.questionManagement.entity.Paper;
import com.questionManagement.entity.Question;
import com.questionManagement.entity.QuestionType;
import com.questionManagement.entity.Subject;
import com.questionManagement.entity.User;
import com.questionManagement.entity.dto.QuestionDTO;
import com.questionManagement.repository.IQuestionRepository;
import com.questionManagement.repository.IQuestionTypeRepository;
import com.questionManagement.repository.ISubjectRepository;
import com.questionManagement.repository.IUserRepository;
import com.questionManagement.service.IPaperService;
import com.questionManagement.service.IQuestionService;
import com.questionManagement.util.StringUtils;

/**
 * 
 * @author CJN
 * @date 2019年3月7日
 * Title: QuestionServiceImpl 
 * Description: Question实体业务接口实现类
 */
@Service
public class QuestionServiceImpl implements IQuestionService {

	@Autowired
	private IQuestionRepository questionRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private ISubjectRepository subjectRepository;
	
	@Autowired
	private IQuestionTypeRepository questionTypeRepository;
	
	@Autowired
	private IPaperService paperService;
	
	@Override
	public Question getById(Integer id) {
		return questionRepository.getOne(id);
	}

	@Override
	public QuestionDTO getDTOById(Integer id) {
		Question question = this.getById(id);
		User user = userRepository.getOne(question.getCreatorId());
		Subject subject = subjectRepository.getOne(question.getSubjectId());
		QuestionType type = questionTypeRepository.getOne(question.getTypeId());
		return new QuestionDTO(question, user.getName(), subject.getName(), type.getName());
	}

	@Override
	public List<Question> listAll() {
		return questionRepository.findAll();
	}

	@Override
	public List<QuestionDTO> getDTOListByList(List<Question> questions) {
		List<QuestionDTO> questionDTOs = new ArrayList<QuestionDTO>();
		for(Question question : questions) {
			QuestionDTO questionDTO = this.getDTOById(question.getId());
			questionDTOs.add(questionDTO);
		}
		return questionDTOs;
	}

	@Override
	public List<QuestionDTO> getDTOsByCreatorId(Integer creatorId) {
		List<Question> questions = questionRepository.findAllByCreatorId(creatorId);
		
		return this.getDTOListByList(questions);
	}

	@Override
	public List<QuestionDTO> getDTOsBySubjectId(Integer subjectId) {
		List<Question> questions = questionRepository.findAllBySubjectId(subjectId);
		
		return this.getDTOListByList(questions);
	}

	@Override
	public Question save(Question question) {
//		Integer typeId = question.getTypeId();
//		QuestionType type = questionTypeRepository.getOne(typeId);
//		if(type.getName().contains("选择题")) {
//			question.setOptionA("A. " + question.getOptionA());
//			question.setOptionB("B. " + question.getOptionB());
//			question.setOptionC("C. " + question.getOptionC());
//			question.setOptionD("D. " + question.getOptionD());
//		}
		if(question.getCreateTime() == null) {
			question.setCreateTime(new Date());
		}
		
		if(question.getUpdateTime() == null) {
			question.setUpdateTime(new Date ());
		}
		return questionRepository.save(question);
	}

	/**
	 * 验证新添加的试题是否和试题库中的试题具有一定相似度
	 */
	@Override
	public boolean validateSimilarity(String content, Integer subjectId, 
											Integer typeId, Integer chapter, Integer difficulty) {
		List<Question> questions = questionRepository.findAllBySubjectIdAndTypeIdAndChapterAndDifficulty(subjectId, typeId, chapter, difficulty);
		boolean flag = false;
		if(questions.size() <= 0) {
			return false;
		}
		for(Question question : questions ) {
			float similarity = StringUtils.similarity(content, question.getContent());
			if(similarity >= Similarity.similarity) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	@Override
	public List<Question> getPaperQuestionList(int typeId, int typeScore, int minChapter, int maxChapter) {
		
		return questionRepository.findByParams(typeId, typeScore, minChapter, maxChapter);
	}

	@Override
	public List<Question> getByTypeIdAndScore(Integer typeId, Integer score) {
		return questionRepository.findAllByTypeIdAndScore(typeId, score);
	}

	@Override
	public String getIdsByList(List<Question> questions) {
		int size = questions.size();
		String[] ids = new String[size];
		for(int i = 0; i < size; i++) {
			ids[i] = questions.get(i).getId() + "";
		}
		return String.join(",", ids);
	}

	@Override
	public List<Question> getListByIds(String ids) {
//		String[] idsArr = ids.split(",");
//		List<Integer> idsList = new ArrayList<>();
//		for(int i = 0; i < idsArr.length; i++) {
//			idsList.add(Integer.parseInt(idsArr[i]));
//		}
//		return questionRepository.findAllById(idsList);
		return questionRepository.findAllByIdOrderByDifficulty(ids);
	}

	@Override
	public String getTypeNameById(Integer id) {
		Integer typeId = questionRepository.getOne(id).getTypeId();
		
		return questionTypeRepository.getOne(typeId).getName();
	}

	@Override
	public long countBySubjectId(Integer subjectId) {
		return questionRepository.countBySubjectId(subjectId);
	}

	@Override
	public boolean deleteById(Integer id) {
		List<Paper> papers = paperService.listAll();
		List<String> idsList = new ArrayList<>();
		for(Paper p : papers) {
			idsList.add(p.getContent());
		}
		for(String ids : idsList) {
			if(ids.contains(id + "")) {
				return false;
			}
		}
		questionRepository.deleteById(id);
		return true;
	}

	@Override
	public List<String> getOptionContentsByOptions(Question question, List<String> options) {
		List<String> contents = new ArrayList<String>();
		for(String option : options) {
			if(AnswerEnum.A.getValue().equals(option.trim())) {
				contents.add(question.getOptionA());
			}else if(AnswerEnum.B.getValue().equals(option.trim())) {
				contents.add(question.getOptionB());
			}else if(AnswerEnum.C.getValue().equals(option.trim())) {
				contents.add(question.getOptionC());
			}else if(AnswerEnum.D.getValue().equals(option.trim())) {
				contents.add(question.getOptionD());
			}else {
				
			}
		
		}
		return contents;
	}

	@Override
	public void saveList(List<Question> questions) {
		questionRepository.saveAll(questions);
	}

}
