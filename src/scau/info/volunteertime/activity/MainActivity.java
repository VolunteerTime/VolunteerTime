/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-7-15
 */
package scau.info.volunteertime.activity;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.capricorn.ArcMenu;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.activitycenter.ActivityCenter;
import scau.info.volunteertime.activity.resultsexhibition.ResultsExhibitionFragment;
import scau.info.volunteertime.activity.votecenter.VoteCenter;
import android.app.LocalActivityManager;
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
 * @author �̳���
 * 
 */
public class MainActivity extends ActionBarActivity {

	public static int ScreenWidth;
	private static final int[] ITEM_DRAWABLES = {
			R.drawable.ab_bottom_solid_maintheme,
			R.drawable.ab_bottom_solid_maintheme,
			R.drawable.ab_bottom_solid_maintheme,
			R.drawable.ab_bottom_solid_maintheme,
			R.drawable.ab_bottom_solid_maintheme };

	private ViewPager viewFlow;
	private ListView listView;
	LocalActivityManager manager = null;
	PageIndicator mIndicator;
	/** Called when the activity is first created. */

	HashMap<Integer, Fragment> map = new HashMap<Integer, Fragment>();//放程序的每一个Fragment 
	HashMap<Integer, String> mapTitle = new HashMap<Integer, String>();//每一个Fragment的titile 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// System.out.println("!!!!!!!!!!");
		FragmentManager fragmentManager = getSupportFragmentManager();

		ArcMenu arcMenu2 = (ArcMenu) findViewById(R.id.arc_menu_2); //5个小点

		WindowManager manager = getWindowManager();
		  ScreenWidth = manager.getDefaultDisplay().getWidth();
		int height = manager.getDefaultDisplay().getHeight();
	 
		arcMenu2.setWindowSize(ScreenWidth, height);
		initArcMenu(arcMenu2, ITEM_DRAWABLES);

		map.put(0, new ResultsExhibitionFragment());
		map.put(1, new ActivityCenter());
		map.put(2, new VoteCenter());
		map.put(3, new ActivityCenter());
		map.put(4, new ActivityCenter());

		mapTitle.put(0, "作品展示");
		mapTitle.put(1, "活动中心");
		mapTitle.put(2, "投票中心");
		mapTitle.put(3, "活动中心");
		mapTitle.put(4, "活动中心");

		viewFlow = (ViewPager) findViewById(R.id.guidePages);
		MainAdapter adapter = new MainAdapter(fragmentManager, map, mapTitle);
		viewFlow.setAdapter(adapter);

		mIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(viewFlow);
		
		getOverflowMenu();//强制显示菜单的三个点

	}
    private void getOverflowMenu() {		//强制显示菜单的三个点
        try {
           ViewConfiguration config = ViewConfiguration.get(this);
           Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
           if(menuKeyField != null) {
               menuKeyField.setAccessible(true);
               menuKeyField.setBoolean(config, false);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
    
	private void initArcMenu(ArcMenu menu, int[] itemDrawables) {
		menu.scrollBy(0, -100);
		final int itemCount = itemDrawables.length;
		for (int i = 0; i < itemCount; i++) {
			ImageView item = new ImageView(this);
			item.setImageResource(itemDrawables[i]);

			final int position = i;
			menu.addItem(item, new OnClickListener() { //每个小菜单项的监听器

						@Override
						public void onClick(View v) {
							viewFlow.setCurrentItem(position);
							Toast.makeText(MainActivity.this,
									"position:" + position, Toast.LENGTH_SHORT)
									.show();
						}
					});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
	 
		MenuItemCompat.setShowAsAction(menu.add("个人信息") 
                .setIcon(android.R.drawable.ic_menu_rotate), MenuItemCompat.SHOW_AS_ACTION_NEVER);
		MenuItemCompat.setShowAsAction(menu.add("No.41") 
                .setIcon(android.R.drawable.ic_menu_rotate), MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

	        return true; 
	         
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
