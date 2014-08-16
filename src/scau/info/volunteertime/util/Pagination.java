/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-21
 */
package scau.info.volunteertime.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 蔡超敏
 * 
 */
public class Pagination<T> {

	private List<T> records; // 当前分页的记录集合

	private int currentPageNumber; // 当前分页

	// private int amountOfRecorders; // 记录总数

	private int pageSize; // 每页的记录数

	public Pagination() {
		records = new ArrayList<T>();
	}

	/**
	 * 返回记录内容list
	 * 
	 * @return List
	 */
	public List<T> getRecords() {
		return records;
	}

	/**
	 * 设置记录内容list
	 * 
	 * @param records
	 */
	public void setRecords(List<T> records) {
		this.records = records;
	}

	/**
	 * 返回当前页的页码
	 * 
	 * @return int
	 */
	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	/**
	 * 设置当前页的页码
	 * 
	 * @param currentPageNumber
	 */
	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}

	/**
	 * 返回记录总数
	 * 
	 * @return int
	 */
	public int getAmountOfRecorders() {
		return records.size();
	}

	// /**
	// * 设置记录总数
	// *
	// * @param amountOfRecorders
	// */
	// public void setAmountOfRecorders(int amountOfRecorders) {
	// this.amountOfRecorders = amountOfRecorders;
	// }

	/**
	 * 返回每页的记录数
	 * 
	 * @return int
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 返回总页数
	 * 
	 * @return int
	 */
	public int getSumPage() {
		double sumPage = ((double) getAmountOfRecorders()) / pageSize;
		return (int) Math.ceil(sumPage);
	}

}
