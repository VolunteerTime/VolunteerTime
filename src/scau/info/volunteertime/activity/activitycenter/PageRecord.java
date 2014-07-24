/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014��7��23��
 */
package scau.info.volunteertime.activity.activitycenter;

import java.util.List;

import scau.info.volunteertime.business.UserBO;
import scau.info.volunteertime.util.Pagination;
import scau.info.volunteertime.vo.ActivityData;

/**
 * @author ������
 *
 */
public class PageRecord extends Pagination<ActivityData> {
	
	private List<ActivityData> records; // ��ǰ��ҳ�ļ�¼����

	private int currentPageNumber=0; // ��ǰҳ��ҳ��

	private int amountOfRecorders; // ��¼����

	private int pageSize; // ÿҳ�ļ�¼��

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
