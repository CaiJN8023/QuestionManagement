package com.questionManagement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.questionManagement.constant.fieldEnum.QuestionTypeEnum;
import com.questionManagement.entity.Paper;
import com.questionManagement.entity.Question;
import com.questionManagement.entity.Subject;
import com.questionManagement.entity.dto.PaperDTO;
import com.questionManagement.repository.IPaperRepository;
import com.questionManagement.repository.ISubjectRepository;
import com.questionManagement.repository.IUserRepository;
import com.questionManagement.service.IAnswerService;
import com.questionManagement.service.IPaperService;
import com.questionManagement.service.IQuestionService;

/**
 * 
 * @author CJN
 * @date 2019年3月7日
 * Title: PaperServiceImpl 
 * Description: Paper实体业务接口实现类
 */
@Service
public class PaperServiceImpl implements IPaperService {
	
	@Autowired
	private IPaperRepository paperRepository;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IQuestionService questionService;
	
	@Autowired
	private ISubjectRepository subjectRepository;
	
	@Autowired
	private IAnswerService answerService;
	

	@Override
	public Paper getById(Integer id) {
		return paperRepository.getOne(id);
	}

	@Override
	public Paper save(Paper paper) {
		return paperRepository.save(paper);
	}

	@Override
	public PaperDTO getDTOById(Integer id) {
		Paper paper = paperRepository.getOne(id);
		Subject subject = subjectRepository.getOne(paper.getSubjectId());
		int creatorId = paper.getCreatorId();
		String creatorName = userRepository.getOne(creatorId).getName();
		String subjectName = subject.getName();
		return new PaperDTO(paper, creatorName, subjectName);
	}

	@Override
	public List<Paper> listAll() {
		return paperRepository.findAll();
	}

	@Override
	public List<Paper> getListByCreatorId(Integer creatorId) {
		return paperRepository.findAllByCreatorId(creatorId);
	}

	@Override
	public List<PaperDTO> getDTOListByList(List<Paper> papers) {
		List<PaperDTO> paperDTOs = new ArrayList<PaperDTO>();
		for(int i = 0; i < papers.size(); i++) {
			int creatorId = papers.get(i).getCreatorId();
			int subjectId = papers.get(i).getSubjectId();
			String creatorName = userRepository.getOne(creatorId).getName();
			String subjectName = subjectRepository.getOne(subjectId).getName();
			paperDTOs.add(new PaperDTO(papers.get(i), creatorName, subjectName));
		}
		return paperDTOs;
	}

	@Override
	public void getKindsQuestionsById(Integer id, Map<Object, Object> map) {
		PaperDTO paperDTO = getDTOById(id);
		String ids = paperDTO.getPaper().getContent();
		List<Question> questions = questionService.getListByIds(ids);
//		Collections.shuffle(questions);
		
		// 筛选出不同题型集合，及计算每部分总分
		// 单选
		List<Question> single = new ArrayList<Question>();
		int singleScore = 0;
		
		// 多选
		List<Question> multi = new ArrayList<Question>();
		int multiScore = 0;
		
		// 判断
		List<Question> judge = new ArrayList<Question>();
		int judgeScore = 0;
		
		// 填空
		List<Question> complete = new ArrayList<Question>();
		int completeScore = 0;
		
		// 简答题
		List<Question> simple = new ArrayList<Question>(); 
		int simpleScore = 0;
		
		// 计算题
		List<Question> calculate = new ArrayList<Question>();
		int calculateScore = 0;
		
		// 思考题
		List<Question> thinking = new ArrayList<Question>();
		int thinkingScore = 0;
		
		// 主观
		List<Question> subjective = new ArrayList<Question>();
		int subjectiveScore = 0;
		
		int size = questions.size();
		for(int i = 0; i < size; i++) {
			Question ques = questions.get(i);
			
			String typeName = questionService.getTypeNameById(ques.getId());
			if(QuestionTypeEnum.SINGLE.getValue().equals(typeName)) {
				// 处理选项答案达到打乱选项的目的
				ques.setOptions();
				ques.setAnswers();
				singleScore += ques.getScore();
				single.add(ques);
				
			}else if(QuestionTypeEnum.MULTIPLE.getValue().equals(typeName)) {
				
				multiScore += ques.getScore();
				multi.add(ques);
				
			}else if(QuestionTypeEnum.JUDGE.getValue().equals(typeName)) {
				
				judgeScore += ques.getScore();
				judge.add(ques);
				
			}else if(QuestionTypeEnum.COMPLETE.getValue().equals(typeName)) {
				
				completeScore += ques.getScore();
				complete.add(ques);
				
			}else if(QuestionTypeEnum.SIMPLE.getValue().equals(typeName)){
				
				simpleScore += ques.getScore();
				simple.add(ques);
				
			}else if(QuestionTypeEnum.CALCULATE.getValue().equals(typeName)) {
				
				calculateScore += ques.getScore();
				calculate.add(ques);
				
			}else if(QuestionTypeEnum.THINKING.getValue().equals(typeName)) {
				
				thinkingScore += ques.getScore();
				thinking.add(ques);
				
			}else {
				
				subjectiveScore += ques.getScore();
				subjective.add(ques);
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
	public boolean isTestedPaper(Integer answererId, Integer paperId, String unMark, String marked) {
		long count = paperRepository.countByAnswererIdAndPaperIdAndStages(answererId, paperId, unMark, marked);
		return (count > 0);
	}

	@Override
	public boolean deleteById(Integer id) {
		long count = answerService.countByPaperId(id);
		if(count > 0) {
			return false;
		}
		paperRepository.deleteById(id);
		return true;
	}

	@Override
	public List<Paper> listByInviteCode(String inviteCode) {
		return paperRepository.findAllByInviteCode(inviteCode);
	}

}
