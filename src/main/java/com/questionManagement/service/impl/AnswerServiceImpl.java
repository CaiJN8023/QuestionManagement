package com.questionManagement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.questionManagement.constant.fieldEnum.QuestionTypeEnum;
import com.questionManagement.entity.Answer;
import com.questionManagement.entity.Question;
import com.questionManagement.entity.dto.AnswerDTO;
import com.questionManagement.entity.dto.AnswerQuestionDTO;
import com.questionManagement.entity.dto.PaperDTO;
import com.questionManagement.repository.IAnswerRepository;
import com.questionManagement.service.IAnswerService;
import com.questionManagement.service.IPaperService;
import com.questionManagement.service.IQuestionService;
import com.questionManagement.service.IUserService;

/**
 * 
 * @author CJN
 * @date 2019年3月7日
 * Title: AnswerServiceImpl 
 * Description: Answer实体业务接口实现类
 */
@Service
public class AnswerServiceImpl implements IAnswerService {

	@Autowired
	private IAnswerRepository answerRepository;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IPaperService paperService;
	
	@Autowired
	private IQuestionService questionService;
	
	
	@Override
	public void saveAll(List<Answer> answers) {
		answerRepository.saveAll(answers);
	}

	@Override
	public long countByAnswererId(Integer answererId) {
		return answerRepository.countByAnswererId(answererId);
	}

	@Override
	public long countByPaperId(Integer paperId) {
		return answerRepository.countByPaperId(paperId);
	}

	@Override
	public List<Answer> listByPaperIdAndGroupByAnswerId(Integer paperId) {
		return answerRepository.findByPaperIdAndGroupByAnswererId(paperId);
	}

	@Override
	public List<AnswerDTO> getDTOListByList(List<Answer> answers) {
		List<AnswerDTO> answerDTOs = new ArrayList<>();
		for(Answer answer : answers) {
			String answerName = userService.getById(answer.getAnswererId()).getName();
			String paperName = paperService.getById(answer.getPaperId()).getName();
			answerDTOs.add(new AnswerDTO(answerName, paperName, answer));
		}
		return answerDTOs;
	}

	@Override
	public List<AnswerQuestionDTO> listAnswerQuestionDTOByAnswererIdAndPaperId(
							Integer answererId, Integer paperId) {
		List<Answer> answers = answerRepository.findByAnswererIdAndPaperId(answererId, paperId);
		List<AnswerQuestionDTO> answerQuestionDTOs = new ArrayList<>();
		for(Answer answer : answers) {
			Question question = questionService.getById(answer.getQuestionId());
			answerQuestionDTOs.add(new AnswerQuestionDTO(question, answer));
		}
		return answerQuestionDTOs;
	}

	@Override
	public List<Answer> listByAnswererIdAndPaperId(Integer answererId, Integer paperId) {
		
		return answerRepository.findByAnswererIdAndPaperId(answererId, paperId);
	}

