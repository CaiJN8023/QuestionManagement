package com.questionManagement;

import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.questionManagement.entity.Answer;
import com.questionManagement.entity.Question;
import com.questionManagement.repository.IAnswerRepository;
import com.questionManagement.repository.IQuestionRepository;

/**
 * 
 * @author CJN
 * @date 2019年3月30日
 * Title: AnswerRepositoryTest 
 * Description: 测试各个 Repository 中方法
 */
@Component
public class RepositorysTest {

	@Autowired
	private IAnswerRepository answerRepository;
	
	@Autowired
	private IQuestionRepository questionRepository;
	
	private static RepositorysTest a;
	
	@PostConstruct
	public void init() {
		a = this;
		a.answerRepository = this.answerRepository;
		a.questionRepository = this.questionRepository;
	}
	
	@Test
	public void testFindByPaperIdAndGroupByAnswererId() {
		Integer paperId = 5;
		List<Answer> answers = a.answerRepository.findByPaperIdAndGroupByAnswererId(paperId);
		System.out.println(answers.size());
	}
	
	
	@Test
	public void testFindAllByIdOrderByDifficulty() {
		String ids = "1,2,3,4,5";
		List<Question> questions = a.questionRepository.findAllByIdOrderByDifficulty(ids);
		System.out.println(questions.toString());
	}
}
