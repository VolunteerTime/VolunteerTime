package scau.info.volunteertime.activity.votecenter;

/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */ 
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpConnection;

import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;
import com.tjerkw.slideexpandable.library.DropDownExpandableListView;
import com.tjerkw.slideexpandable.library.SlideExpandableListAdapter; 

import scau.info.volunteertime.R;
import scau.info.volunteertime.business.voteCenterBO;
import scau.info.volunteertime.vo.VoteData;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment; 
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 蔡超敏
 *
 */
public class VoteCenter extends Fragment {
		
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	
	myArrayAdapter listAdapter;
	ArrayList<VoteData> votesDate=new ArrayList<VoteData>();
	
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onStart()
	 */ 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 		
		
		View view=inflater.inflate(R.layout.activity_vote_center, container,false);
		 
			// get a reference to the listview, needed in order
			// to call setItemActionListener on it
		
		DropDownExpandableListView list = (DropDownExpandableListView)view.findViewById(R.id.listView);

		// fill the list with data
	 	listAdapter=buildDummyData();
		list.setAdapter(listAdapter);
		
	 
				// TODO Auto-generated method stub
		new myAsynctask().execute(0);
				
				
				// listen for events in the two buttons for every list item.
				// the 'position' var will tell which list item is clicked
	 
		
	 
			return view;
	
	}

			
		class myAsynctask extends AsyncTask<Integer, Integer, Integer>{

			/* (non-Javadoc)
			 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
			 */
			@Override
			protected Integer doInBackground(Integer... params) {
				// TODO Auto-generated method stub
				System.out.println("准备取数据");
				 votesDate.addAll(new voteCenterBO().getVotesData(0));
				return null;
			}
			/* (non-Javadoc)
			 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
			 */
			@Override
			protected void onPostExecute(Integer result) {
				// TODO Auto-generated method stub
				listAdapter.notifyDataSetChanged();
				super.onPostExecute(result);
			}

				 
				
			}
	public myArrayAdapter buildDummyData() {
		  
	 
		return new myArrayAdapter(
				getActivity(),
				R.layout.activity_vote_center_expandable_list_item,
				R.id.text,
				votesDate  
		);
	}
	
	/*
	private ArrayList<VoteData> getData()
	{
	 
		ArrayList<VoteData> votesDate=new ArrayList<VoteData>();
		
		 
			VoteData data=new VoteData();
			data.setNumber(0);
			ArrayList<String> stringData=new ArrayList<String>();
						stringData.add("1.我喜欢打篮球");
						stringData.add("2.我喜欢打羽毛球");
						stringData.add("3.我喜欢打排球");
						stringData.add("4.我喜欢打冰棒球"); 
			ArrayList<Integer> votes=new ArrayList<Integer>();
						votes.add(20);
						votes.add(40);
						votes.add(15);
						votes.add(50);
			
			data.setVotes(votes);
			data.setChoice(stringData);
			data.setSingle(true);
			data.setTitle("你喜欢的运动是什么"); 
			
			votesDate.add(data);
		 
			VoteData data1=new VoteData();
			data1.setNumber(1);
			ArrayList<String> stringData1=new ArrayList<String>();
						stringData1.add("1.选项AAAAAA");
						stringData1.add("2.选项bbbbbb");
						stringData1.add("3.选项CCCCCC"); 
			ArrayList<Integer> votes1=new ArrayList<Integer>();
						votes1.add(20);
						votes1.add(40);
						votes1.add(15);
			
			data1.setVotes(votes1);
			data1.setChoice(stringData1);
			data1.setSingle(true);
			data1.setTitle("你喜欢的选项是什么"); 
			
			votesDate.add(data1);
			
			VoteData data2=new VoteData();
			data2.setNumber(2);
			ArrayList<String> stringData2=new ArrayList<String>();
						stringData2.add("1.小学");
						stringData2.add("2.初中");
						stringData2.add("3.高中");
						stringData2.add("4.大学");
						stringData2.add("5.毕业之后");
			ArrayList<Integer> votes2=new ArrayList<Integer>();
						votes2.add(20);
						votes2.add(40);
						votes2.add(15);
						votes2.add(50);
						votes2.add(5);
			data2.setVotes(votes2);
			
			data2.setChoice(stringData2);
			data2.setSingle(true);
			data2.setTitle("你的初恋时什么时候"); 
			System.out.println("hahhahahahahahhahaha"+data2.getVotes());
			votesDate.add(data2);
			
			VoteData data3=new VoteData();
			data3.setNumber(3);
			ArrayList<String> stringData3=new ArrayList<String>();
						stringData3.add("1.11111");
						stringData3.add("2.222222");
						stringData3.add("3.33333");
						stringData3.add("4.4444444"); 
		ArrayList<Integer> votes3=new ArrayList<Integer>();
						votes3.add(20);
						votes3.add(40);
						votes3.add(15);
						votes3.add(50);
			
			data3.setVotes(votes3);
			data3.setChoice(stringData3);
			data3.setSingle(true);
			data3.setTitle("你测试而已不要较真"); 
			
			votesDate.add(data3);
			
			
			VoteData data4=new VoteData();
			data4.setNumber(4);
			ArrayList<String> stringData4=new ArrayList<String>();
						stringData4.add("1.我喜欢打篮球");
						stringData4.add("2.我喜欢打羽毛球");
						stringData4.add("3.我喜欢打排球");
						stringData4.add("4.我喜欢打冰棒球"); 
			ArrayList<Integer> votes4=new ArrayList<Integer>();
						votes4.add(20);
						votes4.add(40);
						votes4.add(15);
						votes4.add(50);
			
			data4.setVotes(votes4);
			
			data4.setChoice(stringData4);
			data4.setSingle(true);
			data4.setTitle("你喜欢的运动是什么"); 
			
			votesDate.add(data4);
			
			
			
			
		return votesDate;
 
	}
	*/

}
