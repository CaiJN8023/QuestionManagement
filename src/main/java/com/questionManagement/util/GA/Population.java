package com.questionManagement.util.GA;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.questionManagement.constant.CreatePaper;
import com.questionManagement.entity.Question;
import com.questionManagement.entity.QuestionType;
import com.questionManagement.service.IQuestionService;
import com.questionManagement.service.ISubjectService;
import com.questionManagement.util.SpringContextHolder;

/**
 * 
 * @author CJN
 * @date 2019年3月24日
 * Title: Population 
 * Description: 试卷种群类
 */
@Component
@DependsOn("springContextHolder")
public class Population {

	// 试卷集合，即种群
	private Paper[] papers;
	
	private static ISubjectService subjectService = SpringContextHolder.getBean(ISubjectService.class);
	
	private static IQuestionService questionService = SpringContextHolder.getBean(IQuestionService.class);
	
	public Population() {
		super();
	}
	
	/**
	 * 
	 * Title: 
	 * Description: 初始化种群，符合基本要求 
	 * @param populationSize
	 * @param initFlag
	 * @param rule
	 * @param req
	 */
	public Population(int populationSize, boolean initFlag, Rule rule, HttpServletRequest req) {
		papers = new Paper[populationSize];
		if(initFlag) {
			Paper paper;
			Random random = new Random();
			for(int i = 0; i < populationSize; i++) {
				paper = new Paper();
				paper.setId(i + 1);
				while(paper.getTotalScore() != rule.getTotalMark()) {
					paper.getQuestionList().clear();
					
					// 获取当前试卷所属学科拥有题型
					Integer subjectId = rule.getSubjectId();
					List<QuestionType> types = subjectService.getTypesById(subjectId);
					
					// 遍历所有题型获取对应所需题型数及题型分值，以及所属章节
					for(QuestionType type : types) {
						if(Integer.parseInt(req.getParameter(type.getName() + "数量")) > 0) {
							int typeId = type.getId();
							int typeNum = Integer.parseInt(req.getParameter(type.getName() + "数量"));
							int typeScore = Integer.parseInt(req.getParameter(type.getName() + "分值"));
							int minChapter = rule.getMinChapter();
							int maxChapter = rule.getMaxChapter();
							String errorMsg = type.getName() + "数量不够，组卷失败！";
							
							generateQuestion(typeId, random, typeNum, typeScore, minChapter, maxChapter,
												errorMsg, paper);
							paper.setChapterCoverage(rule);
							paper.setAdaptationDegree(rule, CreatePaper.CHAPTER_WEIGHT, CreatePaper.DIFFCULTY_WEIGHT);
							papers[i] = paper;
						}
					}
				}
				
			}
		}
	}

	/**
	 * 
	 * Title: generateQuestion
	 * Description: 根据组卷规则找出符合基本要求的试卷
	 * @param typeId
	 * @param random
	 * @param typeNum
	 * @param typeScore
	 * @param minChapter
	 * @param maxChapter
	 * @param errorMsg
	 * @param paper
	 * void
	 */
	private void generateQuestion(int typeId, Random random, int typeNum, int typeScore, int minChapter, int maxChapter,
			String errorMsg, Paper paper) {
		List<Question> questions = questionService.getPaperQuestionList(typeId, typeScore, minChapter, maxChapter);
		int size = questions.size();
		if(size < typeNum) {
			System.out.println(errorMsg);
			return ;
		}
		
		for(int i = 0; i < typeNum; i++) {
			Question question;
			// 取出位置0-(size-1) 的随机一个question对象，放入paper中
			int index = random.nextInt(size - i);
			paper.addQuestion(questions.get(index));
			// 并将刚才取出的移到最后，保证每次从前面开始取不会重复
			question = questions.get(size - i - 1);
			questions.set(size - i - 1, questions.get(index));
			questions.set(index, question);
			
		}
	}
	
	/**
     * 获取种群中最优秀个体
     *
     * @return
     */
    public Paper getFitness() {
        Paper paper = papers[0];
        for (int i = 1; i < papers.length; i++) {
            if (paper.getAdaptationDegree() < papers[i].getAdaptationDegree()) {
                paper = papers[i];
            }
        }
        return paper;
    }

    public Population(int populationSize) {
        papers = new Paper[populationSize];
    }

    /**
     * 获取种群中某个个体
     *
     * @param index
     * @return
     */
    public Paper getPaper(int index) {
        return papers[index];
    }

    /**
     * 设置种群中某个个体
     *
     * @param index
     * @param paper
     */
    public void setPaper(int index, Paper paper) {
        papers[index] = paper;
    }

    /**
     * 返回种群规模
     *
     * @return
     */
    public int getLength() {
        return papers.length;
    }
}
