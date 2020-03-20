package com.questionManagement.util;

public class StringUtils {

	/**
	 * 
	 * Title: min
	 * Description: 比较三个值的最小值
	 * @param vals
	 * @return
	 * int
	 */
	public static int min(int ...vals) {
		int min = Integer.MAX_VALUE;
		for(int val : vals) {
			if(min > val)
				min = val;
		}
		
		return min;
	}
	
	/**
	 * 
	 * Title: similarity
	 * Description: 计算两个字符串的相似度
	 * 				https://blog.csdn.net/xcxy2015/article/details/77164126
	 * @param str1
	 * @param str2
	 * @return
	 * float
	 */
	public static float similarity(String str1, String str2) {
		int len1 = str1.length();
		int len2 = str2.length();
		int[][] dif = new int[len1 + 1][len2 + 1];
		dif[0][0] = 0;
		for(int i = 1; i <= len1; i++) {
			dif[i][0] = i;
		}
		for(int i = 1; i <= len2; i++) {
			dif[0][i] = i;
		}
		
		int temp = 0;
		for(int i = 1; i <= len1; i++) {
			for(int j = 1; j <= len2; j++) {
				if(str1.charAt(i - 1) == str2.charAt(j - 1)) {
					temp = 0;
				}else {
					temp = 1;
				}
				dif[i][j] = min((dif[i - 1][j - 1] + temp), dif[i - 1][j] + 1, dif[i][j - 1] + 1);
			}
		}
		int step = dif[len1][len2];
		float similarity = 1 - (float) step / Math.max(len1, len2);
		
		return similarity;
	}
	
	/**
	 * 
	 * Title: join
	 * Description: 将字符串数组转化为以逗号分隔开的字符串，用于解析多选题答案
	 * @param arr
	 * @return
	 * String
	 */
	public static String join(String[] arr, String separator) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < arr.length; i++) {
			if(i == arr.length - 1) {
				sb.append(arr[i]);
			}else {
				sb.append(arr[i] + separator);
			}
		}
		
		return sb.toString();
	}
	
}
