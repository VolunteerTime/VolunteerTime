/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-7-15
 */
package scau.info.volunteertime.activity.activitycenter;

import java.util.ArrayList;
import java.util.List; 

import com.nhaarman.supertooltips.ToolTipRelativeLayout;

import cn.trinea.android.common.util.ListUtils;
import cn.trinea.android.common.view.DropDownListView;
import scau.info.volunteertime.R;
import scau.info.volunteertime.vo.ActivityData; 
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog; 
import android.os.Bundle;
import android.os.Handler;
import android.os.Message; 
import android.support.v4.app.Fragment;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener; 
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils; 
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author �̳���
 *
 */
public class ActivityCenter extends Fragment  {

	   
	 DropDownListView listview;
	View container;
	ActivityAdapter adapter;
	  ToolTipRelativeLayout mToolTipFrameLayout;
	PageRecord getData=new PageRecord();	//������ȡ�������������listView
	List<ActivityData> list=new ArrayList<ActivityData>();
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
}
	
	
/* (non-Javadoc)
 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
 */
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	View view=inflater.inflate(R.layout.activity_activity_center, container,false);
	 
	  
 
	 listview=(DropDownListView)view.findViewById(R.id.listView);
	 
 // View menu=findViewById(R.id.menu);
	// menu.startAnimation(animation);
        mToolTipFrameLayout = (ToolTipRelativeLayout)view. findViewById(R.id.activity_main_tooltipframelayout); 
         
	
	 ActivityData a1=new ActivityData("xin","����21��ȥ�Է���������ˣ��Һ�һ������㣬���ˣ�","2014-07-22","eee");
	 ActivityData a2=new ActivityData("name","���Ǹ�ʧ����ȥһ�Ҳ����Է����պú�����һ���������������ҵģ��ò����ȡ�","2014-07-22","eee");
	 ActivityData a3=new ActivityData("name","���ҿ������ǲ��������ɣ�������Ϊ�����˻���ͷ���ҿ�������Ǯ��Ǯ�����ߣ�����׷���������ֻ�û׷�ϱ�����","2014-07-22","eee");
	 ActivityData a4=new ActivityData("name","һ����Ů�ĶԻ����У�Ϊʲô�㿴���Ұ���Ů����Ϊ�㳤��˧���У�˧�ֲ��ܵ����ԡ�Ů�����ǲ�˧�Ļ������Ż�Բ��·���","2014-07-22","eee");
	 ActivityData a5=new ActivityData("name","1.У����Ӣ����ʦһ��ȥ����ĳ��ѧ����,У�������ý���,Ӣ����ʦ������","2009-9-26","eee");
	 ActivityData a6=new ActivityData("name","�ʼǱ�˵:�Ҹ���˵��Ц���¡��豭˵:�ð��ð����ʼǱ�˵:��ǰ�и��豭,���ӽ�ˮ�ˡ��豭:","5Сʱǰ","eee");
	 ActivityData a7=new ActivityData("xin","����21��ȥ�Է���������ˣ��Һ�һ������㣬���ˣ�","2014-07-22","eee");
	 ActivityData a8=new ActivityData("name","���Ǹ�ʧ����ȥһ�Ҳ����Է����պú�����һ���������������ҵģ��ò����ȡ�","2014-07-22","eee");
	 ActivityData a9=new ActivityData("name","���ҿ������ǲ��������ɣ�������Ϊ�����˻���ͷ���ҿ�������Ǯ��Ǯ�����ߣ�����׷���������ֻ�û׷�ϱ�����","2014-07-22","eee");
	 ActivityData a10=new ActivityData("name","һ����Ů�ĶԻ����У�Ϊʲô�㿴���Ұ���Ů����Ϊ�㳤��˧���У�˧�ֲ��ܵ����ԡ�Ů�����ǲ�˧�Ļ������Ż�Բ��·���","2014-07-22","eee");
	 ActivityData a11=new ActivityData("name","1.У����Ӣ����ʦһ��ȥ����ĳ��ѧ����,У�������ý���,Ӣ����ʦ������","2009-9-26","eee");
	 ActivityData a12=new ActivityData("name","�ʼǱ�˵:�Ҹ���˵��Ц���¡��豭˵:�ð��ð����ʼǱ�˵:��ǰ�и��豭,���ӽ�ˮ�ˡ��豭:","5Сʱǰ","eee");
	 ActivityData a13=new ActivityData("xin","����21��ȥ�Է���������ˣ��Һ�һ������㣬���ˣ�","2014-07-22","eee");
	 ActivityData a14=new ActivityData("name","���Ǹ�ʧ����ȥһ�Ҳ����Է����պú�����һ���������������ҵģ��ò����ȡ�","2014-07-22","eee");
	 ActivityData a15=new ActivityData("name","���ҿ������ǲ��������ɣ�������Ϊ�����˻���ͷ���ҿ�������Ǯ��Ǯ�����ߣ�����׷���������ֻ�û׷�ϱ�����","2014-07-22","eee");
	 ActivityData a16=new ActivityData("name","һ����Ů�ĶԻ����У�Ϊʲô�㿴���Ұ���Ů����Ϊ�㳤��˧���У�˧�ֲ��ܵ����ԡ�Ů�����ǲ�˧�Ļ������Ż�Բ��·���","2014-07-22","eee");
	 ActivityData a17=new ActivityData("name","1.У����Ӣ����ʦһ��ȥ����ĳ��ѧ����,У�������ý���,Ӣ����ʦ������","2009-9-26","eee");
	 ActivityData a18=new ActivityData("name","�ʼǱ�˵:�Ҹ���˵��Ц���¡��豭˵:�ð��ð����ʼǱ�˵:��ǰ�и��豭,���ӽ�ˮ�ˡ��豭:","5Сʱǰ","eee");
	 
		list.add(a1); 
		list.add(a2); 
		list.add(a3); 
		list.add(a4); 
		list.add(a5); 
		list.add(a6); 		
		list.add(a7); 
		list.add(a8); 
		list.add(a9); 
		list.add(a10); 
		list.add(a11); 
		list.add(a12); 		list.add(a18); 
		list.add(a13); 
		list.add(a14); 
		list.add(a15); 
		list.add(a16); 
		list.add(a17); 
	
	adapter=new ActivityAdapter(getActivity(),list,handler,mToolTipFrameLayout );
	
	listview.setOnItemClickListener(new OnItemClickListener() {

		@Override		//�򿪶Ի�����ʾ�����
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			
			LayoutInflater inflaterDl = LayoutInflater.from(getActivity());
	         View layout =inflaterDl.inflate(R.layout.activity_activity_center_detail, null );
	        
	         //�Ի���
	         final ActivityDetail dialog = new ActivityDetail(getActivity());
	         TextView text=(TextView)layout.findViewById(R.id.title);
	         
	         dialog.show(); 
	         
	          
		}
	});
	listview.setAdapter(adapter);  
	listview.setOnDragListener(new OnDragListener() {
		
		@Override
		public boolean onDrag(View v, DragEvent event) {
			// TODO Auto-generated method stub
			listview.onDropDownComplete("����");
			return false;
		}
	});
	final Handler adapterHandler=adapter.getHandler();
	
	listview.setOnScrollListener(new OnScrollListener() {
		
		 
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			
		}
		int now=0;
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
		 
			// TODO Auto-generated method stub
			if(now!=firstVisibleItem)
			{Message msg=adapterHandler.obtainMessage();
			msg.arg2=3;
			msg.arg1=firstVisibleItem;
			msg.sendToTarget();
			now=firstVisibleItem;
			}
		}
	});
	listview.setOnBottomListener(new  OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Message msg=handler.obtainMessage();
			
			List<ActivityData> data=getData.getRecords();
			if(data==null) msg.arg1=-1;
			else list.addAll(data);
			
			msg.arg2=2;
			msg.sendToTarget();
		}
	});

	return view;
}
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getActivity().getMenuInflater().inflate(R.menu.activity_center, menu);
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
 
	/* (non-Javadoc)
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
	 */
	 

	public Handler getHandler(){return handler;}
	Handler handler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			if(msg.arg2==3)  //����յ�3��ô����Ҫ�����۴���
			{ 
				ActivityData data=list.get(msg.arg1); 
				 
				 
			}
			else if(msg.arg2==2)		//����յ�arg2 �յ�2�Ǿ���Ҫ���ظ�������
			{	
				if(msg.arg1==-1)  //���msg.arg1�յ�-1��ô����û�и���������
					{Toast.makeText(getActivity(), "û�и���������", 1).show();
					listview.setOnBottomStyle(false);
					}
				else 
				{adapter.notifyDataSetChanged();
				listview.onBottomComplete();}
			} 
		};
	};

  

}
