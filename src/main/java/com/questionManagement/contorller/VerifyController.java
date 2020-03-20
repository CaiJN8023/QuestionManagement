package com.questionManagement.contorller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.questionManagement.util.VerifyUtils;

/**
 * 
 * @author CJN
 * @date 2019年3月6日
 * Title: VerifyController 
 * Description: 验证码的生成和校验控制器
 */
@Controller
@RequestMapping(value = "/verify")
public class VerifyController {

	/**
	 * 
	 * Title: getImage
	 * Description: 获取验证码图片
	 * @param request
	 * @param response
	 * void
	 */
	@GetMapping(value = "/getImage")
	@ResponseBody
	public void getImage(HttpServletRequest request, HttpServletResponse response) {
		VerifyUtils.createNewVerifyImage(request, response);;
	}
	
	/**
	 * 
	 * Title: checkCode
	 * Description: 校验验证码正确与否
	 * @param request
	 * @param response
	 * @return
	 * boolean
	 */
	@GetMapping(value = "/checkCode")
	@ResponseBody
	public boolean checkCode(HttpServletRequest request, HttpServletResponse response) {
		// 分别获取用户输入验证码和存在session中的正确验证码
		String requestCode = request.getParameter("verifyCode");
		String sessionCode = (String) request.getSession().getAttribute("verifyCode");
		
		if(requestCode != null && sessionCode != null && requestCode.equalsIgnoreCase(sessionCode)) {
			// 检验正确则将校验结果改为 true
			request.getSession().setAttribute("checkResult", true);
			return true;
		}else {
			return false;
		}
	}
	
}
