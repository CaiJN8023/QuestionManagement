package com.questionManagement.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author CJN
 * @date 2019年3月6日
 * Title: HttpInfoUtils 
 * Description: 处理请求头，获取请求头中信息工具类
 */
public class HttpInfoUtils {

	/**
	 *
	 * Title: getHeadersInfo
	 * Description: 处理Http请求，获取请求头
	 * @param request
	 * @return
	 * Map<String,String>
	 */
	public static Map<String, String> getHeadersInfo(HttpServletRequest request){
		Map<String, String> headersInfo = new HashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		String headerName = null;
		String headerValue = null;
		while(headerNames.hasMoreElements()) {
			headerName = headerNames.nextElement();
			headerValue = request.getHeader(headerName);
			headersInfo.put(headerName, headerValue);
		}
		return headersInfo;
	}
	
	/**
	 * 
	 * Title: getIpAdde
	 * Description: 从Http请求头获取客户端IP
	 * @param request
	 * @return
	 * String
	 */
	public static String getIpAdde(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client_IP");
		}
		if(ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client_IP");
		}
		if(ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
}
