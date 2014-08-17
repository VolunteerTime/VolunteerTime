/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014年8月17日
 */
package scau.info.volunteertime.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 蔡超敏
 * @param <T>
 *
 */
public class SortedLinkList<T>   {
	
	LinkedList<T> sortedLinkList=new LinkedList<T>();
	Comparator<T> comparator;
	
	public void setSort(Comparator<T> com)
	{
		comparator=com;
	}
	
	public	List<T> sort()
	{
		Collections.sort (sortedLinkList, comparator); 
		return sortedLinkList;
	}
	
	public List<T> addAll(List<T> list)
	{	
	 
		
		sortedLinkList.addAll(list);
		
		nsort();
		return sortedLinkList;
	}
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	
	public int nsort() {
		// TODO Auto-generated method stub
		int length=sortedLinkList.size(); //数组长度  
        int j;               //当前值的位置  
        int i;               //指向j前的位置  
        T key;             //当前要进行插入排序的值  
        //从数组的第二个位置开始遍历值  
        for(j=1;j<length;j++){  
            key=sortedLinkList.get(j);;  
            i=j-1;  
           
            //a[i]比当前值大时，a[i]后移一位,空出i的位置，好让下一次循环的值后移  
            while((i>=0)&&comparator.compare(sortedLinkList.get(i),key)>0){  
            	 sortedLinkList.set(i+1, sortedLinkList.get(i)); //将a[i]值后移  
                i--;         //i前移  
            }//跳出循环(找到要插入的中间位置或已遍历到0下标)  
            sortedLinkList.set(i+1, key);   //将当前值插入  
        }  
		return 0;
	}

	/**
	 * @param i
	 */
	public void add(T item) {
		// TODO Auto-generated method stub
		sortedLinkList.add(item);
	}
	public T get(int i)
	{
		return sortedLinkList.get(i);
	}
	public int size()
	{
		return sortedLinkList.size();
	}
	

}
