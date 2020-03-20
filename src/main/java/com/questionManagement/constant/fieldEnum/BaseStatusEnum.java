package com.questionManagement.constant.fieldEnum;

/**
 * 
 * @author CJN
 * @date 2019年3月6日
 * Title: BaseEnum 
 * Description: 基础状态枚举类
 */
public enum BaseStatusEnum {

	NORMAL("正常"),		//状态：正常
	DESTORY("销毁");	//状态：销毁
	
	private String value;
	
	BaseStatusEnum(String value){
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
