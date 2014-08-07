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
	
	
	HashMap<Integer,Fragment> map=new HashMap<Integer,Fragment>();
	HashMap<Integer,String> mapTitle=new HashMap<Integer,String>();
	public MainAdapter(FragmentManager fm,HashMap<Integer,Fragment> map,HashMap<Integer,String> mapTitle) {
		super(fm);
		this.map=map;
		this.mapTitle=mapTitle;
		// TODO Auto-generated constructor stub
	} 
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return map.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return map.size();
	} 
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return mapTitle.get(position).toString();
		//return super.getPageTitle(position);
	}
	
 

}
