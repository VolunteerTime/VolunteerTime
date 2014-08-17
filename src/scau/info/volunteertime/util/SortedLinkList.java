/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014��8��17��
 */
package scau.info.volunteertime.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author �̳���
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
		int length=sortedLinkList.size(); //���鳤��  
        int j;               //��ǰֵ��λ��  
        int i;               //ָ��jǰ��λ��  
        T key;             //��ǰҪ���в��������ֵ  
        //������ĵڶ���λ�ÿ�ʼ����ֵ  
        for(j=1;j<length;j++){  
            key=sortedLinkList.get(j);;  
            i=j-1;  
           
            //a[i]�ȵ�ǰֵ��ʱ��a[i]����һλ,�ճ�i��λ�ã�������һ��ѭ����ֵ����  
            while((i>=0)&&comparator.compare(sortedLinkList.get(i),key)>0){  
            	 sortedLinkList.set(i+1, sortedLinkList.get(i)); //��a[i]ֵ����  
                i--;         //iǰ��  
            }//����ѭ��(�ҵ�Ҫ������м�λ�û��ѱ�����0�±�)  
            sortedLinkList.set(i+1, key);   //����ǰֵ����  
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
