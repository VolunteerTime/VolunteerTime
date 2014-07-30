/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.activity.activitycenter;

import java.util.ArrayList;
import java.util.List;
 
 










import cn.trinea.android.common.view.DropDownListView;
import scau.info.volunteertime.R;
import scau.info.volunteertime.vo.ActivityData;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.AndroidCharacter;
import android.text.Layout;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 蔡超敏
 *
 */
public class ActivityCenter extends Activity  {

	   
	 DropDownListView listview;
	View container;
	ActivityAdapter adapter;
	PageRecord getData=new PageRecord();	//用来获取更多数据来填充listView
	List<ActivityData> list=new ArrayList<ActivityData>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_center);
		 
		container=findViewById(R.id.container);
	 
		 listview=(DropDownListView)findViewById(R.id.listView);
		
	 	 Animation animation=AnimationUtils.loadAnimation(ActivityCenter.this, R.anim.set);
	 // View menu=findViewById(R.id.menu);
 	// menu.startAnimation(animation);
		
		
		 ActivityData a1=new ActivityData("xin","本人21，去吃饭，吃完结账，我喊一声，大姐，结账！","2014-07-22","eee");
		 ActivityData a2=new ActivityData("name","哥们刚失恋，去一家餐厅吃饭，刚好后面有一对情吕在唧唧我我的，好不亲热。","2014-07-22","eee");
		 ActivityData a3=new ActivityData("name","“我靠，哥们不用这样吧！”他以为我怕了还点头，我快速拿起钱跟钱包就走，尼玛追了我两条街还没追上被我跑","2014-07-22","eee");
		 ActivityData a4=new ActivityData("name","一对男女的对话：男：为什么你看上我啊？女：因为你长得帅埃男：帅又不能当饭吃。女：但是不帅的话，对着会吃不下饭。","2014-07-22","eee");
		 ActivityData a5=new ActivityData("name","1.校长和英语老师一起去法国某中学访问,校长在礼堂讲话,英语老师做翻译","2009-9-26","eee");
		 ActivityData a6=new ActivityData("name","笔记本说:我给你说个笑话呗。茶杯说:好啊好啊。笔记本说:从前有个茶杯,脑子进水了。茶杯:","5小时前","eee");
		 ActivityData a7=new ActivityData("xin","本人21，去吃饭，吃完结账，我喊一声，大姐，结账！","2014-07-22","eee");
		 ActivityData a8=new ActivityData("name","哥们刚失恋，去一家餐厅吃饭，刚好后面有一对情吕在唧唧我我的，好不亲热。","2014-07-22","eee");
		 ActivityData a9=new ActivityData("name","“我靠，哥们不用这样吧！”他以为我怕了还点头，我快速拿起钱跟钱包就走，尼玛追了我两条街还没追上被我跑","2014-07-22","eee");
		 ActivityData a10=new ActivityData("name","一对男女的对话：男：为什么你看上我啊？女：因为你长得帅埃男：帅又不能当饭吃。女：但是不帅的话，对着会吃不下饭。","2014-07-22","eee");
		 ActivityData a11=new ActivityData("name","1.校长和英语老师一起去法国某中学访问,校长在礼堂讲话,英语老师做翻译","2009-9-26","eee");
		 ActivityData a12=new ActivityData("name","笔记本说:我给你说个笑话呗。茶杯说:好啊好啊。笔记本说:从前有个茶杯,脑子进水了。茶杯:","5小时前","eee");
		 ActivityData a13=new ActivityData("xin","本人21，去吃饭，吃完结账，我喊一声，大姐，结账！","2014-07-22","eee");
		 ActivityData a14=new ActivityData("name","哥们刚失恋，去一家餐厅吃饭，刚好后面有一对情吕在唧唧我我的，好不亲热。","2014-07-22","eee");
		 ActivityData a15=new ActivityData("name","“我靠，哥们不用这样吧！”他以为我怕了还点头，我快速拿起钱跟钱包就走，尼玛追了我两条街还没追上被我跑","2014-07-22","eee");
		 ActivityData a16=new ActivityData("name","一对男女的对话：男：为什么你看上我啊？女：因为你长得帅埃男：帅又不能当饭吃。女：但是不帅的话，对着会吃不下饭。","2014-07-22","eee");
		 ActivityData a17=new ActivityData("name","1.校长和英语老师一起去法国某中学访问,校长在礼堂讲话,英语老师做翻译","2009-9-26","eee");
		 ActivityData a18=new ActivityData("name","笔记本说:我给你说个笑话呗。茶杯说:好啊好啊。笔记本说:从前有个茶杯,脑子进水了。茶杯:","5小时前","eee");
		 
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
		
		adapter=new ActivityAdapter(this,list,handler );
		listview.setAdapter(adapter);  
		listview.setOnDragListener(new OnDragListener() {
			
			@Override
			public boolean onDrag(View v, DragEvent event) {
				// TODO Auto-generated method stub
				listview.onDropDownComplete("哈哈");
				return false;
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
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_center, menu);
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
			
			if(msg.arg2==3)  //如果收到3那么就是要打开评论窗口
			{ 
			}
			else if(msg.arg2==2)		//如果收到arg2 收到2那就是要加载更多数据
			{	
				if(msg.arg1==-1)  //如果msg.arg1收到-1那么就是没有更多数据了
					{Toast.makeText(getApplicationContext(), "没有更多数据了", 1).show();
					listview.setOnBottomStyle(false);
					}
				else 
				{adapter.notifyDataSetChanged();
				listview.onBottomComplete();}
			}
			else if(msg.arg2==1)		//如果收到arg2 收到1那就是要打开对话框显示活动详情
			{
		    
				LayoutInflater inflaterDl = LayoutInflater.from(ActivityCenter.this);
		         View layout =inflaterDl.inflate(R.layout.activity_activity_center_detail, null );
		        
		         //对话框
		         final Dialog dialog = new AlertDialog.Builder(ActivityCenter.this).create();
		         TextView text=(TextView)layout.findViewById(R.id.title);
		         text.setText(msg.arg1+"");
		         dialog.show();
		         dialog.getWindow().setContentView(layout);
			}
			else container.scrollTo(0, msg.arg1);
		};
	};

 
	 

}
