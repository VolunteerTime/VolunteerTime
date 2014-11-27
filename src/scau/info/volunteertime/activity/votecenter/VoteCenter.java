/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.activity.votecenter;

/**

 * 
 * 文件创建时间：2014-7-15
 */
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.votecenter.myArrayAdapter.OnSendVoteDataListener;
import scau.info.volunteertime.application.Ding9App;
import scau.info.volunteertime.business.voteCenterBO;
import scau.info.volunteertime.util.NetworkStateUtil;
import scau.info.volunteertime.util.SortedLinkList;
import scau.info.volunteertime.vo.VoteData;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.trinea.android.common.util.ToastUtils;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.tjerkw.slideexpandable.library.PullToRefreshDropDownExpandableListView;

/**
 * @author 蔡超敏
 * 
 */
public class VoteCenter extends Fragment {

	private myArrayAdapter listAdapter;
	private ArrayList<VoteData> votesDate = new ArrayList<VoteData>();

	private Activity activity;
	private SortedLinkList<VoteData> sortedLinkList;
	private PullToRefreshDropDownExpandableListView downExpandableListView;

	private voteCenterBO voteCenterBO;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_vote_center, container,
				false);
		activity = getActivity();

		downExpandableListView = (PullToRefreshDropDownExpandableListView) view
				.findViewById(R.id.listView);

		downExpandableListView.setMode(Mode.BOTH);

		sortedLinkList = new SortedLinkList<VoteData>();
		voteCenterBO = new voteCenterBO();

		listAdapter = new myArrayAdapter(getActivity(),
				R.layout.activity_vote_center_expandable_list_item, R.id.text,
				sortedLinkList.getSortedLinkList());

		downExpandableListView.setAdapter(listAdapter);

		downExpandableListView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				Log.d("VoteCenter-onPullDownToRefresh", "onPullDownToRefresh");
				new GetDataTask().execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				Log.d("VoteCenter-onPullUpToRefresh", "onPullUpToRefresh");
				// new GetDataTask().execute();
				downExpandableListView.onRefreshComplete();
			}
		});

		listAdapter.setOnSendVoteDataListener(new OnSendVoteDataListener() {

			@Override
			public void onSendVoteData(VoteData voteData) {
				Log.d("VoteCenter", "onSendVoteData start");
				new commitDataTask(voteData).execute();
			}
		});

		if (listAdapter.getCount() == 0) {
			Log.d("chao-onCreateView", "setState");
			downExpandableListView.setState(State.REFRESHING, true);
			downExpandableListView.setHeaderScroll(-84);
			new GetDataTask().execute();
		}
		return view;

	}

	private class commitDataTask extends AsyncTask<Void, Void, String> {

		private boolean isConnect;

		private String userId;
		private VoteData voteData;

		/**
		 * @param voteData
		 * @param userId
		 */
		public commitDataTask(VoteData voteData) {
			this.voteData = voteData;
		}

		@Override
		protected String doInBackground(Void... params) {
			isConnect = NetworkStateUtil.isNetworkAvailable(activity);

			Log.d("GetDataTask-doInBackground", "isConnect = " + isConnect);
			if (!isConnect) {
				Log.d("doInBackground", "isConnect not");
				cancel(true);
				return null;
			}

			Log.d("GetDataTask-doInBackground", "in");

			return doInBackgroundFunction();
		}

		@Override
		protected void onCancelled() {
			cancelledFunction();
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(String result) {
			if (isCancelled()) {
				Log.d("Cancle", "call");
				cancelledFunction();
			} else {
				postFunction(result);
			}
			super.onPostExecute(result);
		}

		/**
		 * @param result
		 */
		private void postFunction(String result) {
			Log.d("commitDataTask-postFunction", "result = " + result);
		}

		/**
		 * 
		 */
		private void cancelledFunction() {
			if (!isConnect) {
				ToastUtils.show(activity, "网络连接不正常");
			}
		}

		/**
		 * 
		 */
		private String doInBackgroundFunction() {
			userId = ((Ding9App) activity.getApplicationContext()).getUserId();
			Log.d("doInBackgroundFunction", "userId = " + userId);
			return toGetNewDataFromNet();
		}

		/**
		 * 
		 */
		private String toGetNewDataFromNet() {
			Log.d("doInBackgroundFunction", "getNewData1");
			String votes = "";
			try {
				JSONArray jsonArray = new JSONArray();
				ArrayList<Integer> integers = voteData.getVotes();
				for (int i = 0; i < integers.size(); i++) {
					JSONObject jsonObject = new JSONObject();

					jsonObject.put("vote", integers.get(i));
					jsonArray.put(jsonObject);
				}
				JSONObject object = new JSONObject();
				object.put("votes", jsonArray);
				votes = object.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.d("toGetNewDataFromNet", "votes = " + votes);
			return voteCenterBO.commitData(userId, votes, voteData.getId());

		}
		// private void toSaveDataInDatabase(LinkedList<VoteData> voteDatas) {
		//
		// SQLiteDatabase db = activity.openOrCreateDatabase(
		// "volunteertimedatabase.db", Context.MODE_PRIVATE, null);
		// for (VoteData voteData : voteDatas) {
		// db.execSQL(
		// "REPLACE INTO votes(id, receive_user_id , launch_user_id , title , content  , launch_time , is_send) VALUES(?,?,?,?,?,?,?)",
		// new Object[] { message.getId(),
		// message.getReceive_user_id(),
		// message.getLaunch_user_id(),
		// message.getTitle(), message.getContent(),
		// message.getLaunch_time(), message.getIs_send() });
		// }
		//
		// db.close();
		//
		// }
	}

	private class GetDataTask extends AsyncTask<Void, Void, Void> {

		private boolean isConnect;

		private String userId;

		/**
		 * @param userId
		 */
		public GetDataTask() {
		}

		@Override
		protected Void doInBackground(Void... params) {
			isConnect = NetworkStateUtil.isNetworkAvailable(activity);

			Log.d("GetDataTask-doInBackground", "isConnect = " + isConnect);
			if (!isConnect) {
				Log.d("doInBackground", "isConnect not");
				cancel(true);
				return null;
			}

			Log.d("GetDataTask-doInBackground", "in");
			doInBackgroundFunction();
			return null;
		}

		@Override
		protected void onCancelled() {
			cancelledFunction();
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(Void result) {
			if (isCancelled()) {
				Log.d("Cancle", "call");
				cancelledFunction();
			} else {
				postFunction(result);
			}
			super.onPostExecute(result);
		}

		/**
		 * @param result
		 */
		private void postFunction(Void result) {

			listAdapter.notifyDataSetChanged();

			downExpandableListView.onRefreshComplete();
		}

		/**
		 * 
		 */
		private void cancelledFunction() {
			if (!isConnect) {
				ToastUtils.show(activity, "网络连接不正常");
			}
			downExpandableListView.onRefreshComplete();
		}

		/**
		 * 
		 */
		private void doInBackgroundFunction() {
			userId = ((Ding9App) activity.getApplicationContext()).getUserId();
			Log.d("doInBackgroundFunction", "userId = " + userId);
			toGetNewDataFromNet();
		}

		/**
		 * 
		 */
		private void toGetNewDataFromNet() {
			Log.d("doInBackgroundFunction", "getNewData1");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ArrayList<VoteData> voteDatas = voteCenterBO.getNewData(userId);
			if (voteDatas != null) {
				Log.d("toGetNewDataFromNet",
						"voteDatas size = " + voteDatas.size());
				// sortedLinkList = new SortedLinkList<VoteData>();

				LinkedList<VoteData> voteDatas2 = sortedLinkList
						.getSortedLinkList();

				while (!voteDatas2.isEmpty()) {
					voteDatas2.removeFirst();
				}

				sortedLinkList.addAll(voteDatas);
				// toSaveDataInDatabase(sortedLinkList.getSortedLinkList());
			}
			Log.d("doInBackgroundFunction", "getNewData2");
		}

		// private void toSaveDataInDatabase(LinkedList<VoteData> voteDatas) {
		//
		// SQLiteDatabase db = activity.openOrCreateDatabase(
		// "volunteertimedatabase.db", Context.MODE_PRIVATE, null);
		// for (VoteData voteData : voteDatas) {
		// db.execSQL(
		// "REPLACE INTO votes(id, receive_user_id , launch_user_id , title , content  , launch_time , is_send) VALUES(?,?,?,?,?,?,?)",
		// new Object[] { message.getId(),
		// message.getReceive_user_id(),
		// message.getLaunch_user_id(),
		// message.getTitle(), message.getContent(),
		// message.getLaunch_time(), message.getIs_send() });
		// }
		//
		// db.close();
		//
		// }
	}

	class myAsynctask extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			System.out.println("准备取数据");
			votesDate.addAll(new voteCenterBO().getVotesData(0));
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			listAdapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

	}

	private ArrayList<VoteData> getData() {

		ArrayList<VoteData> votesDate = new ArrayList<VoteData>();

		VoteData data = new VoteData();
		ArrayList<String> stringData = new ArrayList<String>();
		stringData.add("1.我喜欢打篮球");
		stringData.add("2.我喜欢打羽毛球");
		stringData.add("3.我喜欢打排球");
		stringData.add("4.我喜欢打冰棒球");

		ArrayList<Integer> votes = new ArrayList<Integer>();
		votes.add(20);
		votes.add(40);
		votes.add(15);
		votes.add(50);

		data.setVotes(votes);
		data.setChoice(stringData);
		data.setSingle(true);
		data.setTitle("你喜欢的运动是什么");

		votesDate.add(data);

		VoteData data1 = new VoteData();
		ArrayList<String> stringData1 = new ArrayList<String>();
		stringData1.add("1.选项AAAAAA");
		stringData1.add("2.选项bbbbbb");
		stringData1.add("3.选项CCCCCC");
		ArrayList<Integer> votes1 = new ArrayList<Integer>();
		votes1.add(20);
		votes1.add(40);
		votes1.add(15);

		data1.setVotes(votes1);
		data1.setChoice(stringData1);
		data1.setSingle(true);
		data1.setTitle("你喜欢的选项是什么");

		votesDate.add(data1);

		VoteData data2 = new VoteData();
		ArrayList<String> stringData2 = new ArrayList<String>();
		stringData2.add("1.小学");
		stringData2.add("2.初中");
		stringData2.add("3.高中");
		stringData2.add("4.大学");
		stringData2.add("5.毕业之后");
		ArrayList<Integer> votes2 = new ArrayList<Integer>();
		votes2.add(20);
		votes2.add(40);
		votes2.add(15);
		votes2.add(50);
		votes2.add(5);
		data2.setVotes(votes2);

		data2.setChoice(stringData2);
		data2.setSingle(false);
		data2.setTitle("你的初恋时什么时候");
		System.out.println("hahhahahahahahhahaha" + data2.getVotes());
		votesDate.add(data2);

		VoteData data3 = new VoteData();
		ArrayList<String> stringData3 = new ArrayList<String>();
		stringData3.add("1.11111");
		stringData3.add("2.222222");
		stringData3.add("3.33333");
		stringData3.add("4.4444444");
		ArrayList<Integer> votes3 = new ArrayList<Integer>();
		votes3.add(20);
		votes3.add(40);
		votes3.add(15);
		votes3.add(50);

		data3.setVotes(votes3);
		data3.setChoice(stringData3);
		data3.setSingle(true);
		data3.setTitle("你测试而已不要较真");

		votesDate.add(data3);

		VoteData data4 = new VoteData();
		ArrayList<String> stringData4 = new ArrayList<String>();
		stringData4.add("1.我喜欢打篮球");
		stringData4.add("2.我喜欢打羽毛球");
		stringData4.add("3.我喜欢打排球");
		stringData4.add("4.我喜欢打冰棒球");
		ArrayList<Integer> votes4 = new ArrayList<Integer>();
		votes4.add(20);
		votes4.add(40);
		votes4.add(15);
		votes4.add(50);

		data4.setVotes(votes4);

		data4.setChoice(stringData4);
		data4.setSingle(false);
		data4.setTitle("你喜欢的运动是什么");

		votesDate.add(data4);

		return votesDate;

	}

}
