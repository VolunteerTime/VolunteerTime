package scau.info.volunteertime.util;

/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014年8月17日
 */

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import scau.info.volunteertime.vo.VolunteertimeData;

/**
 * @author 林锡鑫
 * @param <Data>
 * 
 */
public class SortedLinkList<T extends VolunteertimeData> implements
		Comparator<VolunteertimeData> {

	LinkedList<VolunteertimeData> sortedLinkList = new LinkedList<VolunteertimeData>();

	public LinkedList<VolunteertimeData> getList() {
		return sortedLinkList;
	}

	public boolean remove(VolunteertimeData object) {
		return sortedLinkList.remove(object);
	}

	public List<VolunteertimeData> sort() {
		Collections.sort(sortedLinkList, this);
		return sortedLinkList;
	}

	public List<VolunteertimeData> addAll(List<T> list) {

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

	public int nsort() {// 插入排序
		// TODO Auto-generated method stub
		int length = sortedLinkList.size(); // 数组长度
		int j; // 当前值的位置
		int i; // 指向j前的位置
		VolunteertimeData key; // 当前要进行插入排序的值
		// 从数组的第二个位置开始遍历值
		for (j = 1; j < length; j++) {
			key = sortedLinkList.get(j);
			;
			i = j - 1;

			// a[i]比当前值大时，a[i]后移一位,空出i的位置，好让下一次循环的值后移
			while ((i >= 0) && this.compare(sortedLinkList.get(i), key) > 0) {
				sortedLinkList.set(i + 1, sortedLinkList.get(i)); // 将a[i]值后移
				i--; // i前移
			}// 跳出循环(找到要插入的中间位置或已遍历到0下标)
			sortedLinkList.set(i + 1, key); // 将当前值插入
		}
		return 0;
	}

	/**
	 * @param i
	 */
	public void add(VolunteertimeData item) {
		// TODO Auto-generated method stub
		// System.out.println(item.getDate());
		sortedLinkList.remove(item.getId());
		sortedLinkList.add(item);

	}

	public VolunteertimeData get(int i) {
		return sortedLinkList.get(i);
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
