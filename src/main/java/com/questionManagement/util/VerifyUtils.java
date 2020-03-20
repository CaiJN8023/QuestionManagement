package com.questionManagement.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author CJN
 * @date 2019年3月6日 Title: VerifyUtils Description: 生成验证码工具类
 */
public class VerifyUtils {

	// 验证码图片尺寸
	private static final int IMAGE_WIDTH = 120;
	private static final int IMAGE_HEIGHT = 50;
	// 验证码长度
	private static final int VERIFY_LENGTH = 4;
	// 邀请码长度
	private static final int INVITE_LEGTH = 6;

	// 验证码字体及待选字符
	static final String[] FONTS = { "方正舒体", "华文彩云", "华文琥珀", "华文新魏", "幼圆", "微软雅黑", "楷体", "Agency FB", "Bradley Hand ITC",
			"Copperplate Gothic Light" };
	static final String[] CHARS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G",
			"H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
			"r", "s", "t", "u", "v", "w", "x", "y", "z"};
	
	/**
	 * 
	 * Title: get3RandColor
	 * Description: 随机获取三种颜色
	 * @return
	 * Color[]
	 */
	public static Color[] get3RandColor() {
		Color[] c = new Color[3];
		Random ran = new Random();
		if (ran.nextInt(2) == 1) {
			c[0] = new Color(120 + ran.nextInt(50), 120 + ran.nextInt(50), 120 + ran.nextInt(50));
			c[1] = new Color(20 + ran.nextInt(80), 20 + ran.nextInt(80), 20 + ran.nextInt(80));
			c[2] = new Color(120 + ran.nextInt(120), 12 + ran.nextInt(50), 120 + ran.nextInt(120));
		} else {
			c[1] = new Color(150 + ran.nextInt(80), 150 + ran.nextInt(80), 150 + ran.nextInt(80));
			c[0] = new Color(20 + ran.nextInt(80), 20 + ran.nextInt(80), 10 + ran.nextInt(80));
			c[2] = new Color(ran.nextInt(100), ran.nextInt(100), ran.nextInt(100));
		}
		return c;
	}
	
	/**
	 * 
	 * Title: getRandFONTS
	 * Description: 随机获取待选字体
	 * @param index
	 * @return
	 * String
	 */
	public static String getRandFONTS(int index) {
		return FONTS[index];
	}
	
	/**
	 * 
	 * Title: getVerifyCode
	 * Description: 随机获取验证码
	 * @param ran
	 * @return
	 * StringBuffer
	 */
	public static StringBuffer getVerifyCode() {
		Random ran = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < VERIFY_LENGTH; i++) {
			int index = ran.nextInt(CHARS.length);
			sb.append(CHARS[index]);
		}
		return sb;
	}
	
	/**
	 * 
	 * Title: getInviteCode
	 * Description: 随机获取邀请码
	 * @return
	 * StringBuffer
	 */
	public static String getInviteCode() {
		Random ran = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < INVITE_LEGTH - 1; i++) {
			int index = ran.nextInt(CHARS.length);
			sb.append(CHARS[index]);
		}
		Date date = new Date();
		String timeStr = date.getTime() + "";
		String last = timeStr.charAt(timeStr.length() - 1) + "";
		sb.append(last);
		return sb.toString();
	}

	/**
	 * 
	 * Title: createNewVerifyImage
	 * Description: 根据获取的颜色和字体，随机获取字符绘成图片
	 * @param request
	 * @param response
	 * void
	 */
	public static void createNewVerifyImage(HttpServletRequest request, HttpServletResponse response) {
		// 没有I、O
		// 设置响应的类型
		response.setContentType("image/jpeg");
		BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		// 获得一个画笔
		Random ran = new Random();
		Color[] color = get3RandColor();
		Graphics graphics = image.getGraphics();
		/*
		 * graphics.setColor(new Color(255, 255, 255)); graphics.fillRect(0, 0,
		 * IMAGE_WIDTH, IMAGE_HEIGHT);
		 */

		graphics.setColor(color[0]);// 设置画笔的颜色
		// 画一个长方形
		graphics.fillRoundRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, 10, 10);

		StringBuffer sbf = getVerifyCode();
		
		// 将正确验证码值存入session，并设置校验结果为 false
		request.getSession().setAttribute("verifyCode", sbf.toString());
		request.getSession().setAttribute("checkResult", false);
		// 重新设置画笔的颜色
		graphics.setColor(color[1]);
		for (int i = 0; i < 4; i++) {
			graphics.setFont(new Font(getRandFONTS(ran.nextInt(10)), ran.nextInt(3), 30 + ran.nextInt(10)));
			graphics.drawString(sbf.charAt(i) + "", i * 30 + 5, 30 + ran.nextInt(15));
		}
		// addSalt
		graphics.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		graphics.setColor(color[2]);
		for (int i = 0; i < 8; i++) {
			graphics.drawString(".", i * 15 + ran.nextInt(10), 15 + ran.nextInt(40));
			graphics.drawString(".", i * 15 + ran.nextInt(10), 15 + ran.nextInt(40));
			graphics.drawString(".", i * 15 + ran.nextInt(10), 15 + ran.nextInt(40));
		}
		// 将图片输出
		OutputStream out;
		try {
			out = response.getOutputStream();
			ImageIO.write(image, "jpeg", out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
