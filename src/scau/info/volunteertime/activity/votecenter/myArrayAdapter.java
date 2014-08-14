/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014年8月14日
 */
package scau.info.volunteertime.activity.votecenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 




















import scau.info.volunteertime.R;
import scau.info.volunteertime.vo.VoteData;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author 蔡超敏
 * @param <VoteData>
 *
 */
public class myArrayAdapter extends ArrayAdapter<VoteData>{

	/**
	 * @param context
	 * @param resource
	 * @param textViewResourceId
	 * @param objects
	 */
	final int ITEM_FONT_SIZE=15;	//选项字体大小
	final int TITLE_FONT_SIZE=18;//标题字体大小
	int Pcolor[];
	HashMap<Integer,View> map=new HashMap< Integer,View>(); //缓存
	
	List<VoteData> votedata;//adapter里面的数据
	Context context;
	int layout;
	LayoutInflater inflater; 
	
	public myArrayAdapter(Context context, int resource,
			int textViewResourceId, List<VoteData> objects) {
		super(context, resource, textViewResourceId, objects);
		votedata=objects;
		layout=resource;
		this.context=context; 
		Pcolor=new int[]{0xFFFF8C05,0xFF43A102,0xFF4499EE,0xFFEED205,0xFF1291A9,0xFF712704,0xFF70E1FF};
		 
		inflater=LayoutInflater.from(context);
		// TODO Auto-generated constructor stub
	}
 
	/**
	 * @param context
	 * @param resource
	 */ 
	 
	
	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	         ViewGroup expandable=(ViewGroup)view.findViewById(R.id.expandable);
        
        RadioGroup radioGroup=new RadioGroup(context);
        AbsListView.LayoutParams layoutParams=new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        radioGroup.setLayoutParams(layoutParams);
        
        ArrayList<String> sum=votedata.get(position).getChoice();
        System.out.println("dkfkdjfkdjkfdkf  "+sum+"   "+position);
        
        
        RelativeLayout relativeLayout=new RelativeLayout(context);
        
        RelativeLayout.LayoutParams rulel=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rulel.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        RelativeLayout.LayoutParams ruler=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ruler.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	 *
	 */
	private int getAllVotes(ArrayList<Integer> votes)	//得到所有投票方便统计比例
	{
		int sum=0;
		
		for(int i:votes)
		{
			sum+=i;
		}
		return sum;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 System.out.println("position"+position+"   "+votedata.get(position).getVotes());
		
        View view=map.get(position);
        if(votedata.get(position).isChange)	//如果是投票了，需要更换画面
        {	
        	

        	
        	view=this.inflater.inflate(layout, parent, false);
            TextView  text = (TextView) view.findViewById(R.id.voteTitle); //显示投票主题
            text.setTextSize(TITLE_FONT_SIZE);
            text.setPadding(5, 5, 5, 0);
            text.setText(votedata.get(position).getTitle());
            ViewGroup expandable=(ViewGroup)view.findViewById(R.id.expandable);
            
            setCheckView(expandable,position,parent);
            
            votedata.get(position).isChange=false;
            map.put(position, view);
            return view;
        }
        else if(view==null)
        {view=this.inflater.inflate(layout, parent, false);
        
         
        ViewGroup expandable=(ViewGroup)view.findViewById(R.id.expandable);
        
        TextView  text = (TextView) view.findViewById(R.id.voteTitle); //显示投票主题
        text.setText(votedata.get(position).getTitle());
        text.setTextSize(TITLE_FONT_SIZE);
        text.setPadding(5, 5, 5, 0);
        
       setUncheckView(expandable,position,parent);
       map.put(position, view);
       return view;
        }
        
        return view;
	}
	/*
	 *    expandable.addView(textView);
        
        ImageView progress=new ImageView(context);
        
        progress.setImageResource(R.drawable.voteprogress);
        int votes=votedata.get(position).getVotes().get(i);
        AbsListView.LayoutParams layoutParams=new AbsListView.LayoutParams(screenWidth*(),ViewGroup.LayoutParams.WRAP_CONTENT);
        
        progress.setLayoutParams(layoutParams);
        
        expandable.addView(progress);*/
	private void setCheckView(ViewGroup expandable,int position,ViewGroup parent)
	{
		 ArrayList<String> sum=votedata.get(position).getChoice();
		 System.out.println(position+"changeeeee "+sum.size()+"    "+votedata.get(position).getVotes());
		 int allVotes=getAllVotes(votedata.get(position).getVotes());
		int i=0; 
		for(String s:sum)
        {	
        TextView textView=new TextView(context); 
       
        textView.setText(s); 
        textView.setTextSize(ITEM_FONT_SIZE);
        textView.setLines(1);
        textView.setPadding(30, 19, 30, 3);
        
        expandable.addView(textView);
        
        LinearLayout layout=new LinearLayout(context);	//用来装载颜色进度条和百分比
      
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,19));
        layout.setPadding(0, 0, 0, 0);
      
        layout.setOrientation(LinearLayout.HORIZONTAL);
        
        
        ImageView progress=new ImageView(context);
        progress.setBackgroundColor(Pcolor[i%Pcolor.length]);
        
        int votes=votedata.get(position).getVotes().get(i++);	
        int pro=(int)(VoteCenter.ScreenWidth*0.8*votes/allVotes); 	//设置每个投票选项的比例，需要用到屏幕宽度
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(pro,16);	//每个投票选项的长度为20
        layoutParams.setMargins(30, 0, 30, 0);
        
        
        
        
        
        ScaleAnimation   scaleAnimation=new ScaleAnimation(0f,1f,1f,1f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f);//明程度，从1-0
        scaleAnimation.setDuration(300);
       progress.setAnimation(scaleAnimation);
        progress.setLayoutParams(layoutParams); 
        
        TextView pen=new TextView(context);
      //  System.out.println("百分比啊！！！  "+votes*100/allVotes); 
        pen.setText(votes*100/allVotes+"%");	//百分比
       pen.setTextSize(14);   
       pen.setPadding(0, -8, 0, 0);
        layout.addView(progress);
        layout.addView(pen);
        expandable.addView(layout); 
        }
		
	}
	
	private void setUncheckView(ViewGroup expandable,int position,ViewGroup parent)
	{

        RadioGroup radioGroup=new RadioGroup(context); 

        AbsListView.LayoutParams layoutParams=new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
         
        radioGroup.setLayoutParams(layoutParams);
        radioGroup.setTag(position);
       
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				System.out.println("投票了"+group.getTag());
				votedata.get((Integer) group.getTag()).isChange=true;
				notifyDataSetChanged();
				// TODO Auto-generated method stub
			}
		});
        ArrayList<String> sum=votedata.get(position).getChoice();
        
        for(String i:sum)
        {RadioButton radioButton=(RadioButton) inflater.inflate(R.layout.radio_button,parent,false);
       
         radioButton.setLayoutParams(layoutParams);
         radioButton.setPadding(30, 0, 30, 0);
         radioButton.setTextSize(ITEM_FONT_SIZE);
         radioButton.setText(i);
         radioButton.setLines(1);
        radioGroup.addView(radioButton);
        }
        
        expandable.addView(radioGroup);
	}
	 
}
