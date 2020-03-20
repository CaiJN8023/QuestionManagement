package com.questionManagement.util.GA;

/**
 * 
 * @author CJN
 * @date 2019年3月23日
 * Title: Rule 
 * Description: 教职工填写的试卷组卷规则类
 */
public class Rule {

	private Integer id;
	
	private Integer subjectId;
	
	// 试卷总分
	private int totalMark;
	
	// 试卷难度系数
	private double difficulty;
	
	// 试卷包含试题最小章节
	private int minChapter;
	
	// 试卷包含试题最大章节
	private int maxChapter;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public int getTotalMark() {
		return totalMark;
	}

	public void setTotalMark(int totalMark) {
		this.totalMark = totalMark;
	}

	public double getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(double difficulty) {
		this.difficulty = difficulty;
	}

	public int getMinChapter() {
		return minChapter;
	}

	public void setMinChapter(int minChapter) {
		this.minChapter = minChapter;
	}

	public int getMaxChapter() {
		return maxChapter;
	}

	public void setMaxChapter(int maxChapter) {
		this.maxChapter = maxChapter;
	}

	@Override
	public String toString() {
		return "Rule [id=" + id + ", subjectId=" + subjectId + ", totalMark=" + totalMark + ", difficulty=" + difficulty
				+ ", minChapter=" + minChapter + ", maxChapter=" + maxChapter + "]";
	}

}
