package com.questionManagement.constant.fieldEnum;


/**
 * 
 * @author CJN
 * @date 2019年4月14日
 * Title: QuestionTypeEnum 
 * Description: 将题型定义为以下8种，且不可更改
 */
public enum QuestionTypeEnum {
	
	SINGLE("单选题"),
	MULTIPLE("多选题"),
	JUDGE("判断题"),
	COMPLETE("填空题"),
	SIMPLE("简答题"),
	CALCULATE("计算题"),
	THINKING("思考题"),
	SUBJECTIVE("主观题");

	private String value;
	
	QuestionTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
