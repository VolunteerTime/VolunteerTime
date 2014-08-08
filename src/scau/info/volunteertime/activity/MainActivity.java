/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-7-15
 */
package scau.info.volunteertime.activity;
 
import java.util.HashMap;
 


import com.capricorn.ArcMenu; 
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.activitycenter.ActivityCenter;
import android.app.LocalActivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

	
	
	private static final int[] ITEM_DRAWABLES = { R.drawable.ab_bottom_solid_maintheme, R.drawable.ab_bottom_solid_maintheme,
		R.drawable.ab_bottom_solid_maintheme,  R.drawable.ab_bottom_solid_maintheme, R.drawable.ab_bottom_solid_maintheme };

	
	
	private ViewPager viewFlow;
	private ListView listView;
    LocalActivityManager manager = null;
    PageIndicator mIndicator;
	/** Called when the activity is first created. */
    
	HashMap<Integer,Fragment> map=new HashMap<Integer,Fragment>();//�ų����ÿһ��Fragment 
	HashMap<Integer,String> mapTitle=new HashMap<Integer,String>();//ÿһ��Fragment��titile 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_main);
     //   System.out.println("!!!!!!!!!!"); 
        FragmentManager fragmentManager = getSupportFragmentManager();
  
        
        ArcMenu arcMenu2 = (ArcMenu) findViewById(R.id.arc_menu_2);	//5��С��
        
        
        WindowManager manager = getWindowManager();
        int width = manager.getDefaultDisplay().getWidth();
        int height = manager.getDefaultDisplay().getHeight();
        arcMenu2.setWindowSize(width,height);
        initArcMenu(arcMenu2, ITEM_DRAWABLES);
        
        map.put(0, new ActivityCenter());
        map.put(1, new ActivityCenter());
        map.put(2, new ActivityCenter());
        map.put(3, new ActivityCenter());
        map.put(4, new ActivityCenter());
         
	
        mapTitle.put(0, "�����");
        mapTitle.put(1, "�����");
        mapTitle.put(2, "�����");
        mapTitle.put(3, "�����");
        mapTitle.put(4, "�����");
        
	 	viewFlow = (ViewPager) findViewById(R.id.guidePages); 
        MainAdapter adapter = new MainAdapter(fragmentManager,map,mapTitle);
        viewFlow.setAdapter(adapter);  
        
         
        mIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(viewFlow);
         
		 
    }
    private void initArcMenu(ArcMenu menu, int[] itemDrawables) {
    	menu.scrollBy(0, -100);
        final int itemCount = itemDrawables.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(itemDrawables[i]);

            final int position = i;
            menu.addItem(item, new OnClickListener() {		//ÿ��С�˵���ļ�����

                @Override
                public void onClick(View v) {
                	viewFlow.setCurrentItem(position);
                    Toast.makeText(MainActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
