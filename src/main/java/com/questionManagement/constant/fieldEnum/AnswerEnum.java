package com.questionManagement.constant.fieldEnum;

public enum AnswerEnum {

	A("A"),
	B("B"),
	C("C"),
	D("D"),
	TRUE("正确"),
	FALSE("错误");
	
	private String value;
	
	AnswerEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
