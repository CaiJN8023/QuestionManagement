package com.questionManagement.constant;

import java.util.List;

/**
 * 
 * @author CJN
 * @date 2019年3月9日
 * @param <T>
 * Title: MyPage 
 * Description: 自定义Page类，封装实体类List和总页数，总记录数
 */
public class MyPage<T> {

	private List<T> list;
	
	private int totalPages;
	
	private long totalElements;
	
	public static int PAGELIMIT = 5;

	public MyPage() {
		super();
	}

	public MyPage(List<T> list, int totalPages, long totalElements) {
		super();
		this.list = list;
		this.totalPages = totalPages;
		this.totalElements = totalElements;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	@Override
	public String toString() {
		return "MyPage [list=" + list + ", totalPages=" + totalPages + ", totalElements=" + totalElements + "]";
	}
	
	
}
