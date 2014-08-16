/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-7-21
 */
package scau.info.volunteertime.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author �̳���
 * 
 */
public class Pagination<T> {

	private List<T> records; // ��ǰ��ҳ�ļ�¼����

	private int currentPageNumber; // ��ǰ��ҳ

	// private int amountOfRecorders; // ��¼����

	private int pageSize; // ÿҳ�ļ�¼��

	public Pagination() {
		records = new ArrayList<T>();
	}

	/**
	 * ���ؼ�¼����list
	 * 
	 * @return List
	 */
	public List<T> getRecords() {
		return records;
	}

	/**
	 * ���ü�¼����list
	 * 
	 * @param records
	 */
	public void setRecords(List<T> records) {
		this.records = records;
	}

	/**
	 * ���ص�ǰҳ��ҳ��
	 * 
	 * @return int
	 */
	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	/**
	 * ���õ�ǰҳ��ҳ��
	 * 
	 * @param currentPageNumber
	 */
	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}

	/**
	 * ���ؼ�¼����
	 * 
	 * @return int
	 */
	public int getAmountOfRecorders() {
		return records.size();
	}

	// /**
	// * ���ü�¼����
	// *
	// * @param amountOfRecorders
	// */
	// public void setAmountOfRecorders(int amountOfRecorders) {
	// this.amountOfRecorders = amountOfRecorders;
	// }

	/**
	 * ����ÿҳ�ļ�¼��
	 * 
	 * @return int
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * ����ÿҳ�ļ�¼��
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * ������ҳ��
	 * 
	 * @return int
	 */
	public int getSumPage() {
		double sumPage = ((double) getAmountOfRecorders()) / pageSize;
		return (int) Math.ceil(sumPage);
	}

}
