/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014��8��14��
 */
package scau.info.volunteertime.vo;

import java.util.ArrayList;

/**
 * @author �̳���
 *
 */
public class VoteData {
	
	private int number;	//ͶƱ���
	private boolean single; //��ѡ��true����ѡ��false
	private String	title;	//���۵�����
	private ArrayList<String> choice;	//ͶƱѡ��
	private ArrayList<Integer>	votes;		//��Ӧ��ͶƱ���
	private boolean checked=false;		//�Ƿ��Ѿ�ѡ����ˣ��ӱ������ݿ���,���
	public boolean isChange=false;	//�ж��Ƿ����˰����������������ô����true�����true�����ı䣬���ܿ��ֻ���false
	
	public void setChecked(boolean checked)
	{
		this.checked=checked;
	}
	public boolean getChecked()
	{
		return checked;
	}
	
	public void changeChecked()
	{
		checked=!checked;
	}
	
	public int getNumber()
	{
		return number;
	}
	public void setNumber(int num)
	{
		number=num;
	}
	
	
	
	public boolean getSingle()
	{
		return single;
	}
	public void setSingle(boolean  t)
	{
		single=t;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String t)
	{
		title=t;
	}
	public ArrayList<String> getChoice()
	{
		return choice;
	}
	public void setChoice(ArrayList<String> c)
	{
		choice=c;
	}
	public ArrayList<Integer> getVotes()
	{
		return votes;
	}
	public void setVotes(ArrayList<Integer> v)
	{
		votes=v;
	}
	

}
