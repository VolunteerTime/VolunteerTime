/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-7-15
 */
package scau.info.volunteertime.activity.activitycenter;

import java.util.ArrayList;
import java.util.List;
 
 




import scau.info.volunteertime.R;
import scau.info.volunteertime.vo.ActivityData;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
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
 * @author �̳���
 *
 */
public class ActivityCenter extends ListActivity  {

	   
	ListView listview;
	View container;
	ActivityAdapter adapter;
	PageRecord getData=new PageRecord();	//������ȡ�������������listView
	List<ActivityData> list=new ArrayList<ActivityData>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_center);
		 
		container=findViewById(R.id.container);
	 
		 listview=(ListView)findViewById(android.R.id.list);
		
	 	 Animation animation=AnimationUtils.loadAnimation(ActivityCenter.this, R.anim.set);
	 // View menu=findViewById(R.id.menu);
 	// menu.startAnimation(animation);
		
		
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
			list.add(a6); 		list.add(a7); 
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
		ListView listView=getListView(); 
		listView.setAdapter(adapter); 
		listView.setOnTouchListener(new listViewOnTouch(true));
 
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
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_activity_center,
					container, false);
			return rootView;
		}
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
	 */
	 

	public Handler getHandler(){return handler;}
	Handler handler=new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			
			if(msg.arg2==3)  //����յ�3��ô����Ҫ�����۴���
			{
				findViewById(R.id.whole_comment).setVisibility(View.VISIBLE);
				
			}
			else if(msg.arg2==2)		//����յ�arg2 �յ�2�Ǿ���Ҫ���ظ�������
			{	
				if(msg.arg1==-1)  //���msg.arg1�յ�-1��ô����û�и���������
					Toast.makeText(getApplicationContext(), "û�и���������", 1).show();
				else 
				adapter.notifyDataSetChanged();
			}
			else if(msg.arg2==1)		//����յ�arg2 �յ�1�Ǿ���Ҫ�򿪶Ի�����ʾ�����
			{
		    
				LayoutInflater inflaterDl = LayoutInflater.from(ActivityCenter.this);
		         View layout =inflaterDl.inflate(R.layout.activity_activity_center_detail, null );
		        
		         //�Ի���
		         final Dialog dialog = new AlertDialog.Builder(ActivityCenter.this).create();
		         TextView text=(TextView)layout.findViewById(R.id.title);
		         text.setText(msg.arg1+"");
		         dialog.show();
		         dialog.getWindow().setContentView(layout);
			}
			else container.scrollTo(0, msg.arg1);
		};
	};

	public void reflesh(final int time,final int start,int k)
	{
		 	System.out.println(time+"   "+start);
				for(int i=time;i*k<=start*k;i+=k)
				{System.out.println(i);
					
					Message msg=handler.obtainMessage();
					msg.arg1=i;
					msg.sendToTarget();
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(i);
				}  
	}
	float ey=0;
	class listViewOnTouch implements OnTouchListener
	{
		boolean flag; //flagΪtrueʱ��������ڶ�����������ʱ��ΪFALSE���ڵײ���������ʱ��
		 
		public listViewOnTouch(boolean flag) {
			this.flag=flag;
		}
		public boolean onTouch(View v, MotionEvent event) { 
			//System.out.println(event.getY());
			
			
			System.out.println(container.getScrollY()+"  !!!  "+listview.getFirstVisiblePosition()+"   "+listview.getLastVisiblePosition()+"   "+listview.getCount());
			boolean flag;
			if(listview.getScrollY()==0&&listview.getFirstVisiblePosition()!=0&&listview.getLastVisiblePosition()!=listview.getCount()-1) return false;
			if(listview.getFirstVisiblePosition()==0||listview.getScrollY()<0) flag=true;
			else flag=false;
			System.out.println("ͨ��");
			
			switch(event.getAction())
			{
			case MotionEvent.ACTION_DOWN:ey=event.getY();  System.out.println("��dfdfdfdf����"+container.getScrollY()+"  "+ey);  break;
			case MotionEvent.ACTION_UP: 
				{
					Thread thread = null;
					System.out.println("scrollY= "+container.getScrollY()+"  "+flag);
					if(flag)
					{ 
						
						if(container.getScrollY()<=-70) {
						
					 	thread=new Thread(new refleshRunnable(true,true));
					 } else {System.out.println("С��70"+container.getScrollY());
						thread=new Thread(new refleshRunnable(false,true));
					 		}
					}
					else{//System.out.println("oooo "+container.getScrollY());
						 if(container.getScrollY()>=70) {
						 	thread=new Thread(new refleshRunnable(true,false));
					 	} else {
					 		thread=new Thread(new refleshRunnable(false,false));
					 	}
					}
					thread.start();
				} 
			} 
			if(flag)
			{if(ey!=0&&event.getY()>ey) container.scrollTo(0, - (int) ((event.getY()-ey)/5));	 System.out.println(event.getY()+"   "+ey+"  k= "+flag);
			}else
			{	
				if(ey!=0&&event.getY()<ey) container.scrollTo(0,  -(int) ((event.getY()-ey)/5));	 System.out.println(event.getY()+"   "+ey+"  k= "+flag);
				
			}
			return false;
		}
	}
	
	
	class refleshRunnable implements   Runnable  {
		boolean flag; //�Ƿ����ˢ�²�����������������ʾͽ���ˢ�£�����ˢ��
		boolean head;// �ж��������������������������������Ǹ���������������
		
		public refleshRunnable(boolean flag,boolean head) {
			// TODO Auto-generated constructor stub
			this.flag=flag;
			this.head=head;
		}
		public void run() { 
			try {
				int k;
				if(head) k=1;
				else k=-1;
				if(flag)
				{reflesh((int)container.getScrollY(),-100*k,k); 
				System.out.println("sleep   3000");
						//��������ø�������ݣ�����ӽ�ȥ
				Message msg=handler.obtainMessage();
				
				List<ActivityData> data=getData.getRecords();
				if(data==null) msg.arg1=-1;
				else list.addAll(data);
				
				msg.arg2=2;
				msg.sendToTarget();
				Thread.sleep(3000); 
				}
				
				 reflesh((int)container.getScrollY(),0,k); 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
	} 

}
