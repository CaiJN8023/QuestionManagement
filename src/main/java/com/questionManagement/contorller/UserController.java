package com.questionManagement.contorller;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.questionManagement.constant.WebFile;
import com.questionManagement.constant.fieldEnum.BaseStatusEnum;
import com.questionManagement.constant.fieldEnum.UserSexEnum;
import com.questionManagement.constant.fieldEnum.UserStatusEnum;
import com.questionManagement.entity.Authority;
import com.questionManagement.entity.Role;
import com.questionManagement.entity.RoleAuthority;
import com.questionManagement.entity.User;
import com.questionManagement.entity.dto.UserDTO;
import com.questionManagement.repository.IAuthorityRepository;
import com.questionManagement.repository.IRoleRepository;
import com.questionManagement.repository.IUserRepository;
import com.questionManagement.service.IUserService;
import com.questionManagement.util.FileUtil;

/**
 * 
 * @author CJN
 * @date 2019年3月6日
 * Title: UserController 
 * Description: User实体类对应控制器类，处理关于User业务逻辑
 */
@Controller
public class UserController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IAuthorityRepository authorityRepository;
	
	@Autowired
	private IRoleRepository roleRepository;
	
	@javax.annotation.Resource
	private ResourceLoader resourceLoader;
	
	// 定义枚举类列表，传入前台获取选中对应选项
	private static List<UserSexEnum> sexes = new ArrayList<UserSexEnum>();
	
	private static List<UserStatusEnum> statuses = new ArrayList<UserStatusEnum>();
	static {
		sexes.add(UserSexEnum.MALE);
		sexes.add(UserSexEnum.FEMALE);
		sexes.add(UserSexEnum.SECRET);
		statuses.add(UserStatusEnum.NORMAL);
		statuses.add(UserStatusEnum.FREEZING);
		statuses.add(UserStatusEnum.EXCEPTION);
		statuses.add(UserStatusEnum.DESTROY);
	}
	
	@GetMapping(value = "/login")
	public String login() {
		return "index";
	}
	
	@PostMapping(value = "/loginConfirm")
	@ResponseBody
	public boolean loginConfirm(@RequestBody User user, HttpSession session) {
		User u = userService.login(user);
		if(u != null) {
			System.out.println("User Login: " + u.getAccount() + ", " + u.getPassword());
			session.setAttribute("user", u);
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 
	 * Title: toMain
	 * Description: 登录成功则进入系统首页，需要根据角色权限分配菜单
	 * @return
	 * String
	 */
	@RequestMapping(value = "/toMain")
	public String toMain(HttpSession session, Map<Object, Object> map) {
		User user = (User) session.getAttribute("user");
		
		// 根据用户 RoleId 找出所有权限
		List<RoleAuthority> roleAuthorities = userService.checkAuthority(user);
		List<Authority> childNodes = new ArrayList<Authority>();
		List<Authority> fatherNodes = new ArrayList<Authority>();
//		List<SubjectDTO> subjectDTOs = subjectService.listAllSubjectDTO();
		Authority authority = new Authority();
		
		// 将所有权限节点分为父节点和子节点
		if(roleAuthorities.size() > 0 && roleAuthorities != null) {
			for(RoleAuthority roleAuthority : roleAuthorities) {
				authority = authorityRepository.getOne(roleAuthority.getAuthorityId());
				if(authority.getParentId() == -1) {
					fatherNodes.add(authority);
				}else {
					childNodes.add(authority);
				}
			}
		}
		
		Role role = roleRepository.getOne(user.getRoleId());
//		List<SubjectDTO> subjectDTOs = new ArrayList<SubjectDTO>();
		
		map.put("roleAuthorities", roleAuthorities);
		map.put("fatherNodes", fatherNodes);
		map.put("childNodes", childNodes);
		map.put("roleName", role.getName());
		
		session.setAttribute("fatherNodes", fatherNodes);
		session.setAttribute("childNodes", childNodes);
		session.setAttribute("roleName", role.getName());
		
		// 根据不同角色返回不同信息
		if(role.getName().contains("教职工")) {
//			subjectDTOs = subjectService.getDTOListByList(subjectService.listByCreatorId(user.getId()));
//			map.put("subjectDTOs", subjectDTOs);
			return "redirect:/questionTypeMana";
		}else if(role.getName().contains("管理员")) {
			return "redirect:/userMana/infoMana";
		}else {
			return "redirect:/stuTest/onlineTest";
		}
	}
	
	/**
	 * 
	 * Title: toMyInfo
	 * Description: 去查看我的信息
	 * @param id
	 * @param map
	 * @return
	 * String
	 */
	@GetMapping(value = "/toMyInfo")
	public String toMyInfo(@RequestParam Integer id, Map<Object, Object> map) {
		User user = userRepository.getOne(id);
		map.put("user", user);
		map.put("sexes", sexes);
		map.put("statuses", statuses);
		List<Role> roles = roleRepository.findAll();
		map.put("roles", roles); 
		return "manager/usersMana/myInfo";
	}
	
	/**
	 * 
	 * Title: toMyResetPassword
	 * Description: 去修改我的密码页面
	 * @param id
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/toMyResetPassword")
	public String toMyResetPassword(@RequestParam(value = "id") Integer id, Map<Object, Object> map) {
		User user = userRepository.getOne(id);
		List<Role> roles = roleRepository.findAll();
		
		map.put("user", user);
		map.put("sexes", sexes);
		map.put("statuses", statuses);
		map.put("roles", roles); 
		return "manager/usersMana/resetPassword";
		
	}
	
	/**
	 * 
	 * Title: updatePassword
	 * Description: 提交修改密码请求，并转向登录
	 * @param user
	 * @param session
	 * @return
	 * String
	 */
	@RequestMapping(value = "/updatePassword")
	public String updatePassword(User user, HttpSession session) {
		System.out.println(user.getPassword());
		User sessionUser = (User) session.getAttribute("user");
		sessionUser.setPassword(user.getPassword());
		userService.save(sessionUser);
		return "redirect:/login";
	}
	
	/**
	 * 
	 * Title: logout
	 * Description: 登出，转到登录页面
	 * @return
	 * String
	 */
	@RequestMapping(value = "/logout")
	public String logout() {
		return "redirect:/login";
	}
	
	/**
	 * 
	 * Title: userMana
	 * Description: 去到所有用户信息列表
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/userMana/infoMana")
	public String userMana(Map<Object, Object> map) {
		List<User> users = userService.listAll();
		List<UserDTO> userDTOs = userService.getDTOListByList(users);
		map.put("userDTOs", userDTOs);
		return "manager/usersMana/users_info";
	}
	
	/**
	 * 
	 * Title: userInfo
	 * Description: 查看一个用户信息
	 * @param id
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/userInfo")
	public String userInfo(@RequestParam(value = "id") Integer id, Map<Object, Object> map) {
		UserDTO userDTO = userService.getUserDTOById(id);
		List<Role> roles = roleRepository.findAll();
		map.put("roles", roles);
		map.put("sexes", sexes);
		map.put("statuses", statuses);
		map.put("userDTO", userDTO);
		return "manager/usersMana/user_info";
	}
	
	/**
	 * 
	 * Title: userEdit
	 * Description: 编辑用户信息
	 * @param id
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/userEdit")
	public String userEdit(@RequestParam(value = "id") Integer id, Map<Object, Object> map) {
		UserDTO userDTO = userService.getUserDTOById(id);
		List<Role> roles = roleRepository.findAll();
		map.put("roles", roles);
		map.put("sexes", sexes);
		map.put("statuses", statuses);
		map.put("userDTO", userDTO);
		return "manager/usersMana/user_edit";
	}
	
	/**
	 * 
	 * Title: userUpdate
	 * Description: 更新保存用户信息
	 * @param user
	 * @return
	 * String
	 */
	@RequestMapping(value = "/userUpdate")
	public String userUpdate(User user) {
		Date date = new Date(new java.util.Date().getTime());
		user.setUpdateTime(date);
		System.out.println(user.toString());
		userService.save(user);
		return "redirect:/userMana/infoMana";
	}
	
	/**
	 * 
	 * Title: userInsert
	 * Description: 去到添加用户页面
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/userInsert")
	public String userInsert(Map<Object, Object> map) {
		List<Role> roles = roleRepository.findAll();
		map.put("roles", roles);
		map.put("sexes", sexes);
		map.put("statuses", statuses);
		
		return "manager/usersMana/user_insert";
	}
	
	/**
	 * 
	 * Title: userSave
	 * Description: 保存添加用户信息
	 * @param user
	 * @return
	 * String
	 */
	@RequestMapping(value = "/userSave")
	public String userSave(User user) {
		Date date = new Date(new java.util.Date().getTime());
		user.setCreateTime(date);
		user.setUpdateTime(date);
		userRepository.save(user);
		return "redirect:/userMana/infoMana";
	}
	
	/**
	 * 
	 * Title: userDelete
	 * Description: 删除用户信息，需要判断是否存在该用户创建的学科
	 * @param id
	 * @return
	 * String
	 */
	@RequestMapping(value = "/userDelete")
	@ResponseBody
	public Boolean userDelete(@RequestParam(value = "id") Integer id) {
		return userService.delete(id);
	}
	
	/**
	 * 
	 * Title: toStuRegister
	 * Description: 去到用户注册页面
	 * @param map
	 * @return
	 * String
	 */
	@RequestMapping(value = "/toStuRegister")
	public String toStuRegister(Map<Object, Object> map) {
		map.put("sexes", sexes);
		return "manager/usersMana/student_register";
	}
	
	/**
	 * 
	 * Title: isAccountExsit
	 * Description: 查询是否账户已经存在
	 * @param account
	 * @return
	 * boolean
	 */
	@RequestMapping(value = "/isAccountExsit")
	@ResponseBody
	public boolean isAccountExsit(@RequestParam(value = "account")String account) {
		
		return userService.isAccountExsit(account);
	}
	
	/**
	 * 
	 * Title: studentRegister
	 * Description: 用户注册
	 * @param user
	 * @return
	 * String
	 */
	@RequestMapping(value = "/studentRegister")
	public String studentRegister(User user) {
		Date date = new Date(new java.util.Date().getTime());
		user.setCreateTime(date);
		user.setUpdateTime(date);
		user.setStatus(BaseStatusEnum.NORMAL.getValue());
		user.setRoleId(roleRepository.findByNameLike("学生").getId());
		userService.save(user);
		return "redirect:/login";
	}
	
	/**
	 * 
	 * Title: tobatchImport
	 * Description: 去到批量添加用户页面
	 * @return
	 * String
	 */
	@RequestMapping(value = "/userMana/batchImport")
	public String tobatchImport() {
		return "manager/usersMana/user_batch_insert";
	}
	
	/**
	 * 
	 * Title: downloadUserTemplate
	 * Description: 下载用户信息模板
	 * @param fileName
	 * @param res
	 * @return
	 * String
	 */
	@RequestMapping(value = "/downloadUserTemplate")
	public String downloadUserTemplate(@RequestParam(value = "fileName") String fileName, HttpServletResponse res) {
		
		// 下载模板
		FileUtil.downloadFile(fileName, res);
		
		return null;
	}
	
	/**
	 * 
	 * Title: batchInsertUser
	 * Description: 上传用户信息文件，并读取文件生成用户list保存到数据库
	 * @param file
	 * @return
	 * String
	 */
	@RequestMapping(value = "/batchInsertUser")
	@ResponseBody
	public String batchInsertUser(@RequestParam(value = "excelFile")MultipartFile file) {
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 获取文件扩展名
		String extention = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		if(!extention.equalsIgnoreCase("xls")) {
			return "文件格式不对，请重新上传xls格式文件！";
		}
		// 创建文件夹
		File f = new File(WebFile.uploadDir);
		if(!f.exists()) {
			f.mkdir();
		}
		// 传输文件
		String fileDir = WebFile.uploadDir + File.separator + fileName;
		try {
			file.transferTo(new File(fileDir));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 从文件中读取用户信息保存为 list
		List<User> users = FileUtil.getUsersFromFile(fileDir);
		if(users.size() <= 0) {
			return "请按照正确格式填写用户信息模板！";
		}
		userService.saveList(users);
		return "批量保存用户成功！";
	}
	
}
