/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014年7月23日
 */
package scau.info.volunteertime.activity.activitycenter;

import java.util.List;

import scau.info.volunteertime.business.UserBO;
import scau.info.volunteertime.util.Pagination;
import scau.info.volunteertime.vo.ActivityData;

/**
 * @author 林锡鑫
 *
 */
public class PageRecord extends Pagination<ActivityData> {
	
	private List<ActivityData> records; // 当前分页的记录集合

	private int currentPageNumber=0; // 当前页的页码

	private int amountOfRecorders; // 记录总数

	private int pageSize; // 每页的记录数

	public List<ActivityData> getRecords() {
		
		UserBO userBo=new UserBO();
		
		currentPageNumber++;
		//if(currentPageNumber==amountOfRecorders) return null;
		return userBo.getActivityRecord(currentPageNumber-1);
	}

	public void setRecords(List<ActivityData> records) {
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
