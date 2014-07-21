/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-21
 */
package scau.info.volunteertime.util;

import java.util.List;

/**
 * @author 蔡超敏
 * 
 */
public class Pagination<T> {

	private List<T> records; // 当前分页的记录集合

	private int currentPageNumber; // 当前页的页码

	private int amountOfRecorders; // 记录总数

	private int pageSize; // 每页的记录数

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}

	public int getAmountOfRecorders() {
		return amountOfRecorders;
	}

	public void setAmountOfRecorders(int amountOfRecorders) {
		this.amountOfRecorders = amountOfRecorders;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getSumPage() {
		double sumPage = ((double) amountOfRecorders) / pageSize;
		return (int) Math.ceil(sumPage);
	}

}
