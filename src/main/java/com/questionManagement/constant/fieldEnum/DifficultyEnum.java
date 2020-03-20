package com.questionManagement.constant.fieldEnum;

public enum DifficultyEnum {
	
	LEVEL1(1, "一级"),		//难度为：一级
	
	LEVEL2(2, "二级"),		//难度为：二级
	
	LEVEL3(3, "三级"),		//难度为：三级
	
	LEVEL4(4, "四级"),		//难度为：四级
	
	LEVEL5(5, "五级");		//难度为：五级
	
	private Integer value;
	
	private String name;

	DifficultyEnum(Integer value, String name){
		this.value = value;
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
