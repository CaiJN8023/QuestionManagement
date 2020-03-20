package com.questionManagement.constant.fieldEnum;

/**
 * 
 * @author CJN
 * @date 2019年3月5日
 * Title: UserSexEnum 
 * Description: 用户性别枚举类
 */
public enum UserSexEnum {

	MALE("男"),			//性别男
	FEMALE("女"),		//性别女
	SECRET("保密");		//性别保密
	
	private String value;
	
	UserSexEnum(String value) {
		this.value = value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
