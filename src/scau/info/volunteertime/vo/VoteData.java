package scau.info.volunteertime.vo;

/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014年8月14日
 */ 

import java.util.ArrayList;

/**
 * @author 蔡超敏
 *
 */
public class VoteData {
	
	private int id;	//投票编号
	private boolean single; //单选是true，复选是false
	private String	title;	//评论的主题
	private ArrayList<String> choice;	//投票选项
	private ArrayList<Integer>	votes;		//对应的投票结果
	private boolean checked=false;		//是否已经选择过了，从本地数据库获得,如果
	public boolean isChange=false;	//判断是否按下了按键，如果按下了那么会变成true，变成true画面会改变，但很快又会变成false
	
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
	
	public int getId()
	{
		return id;
	}
	public void setId(int num)
	{
		id=num;
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
