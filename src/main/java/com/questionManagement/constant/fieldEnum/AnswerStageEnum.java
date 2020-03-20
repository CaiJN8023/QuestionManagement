package com.questionManagement.constant.fieldEnum;

/**
 * 
 * @author CJN
 * @date 2019年3月27日
 * Title: AnswerStageEnum 
 * Description: 学生作答的答案情况
 */
public enum AnswerStageEnum {

	UNTEST("未测评"),
	UNMARK("待评分"),
	MARKED("已评分");
	
	private String value;
	
	AnswerStageEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
