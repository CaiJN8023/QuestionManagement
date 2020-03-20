package com.questionManagement.constant.fieldEnum;

/**
 * 
 * @author CJN
 * @date 2019年3月5日
 * Title: UserStatusEnum 
 * Description: 用户状态枚举类
 */
public enum UserStatusEnum {
	
	NORMAL("正常"),		//用户状态正常
	EXCEPTION("异常"),	//用户状态异常
	FREEZING("冻结"),	//用户被冻结
	DESTROY("销毁");	//用户被消除

	private String value;
	
	UserStatusEnum(String value){
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
