/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.activity;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.capricorn.ArcMenu;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.activitycenter.ActivityCenter;
import scau.info.volunteertime.activity.personalinfo.PersonalInfo;
import scau.info.volunteertime.activity.resultsexhibition.ResultsExhibitionFragment; 
import scau.info.volunteertime.activity.votecenter.VoteCenter;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author 蔡超敏
 * 
 */
public class MainActivity extends BaseActionBarActivity {

	public static int ScreenWidth;
	MainActivityFragment mainfragment ;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FragmentManager fragmentManager = getSupportFragmentManager(); 
		
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); 
		
		mainfragment = new MainActivityFragment(); 
		
	 fragmentTransaction.replace(R.id.mainActivityFragmentLayout, mainfragment).commit(); 

	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.

		MenuItemCompat.setShowAsAction(
				menu.add("个人信息").setIcon(android.R.drawable.ic_menu_rotate),
				MenuItemCompat.SHOW_AS_ACTION_NEVER);
		MenuItemCompat.setShowAsAction(
				menu.add("No.41").setIcon(android.R.drawable.ic_menu_rotate),
				MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		String name = (String) item.getTitle();
		if (name.equals("个人信息")) {
			 
		 
			FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction(); 
			fragmentTransaction.replace(R.id.mainActivityFragmentLayout, new PersonalInfo()).addToBackStack(null).commit(); 
 
			return true;
		}
		
		
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */

}
