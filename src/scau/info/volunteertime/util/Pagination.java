/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-7-21
 */
package scau.info.volunteertime.util;

import java.util.List;

/**
 * @author �̳���
 * 
 */
public class Pagination<T> {

	private List<T> records; // ��ǰ��ҳ�ļ�¼����

	private int currentPageNumber; // ��ǰҳ��ҳ��

	private int amountOfRecorders; // ��¼����

	private int pageSize; // ÿҳ�ļ�¼��

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