	@Override
	public void listKindsAnswerQuestionByAnswererIdAndPaperId(Integer answererId,
							Integer paperId, Map<Object, Object> map) {
		PaperDTO paperDTO = paperService.getDTOById(paperId);
		
		List<AnswerQuestionDTO> anQuDTOs = listAnswerQuestionDTOByAnswererIdAndPaperId(answererId, paperId);
		
		// 筛选出不同题型集合
		// 筛选出不同题型集合，及计算每部分总分
		// 单选
		List<AnswerQuestionDTO> single = new ArrayList<AnswerQuestionDTO>();
		int singleScore = 0;
		
		// 多选
		List<AnswerQuestionDTO> multi = new ArrayList<AnswerQuestionDTO>();
		int multiScore = 0;
		
		// 判断
		List<AnswerQuestionDTO> judge = new ArrayList<AnswerQuestionDTO>();
		int judgeScore = 0;
		
		// 填空
		List<AnswerQuestionDTO> complete = new ArrayList<AnswerQuestionDTO>();
		int completeScore = 0;
		
		// 简答题
		List<AnswerQuestionDTO> simple = new ArrayList<AnswerQuestionDTO>(); 
		int simpleScore = 0;
		
		// 计算题
		List<AnswerQuestionDTO> calculate = new ArrayList<AnswerQuestionDTO>();
		int calculateScore = 0;
		
		// 思考题
		List<AnswerQuestionDTO> thinking = new ArrayList<AnswerQuestionDTO>();
		int thinkingScore = 0;
		
		// 主观
		List<AnswerQuestionDTO> subjective = new ArrayList<AnswerQuestionDTO>();
		int subjectiveScore = 0;
				
		int size = anQuDTOs.size();
		for(int i = 0; i < size; i++) {
			AnswerQuestionDTO anQuDTO = anQuDTOs.get(i);
			String typeName = questionService.getTypeNameById(anQuDTO.getQuestion().getId());
			if(QuestionTypeEnum.SINGLE.getValue().equals(typeName)) {
				
				anQuDTO.getAnswer().setAnswers(anQuDTO.getAnswer().getOptions());
				anQuDTO.getAnswer().setContents(questionService.getOptionContentsByOptions(anQuDTO.getQuestion(), 
						anQuDTO.getAnswer().getAnswers()));
				singleScore += anQuDTO.getQuestion().getScore();
				single.add(anQuDTO);
				
			}else if(QuestionTypeEnum.MULTIPLE.getValue().equals(typeName)) {
				
				multiScore += anQuDTO.getQuestion().getScore();
				multi.add(anQuDTO);
				
			}else if(QuestionTypeEnum.JUDGE.getValue().equals(typeName)) {
				
				judgeScore += anQuDTO.getQuestion().getScore();
				judge.add(anQuDTO);
				
			}else if(QuestionTypeEnum.COMPLETE.getValue().equals(typeName)) {
				
				completeScore += anQuDTO.getQuestion().getScore();
				complete.add(anQuDTO);
				
			}else if(QuestionTypeEnum.SIMPLE.getValue().equals(typeName)){
				
				simpleScore += anQuDTO.getQuestion().getScore();
				simple.add(anQuDTO);
				
			}else if(QuestionTypeEnum.CALCULATE.getValue().equals(typeName)) {
				
				calculateScore += anQuDTO.getQuestion().getScore();
				calculate.add(anQuDTO);
				
			}else if(QuestionTypeEnum.THINKING.getValue().equals(typeName)) {
				
				thinkingScore += anQuDTO.getQuestion().getScore();
				thinking.add(anQuDTO);
				
			}else {
				
				subjectiveScore += anQuDTO.getQuestion().getScore();
				subjective.add(anQuDTO);
			}
		}
		map.put("paperDTO", paperDTO);
		map.put("single", single);
		map.put("singleScore", singleScore);
		map.put("multi", multi);
		map.put("multiScore", multiScore);
		map.put("judge", judge);
		map.put("judgeScore", judgeScore);
		map.put("complete", complete);
		map.put("completeScore", completeScore);
		map.put("simple", simple);
		map.put("simpleScore", simpleScore);
		map.put("calculate", calculate);
		map.put("calculateScore", calculateScore);
		map.put("thinking", thinking);
		map.put("thinkingScore", thinkingScore);
		map.put("subjective", subjective);
		map.put("subjectiveScore", subjectiveScore);
	}

	@Override
	public List<Answer> findByAnswererIdAndGroupByPaperId(Integer answererId) {
		return answerRepository.findByAnswererIdAndGroupByPaperId(answererId);
	}

	@Override
	public int calculateTotalScoreByAnswererIdAndPaperId(Integer answererId, Integer paperId) {
		return answerRepository.calculateTotalScoreByAnswererIdAndPaperId(answererId, paperId);
	}

}
