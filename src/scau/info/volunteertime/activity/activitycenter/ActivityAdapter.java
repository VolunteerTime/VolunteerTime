/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014年7月22日
 */
package scau.info.volunteertime.activity.activitycenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scau.info.volunteertime.R;
import scau.info.volunteertime.vo.ActivityData;
import android.app.Dialog;
import android.content.Context;
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
 * @author 林锡鑫
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
	private List<ActivityData> info=null;
	private Map<Integer,View> rowViews=new HashMap<Integer,View>();
	private Context context=null;
	Handler activityCenterHandler;
 
	public ActivityAdapter(Context context,List<ActivityData> list,Handler handler)
	{
		activityCenterHandler=handler;
		this.info=list;
		this.context=context;
	} 

	
	 
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		System.out.println("得到长度"+info.size());
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
		
		 rowView=rowViews.get(position);
		if(rowView==null)
		{System.out.println("qushuju");
			data=info.get(position);
			LayoutInflater layoutInflater=LayoutInflater.from(context);
			rowView=layoutInflater.inflate(R.layout.activity_activity_center_adapter, null);
			ImageButton imageButton=(ImageButton)rowView.findViewById(R.id.imageButton);
			TextView time=(TextView)rowView.findViewById(R.id.time);
			TextView content=(TextView)rowView.findViewById(R.id.content);
			TextView name=(TextView)rowView.findViewById(R.id.name);  
			Button commentButton=(Button)rowView.findViewById(R.id.commentButton);

		 
			imageButton.setImageResource(R.drawable.action_help);		//添加头像
			time.setText(data.getTime());								//添加时间
			content.setText(data.getContent());							//添加活动内容
			name.setText(data.getName());								//添加发起人的名字
			rowViews.put(position, rowView);	//保存数据作为缓存
			 
			
		
		}
		 
		return rowView;
	} 
		 

}
