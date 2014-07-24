/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014年7月22日
 */
package scau.info.volunteertime.vo;

import java.util.ArrayList;

/**
 * @author 林锡鑫
 *
 */
public class ActivityData {
	
	private String time,content,name,location,id;
	private boolean isPartIn;
	private ArrayList<String >comment=new ArrayList<String>();
	
	public void addComment(String item)
	{
		comment.add(item);
	}
	
	public ArrayList<String> getComment()
	{
		return comment;
	}
	
	public boolean getIsPartIn()
	{
		return isPartIn;
	}
	public void setIsPartIn(boolean a)
	{
		isPartIn=a;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id=id;
	}
	
	public ActivityData(String name,String content,String time,String location)
	{
		this.name=name;
		this.content=content;
		this.time=time;
		this.location=location;
	}
	public String getLocation()
	{
		return location;
	}
	public String getContent()
	{
		return content;
	}
	public String getName()
	{
		return name;
	}
	public String getTime()
	{
		return time;
	}

	
	
	
	public void setLocation(String a)
	{
		 location=a;
	}
	public void setContent(String a)
	{
		 content=a;
	}
	public void setName(String a)
	{
		name=a;
	}
	public void setTime(String a)
	{
		time=a;
	}
	
}
