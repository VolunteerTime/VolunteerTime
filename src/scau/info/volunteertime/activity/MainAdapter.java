/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-8-19
 */
package scau.info.volunteertime.activity;
 

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author 林锡鑫
 *
 */
public class MainAdapter extends FragmentPagerAdapter  {
	
	private ArrayList<Fragment> fragments;  
	
	private ArrayList<String> title ; 
	
	public MainAdapter(FragmentManager fm, ArrayList<Fragment> fragments,ArrayList<String>  title) {
		super(fm);
		this.fragments=fragments;
	 
		this.title=title;
		// TODO Auto-generated constructor stub
	} 
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		System.out.println("getitem"+arg0);
		return fragments.get(arg0);
	}
 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub 
		return fragments.size();
	} 
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return title.get(position).toString();
		//return super.getPageTitle(position);
	}
	
 

}
