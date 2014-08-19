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
public class Pagination<VolunteertimeData> {

	private List<VolunteertimeData> records; // ��ǰ��ҳ�ļ�¼����

	private int currentPageNumber; // ��ǰ��ҳ

	// private int amountOfRecorders; // ��¼����

	private int pageSize; // ÿҳ�ļ�¼��

	public Pagination() {
		records = new ArrayList<VolunteertimeData>();
	}

	public List<VolunteertimeData> getcurrentPageRecords() {
		if (currentPageNumber * pageSize > records.size()) {
			return records.subList((currentPageNumber - 1) * pageSize,
					records.size());
		}
		return records.subList((currentPageNumber - 1) * pageSize,
				currentPageNumber * pageSize);
	}

	public VolunteertimeData getLastData() {
		return records.get(records.size() - 1);
	}

	/**
	 * ���ؼ�¼����list
	 * 
	 * @return List
	 */
	public List<VolunteertimeData> getRecords() {
		return records;
	}

	/**
	 * ���ü�¼����list
	 * 
	 * @param records
	 */
	public void setRecords(List<VolunteertimeData> records) {
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
