package com.questionManagement.contorller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.questionManagement.entity.Question;
import com.questionManagement.repository.IQuestionRepository;

/**
 * 
 * @author CJN
 * @date 2019年3月1日
 * Title: ControllerExample 
 * Description: 控制器类示例
 */

@Controller
public class ControllerExample {
	
	@Autowired
	private IQuestionRepository questionRepository;

	/**
	 * 
	 * Title: example
	 * Description: 直接访问http://localhost:8080 则进入登录页面
	 * @return
	 * String
	 */
	@RequestMapping(value = "/")
	public String example() {
		return "index";
	}
	
	
	@RequestMapping(value = "/test")
	public String test() {
		String ids = "1,2,3,4,5";
		List<Question> questions = questionRepository.findAllByIdOrderByDifficulty(ids);
		System.out.println(questions.toString());
		return "success";
	}
	
}
