/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-8-18
 */
package scau.info.volunteertime.activity;

import java.util.ArrayList;
import java.util.HashMap;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.activitycenter.ActivityCenter;
import scau.info.volunteertime.activity.resultsexhibition.ResultsExhibitionFragment;
import scau.info.volunteertime.activity.votecenter.VoteCenter;

import com.capricorn.ArcMenu;
import com.viewpagerindicator.PageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import android.app.LocalActivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author 林锡鑫
 *
 */
public class MainActivityFragment extends Fragment{
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	
	private static final int[] ITEM_DRAWABLES = {
		R.drawable.ab_bottom_solid_maintheme,
		R.drawable.ab_bottom_solid_maintheme,
		R.drawable.ab_bottom_solid_maintheme,
		R.drawable.ab_bottom_solid_maintheme,
		R.drawable.ab_bottom_solid_maintheme };
		View mView;
		private ViewPager viewFlow;
		private ListView listView;
		LocalActivityManager manager = null;
		PageIndicator mIndicator;
		
		private ArrayList<Fragment> fragments=new ArrayList<Fragment>();  //�ų����ÿһ��Fragment 
		
		private ArrayList<String> title=new ArrayList<String>(); //ÿһ��Fragment��titile 

		/* (non-Javadoc)
		 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
		 */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
		
			System.out.println("����б������𡷡���������������������������");
			super.onCreate(savedInstanceState);
		}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		
		if(mView != null){
            ViewGroup parent = (ViewGroup) mView.getParent();  
            if (parent != null) {
            	parent.removeView(mView);  
            }   
            return mView;
		}
		
 
        mView=inflater.inflate(R.layout.fragment_main, container, false);
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

		ArcMenu arcMenu2 = (ArcMenu) mView.findViewById(R.id.arc_menu_2); //5��С��

		WindowManager manager = getActivity().getWindowManager();
		MainActivity.ScreenWidth = manager.getDefaultDisplay().getWidth();
		int height = manager.getDefaultDisplay().getHeight();

		arcMenu2.setWindowSize(MainActivity.ScreenWidth, height);
		initArcMenu(arcMenu2, ITEM_DRAWABLES);
		
		 fragments.clear();
		 fragments.add(new ResultsExhibitionFragment());
		 fragments.add(new ActivityCenter());
		 fragments.add(new VoteCenter());
	//	map.put(3, new ActivityCenter());
	//	map.put(4, new ActivityCenter());

		title.add( "��Ʒչʾ");
		title.add( "�����");
		title.add( "ͶƱ����");
		title.add("�����");
		title.add( "�����");
		 System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11111!!!!!");
		viewFlow = (ViewPager)mView.findViewById(R.id.guidePages);
		MainAdapter adapter = new MainAdapter(fragmentManager, fragments, title);
		adapter.notifyDataSetChanged();
		viewFlow.setAdapter(adapter);
		
		
		
		
		mIndicator = (TitlePageIndicator) mView.findViewById(R.id.indicator);
		mIndicator.setViewPager(viewFlow);
		return mView;
	}
	private void initArcMenu(ArcMenu menu, int[] itemDrawables) {
		menu.scrollBy(0, -100);
		final int itemCount = itemDrawables.length;
		for (int i = 0; i < itemCount; i++) {
			ImageView item = new ImageView(getActivity());
			item.setImageResource(itemDrawables[i]);

			final int position = i;
			menu.addItem(item, new OnClickListener() { //ÿ��С�˵���ļ�����

						@Override
						public void onClick(View v) {
							viewFlow.setCurrentItem(position);
							Toast.makeText(getActivity(),
									"position:" + position, Toast.LENGTH_SHORT)
									.show();
						}
					});
		}
	}

}
