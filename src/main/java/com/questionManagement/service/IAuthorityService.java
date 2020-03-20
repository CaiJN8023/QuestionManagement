package com.questionManagement.service;

import java.util.List;

import com.questionManagement.entity.Authority;

/**
 * 
 * @author CJN
 * @date 2019年3月7日
 * Title: IAuthorityService 
 * Description: Authority实体对应业务接口
 */
public interface IAuthorityService {

	/**
	 * 
	 * Title: listFatherNode
	 * Description: 找出所有权限父节点
	 * @return
	 * List<Authority>
	 */
	List<Authority> listFatherNode();
	
	/**
	 * 
	 * Title: listChilNode
	 * Description: 找出所有权限子节点
	 * @return
	 * List<Authority>
	 */
	List<Authority> listChilNode();
	
	/**
	 * 
	 * Title: listByParentId
	 * Description: 通过父节点查找子节点
	 * @param parentId
	 * @return
	 * List<Authority>
	 */
	List<Authority> listByParentId(Integer parentId);
	
	List<Authority> listAll();
	
	List<Authority> ListByRoleId(Integer roleId);
	
}
