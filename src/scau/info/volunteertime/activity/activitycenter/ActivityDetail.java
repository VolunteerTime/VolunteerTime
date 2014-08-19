/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-31
 */
package scau.info.volunteertime.activity.activitycenter;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.ListUtils;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import scau.info.volunteertime.R;  
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author 林锡鑫
 *
 */
public class ActivityDetail extends Dialog implements OnPageChangeListener{

	/**
	 * @param context
	 */
	
	public ActivityDetail(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see android.app.Dialog#show()
	 */
	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
	}
 
    private AutoScrollViewPager viewPager;
    ArrayList<View> listViews;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_activity_center_detail);
        viewPager = (AutoScrollViewPager)findViewById(R.id.view_pager);
         listViews = new ArrayList<View>();
        for(int i=0;i<5;i++)
        {ImageView imageView=new ImageView(getContext());
        imageView.setBackgroundResource(R.drawable.ab_bottom_solid_maintheme);
        listViews.add(imageView);
        }
        viewPager.startAutoScroll();
        viewPager.setInterval(2000);		//����ͼƬ�Զ���������
         System.out.println("����");
        viewPager.setAdapter(new imagePageAdapter());
        
        
      
    }
    
    class imagePageAdapter extends PagerAdapter{

    	/* (non-Javadoc)
    	 * @see android.support.v4.view.PagerAdapter#destroyItem(android.view.View, int, java.lang.Object)
    	 */
    	@Override
    	public void destroyItem(View container, int position, Object object) {
    		// TODO Auto-generated method stub
    		 ((ViewPager) container).removeView(listViews.get(position)); 
    	}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listViews.size();
		} 
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		} 
		public Object instantiateItem(View arg0, int arg1) { 
            // TODO Auto-generated method stub 
            ((ViewPager) arg0).addView(listViews.get(arg1)); 
            return listViews.get(arg1); 
        } 
    	
    }

	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
	 */
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
