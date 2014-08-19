package scau.info.volunteertime.activity;
 

import java.util.ArrayList;
import java.util.HashMap;
 


import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
