package com.questionManagement.util.GA;

import java.util.ArrayList;
import java.util.List;

import com.questionManagement.entity.Question;

/**
 * 
 * @author CJN
 * @date 2019年3月23日
 * Title: Paper 
 * Description: 试卷个体
 */
public class Paper {

	private Integer id;
	
	// 试卷个体适应度
	private double adaptationDegree = 0.00;
	
	// 试卷个体的章节覆盖度
	private double chapterCoverage = 0.00;
	
	// 试卷的总分
	private int totalScore = 0;
	
	// 试卷的难度系数
	private double difficulty = 0.00;
	
	// 试卷所包含试题集合
	private List<Question> questionList = new ArrayList<Question>();
	
	// 初始化试题集合
	public Paper(int size) {
		for(int i = 0; i < size; i++) {
			questionList.add(null);
		}
	}
	
	public Paper() {
		super();
	}
	
	/**
	 * 
	 * Title: getTotalScore
	 * Description: 返回试卷总分
	 * @return
	 * double
	 */
	public double getTotalScore() {
		if(totalScore == 0) {
			int total = 0;
			for(int i = 0; i < questionList.size(); i++) {
				total += questionList.get(i).getScore();
			}
			totalScore = total;
		}
		return totalScore;
	}
	
	/**
	 * 
	 * Title: getDifficulty
	 * Description: 获取试卷难度系数
	 * @return
	 * double
	 */
	public double getDifficulty() {
		if(difficulty == 0) {
			double diff = 0.0;
			for(int i = 0; i < questionList.size(); i++) {
				diff += questionList.get(i).getScore() * questionList.get(i).getDifficulty();
			}
			difficulty = diff / getTotalScore() / 5;
		}
		
		return difficulty;
	}
	
	/**
	 * 
	 * Title: getQuestionSize
	 * Description: 获取试题数量
	 * @return
	 * int
	 */
	public int getQuestionSize() {
		return questionList.size();
	}
	
	/**
	 * 
	 * Title: setChapterCoverage
	 * Description: 计算章节覆盖率
	 * @param rule
	 * void
	 */
	public void setChapterCoverage(Rule rule) {
		if(chapterCoverage == 0) {
			int size = questionList.size();
			int count = 1;
			int minChapter = rule.getMinChapter();
			int maxChapter = rule.getMaxChapter();
			for(int i = 0; i < size; i++) {
				int chapter = questionList.get(i).getChapter();
				if(chapter >= minChapter && chapter<= maxChapter) {
					count ++;
				}
			}
			chapterCoverage = count / size;
		}
	}
	
	/**
	 * 
	 * Title: setAdaptationDegree
	 * Description:计算个体适应度 公式为：f=1-(1-M/N)*f1-|EP-P|*f2
     * 			    其中M/N为章节覆盖率，EP为期望难度系数，P为种群个体难度系数，f1为章节分布的权重
     * 			    ，f2为难度系数所占权重。当f1=0时退化为只限制试题难度系数，当f2=0时退化为只限制章节分布
	 * @param rule
	 * @param f1
	 * @param f2
	 * void
	 */
	public void setAdaptationDegree(Rule rule, double f1, double f2) {
		if(adaptationDegree == 0) {
			adaptationDegree = 1 - (1 - getChapterCoverage()) * f1 - 
							Math.abs(rule.getDifficulty() - getDifficulty()) * f2;
		}
	}
	
	public double getChapterCoverage() {
		return chapterCoverage;
	}
	
	/**
	 * 
	 * Title: containsQuestion
	 * Description: 判断试题列表是否包含某个试题
	 * @param question
	 * @return
	 * boolean
	 */
	public boolean containsQuestion(Question question) {
		if(question == null) {
			for(int i = 0; i < questionList.size(); i ++) {
				if(questionList.get(i) == null) {
					return true;
				}
			}
		}else {
			for(int i = 0; i < questionList.size(); i ++) {
				if(questionList.get(i) != null) {
					if(question.equals(questionList.get(i))) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * Title: saveQuestion
	 * Description: 为试题列表添加试题
	 * @param index
	 * @param question
	 * void
	 */
	public void saveQuestion(int index, Question question) {
        this.questionList.set(index, question);
        this.totalScore = 0;
        this.adaptationDegree = 0;
        this.difficulty = 0;
        this.chapterCoverage = 0;
    }

    public void addQuestion(Question question) {
        this.questionList.add(question);
        this.totalScore = 0;
        this.adaptationDegree = 0;
        this.difficulty = 0;
        this.chapterCoverage = 0;
    }
	
    /**
     * 
     * Title: getQuestion
     * Description: 返回指定位置的试题对象
     * @param index
     * @return
     * Question
     */
    public Question getQuestion(int index) {
    	return questionList.get(index);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAdaptationDegree() {
        return adaptationDegree;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    } 
}
