/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-8-17
 */

package scau.info.volunteertime.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import scau.info.volunteertime.vo.VolunteertimeData;

/**
 * @author ������
 * @param <Data>
 * 
 */
public class SortedLinkList<T extends VolunteertimeData> implements
		Comparator<VolunteertimeData> {

	LinkedList<T> sortedLinkList = new LinkedList<T>();

	/**
	 * ����ȫ�µ�list
	 * 
	 * @return LinkedList<T>
	 */
	public LinkedList<T> getList() {
		LinkedList<T> newLinkList = new LinkedList<T>();

		for (T t : sortedLinkList) {
			newLinkList.add(t);
		}
		return newLinkList;
	}

	public LinkedList<T> getSortedLinkList() {
		return sortedLinkList;
	}

	public boolean remove(VolunteertimeData object) {
		return sortedLinkList.remove(object);
	}

	public List<T> sort() {
		Collections.sort(sortedLinkList, this);
		return sortedLinkList;
	}

	public List<T> addAll(List<T> list) {

		for (VolunteertimeData i : list) {
			sortedLinkList.remove(i);
		}

		sortedLinkList.addAll(list);

		nsort();
		return sortedLinkList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */

	public int nsort() {// ��������
		// TODO Auto-generated method stub
		int length = sortedLinkList.size(); // ���鳤��
		int j; // ��ǰֵ��λ��
		int i; // ָ��jǰ��λ��
		T key; // ��ǰҪ���в��������ֵ
		// ������ĵڶ���λ�ÿ�ʼ����ֵ
		for (j = 1; j < length; j++) {
			key = sortedLinkList.get(j);
			;
			i = j - 1;

			// a[i]�ȵ�ǰֵ��ʱ��a[i]����һλ,�ճ�i��λ�ã�������һ��ѭ����ֵ����
			while ((i >= 0) && this.compare(sortedLinkList.get(i), key) > 0) {
				sortedLinkList.set(i + 1, sortedLinkList.get(i)); // ��a[i]ֵ����
				i--; // iǰ��
			}// ����ѭ��(�ҵ�Ҫ������м�λ�û��ѱ�����0�±�)
			sortedLinkList.set(i + 1, key); // ����ǰֵ����
		}
		return 0;
	}

	/**
	 * @param i
	 */
	public void add(T item) {
		// TODO Auto-generated method stub
		// System.out.println(item.getDate());
		sortedLinkList.remove(item.getId());
		sortedLinkList.add(item);

	}

	public T get(int i) {
		return (T) sortedLinkList.get(i);
	}

	public int size() {
		return sortedLinkList.size();
	}

	public boolean isEmpty() {
		return sortedLinkList.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(VolunteertimeData lhs, VolunteertimeData rhs) {
		// TODO Auto-generated method stub
		if (lhs.getDate() < rhs.getDate())
			return 1;
		else if (lhs.getDate() > rhs.getDate())
			return -1;
		else
			return 0;
	}

}
