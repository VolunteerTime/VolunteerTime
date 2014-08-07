/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014��7��22��
 */
package scau.info.volunteertime.activity.activitycenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.MainActivity;
import scau.info.volunteertime.vo.ActivityData;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;  
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author ������
 *
 */
public class ActivityAdapter extends BaseAdapter {

	/**
	 * @param context
	 * @param data
	 * @param resource
	 * @param from
	 * @param to
	 */ 
	private ActivityData data;
	Handler adapterHandler=new Handler(){ 				//�õ�ActivityCenter����Ϣ���ж��Ƿ�remove���ۿ�
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.arg2==3) //֤���Ǵ���λ��Ҫ���ı����ۿ��λ�õ�
			{ 
				if(mGreenToolTipView!=null)
					{mGreenToolTipView.remove(); 
					mGreenToolTipView=null;} 
				 
			}
		}
		
	};
	
	private List<ActivityData> info=null;
	private Map<Integer,View> rowViews=new HashMap<Integer,View>();
	private Context context=null;
	Handler activityCenterHandler;
	commentClickListener commentListener;
	
	public Handler getHandler(){ return adapterHandler;}
	
	public ActivityAdapter(Context context,List<ActivityData> list,Handler handler,ToolTipRelativeLayout mToolTipFrameLayout)
	{
		activityCenterHandler=handler;
		this.info=list;
		this.context=context;
		this.mToolTipFrameLayout=mToolTipFrameLayout;
		commentListener=new commentClickListener();
	} 

	
	 
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		System.out.println("�õ�����"+info.size());
		return info.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		return info.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	
	View rowView;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		System.out.println("position "+position);
		 rowView=rowViews.get(position);
		if(rowView==null)
		{System.out.println("qushuju");
			data=info.get(position);
			LayoutInflater layoutInflater=LayoutInflater.from(context);
			
			
			rowView=layoutInflater.inflate(R.layout.activity_activity_center_adapter, null);
			
			Button commentButton=(Button)rowView.findViewById(R.id.commentButton);
			ImageButton imageButton=(ImageButton)rowView.findViewById(R.id.imageButton);
			TextView time=(TextView)rowView.findViewById(R.id.time);
			TextView content=(TextView)rowView.findViewById(R.id.content);
			TextView name=(TextView)rowView.findViewById(R.id.name);  
			
			TextView comment=(TextView)rowView.findViewById(R.id.commentContent);
			comment.setText("");
			commentButton.setTag(position);
			
			commentButton.setOnClickListener(commentListener);
			imageButton.setImageResource(R.drawable.action_help);		//���ͷ��
			time.setText(data.getTime());								//���ʱ��
			content.setText(data.getContent());							//��ӻ����
			name.setText(data.getName());								//��ӷ����˵�����
			rowViews.put(position, rowView);	//����������Ϊ����
			  
		
		}
		 
		return rowView;
	} 
	
	class commentClickListener implements OnClickListener{
		  
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String []content={"aaa:����������","��˵:��������","��˵:����ֵֹ�","ģ��:�ܶ��ܶ��ܶ��ܶ�","�ؼ�:���������������"};
			String kk="";
			int[] masterViewScreenPosition = new int[2];
			v.getLocationOnScreen(masterViewScreenPosition); 	//�õ���ť��λ��
			
			Message msg=activityCenterHandler.obtainMessage();
			msg.arg2=3;
			 
			   if (mGreenToolTipView == null) {
	                addGreenToolTipView(v); 
	                 
			   } else {
	                mGreenToolTipView.remove();
	                addGreenToolTipView(v); 
	            }
			   msg.sendToTarget();
		}
		
	}
	ToolTipRelativeLayout mToolTipFrameLayout;
    private ToolTipView mGreenToolTipView;
    private void addGreenToolTipView(View v) {
        ToolTip toolTip = new ToolTip()
                .withText("Another beautiful Button!")
                .withColor(Color.GREEN);

        mGreenToolTipView = mToolTipFrameLayout.showToolTipForView(toolTip, v);
       
    }
    
    

}
