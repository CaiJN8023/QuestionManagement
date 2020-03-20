package com.questionManagement.entity.dto;

import com.questionManagement.entity.Paper;

/**
 * 
 * @author CJN
 * @date 2019年3月25日
 * Title: PaperDTO 
 * Description: Paper 实体类相关数据传输对象
 */
public class PaperDTO {

	private Paper paper;
	
	private String creatorName;
	
	private String subjectName;
	
	public PaperDTO() {
		super();
	}
	
	public PaperDTO(Paper paper, String creatorName, String subjectName) {
		super();
		this.paper = paper;
		this.creatorName = creatorName;
		this.subjectName = subjectName;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@Override
	public String toString() {
		return "PaperDTO [paper=" + paper + ", creatorName=" + creatorName + ", subjectName=" + subjectName + "]";
	}
	
}
