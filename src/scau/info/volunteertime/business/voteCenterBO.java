/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-8-16
 */

package scau.info.volunteertime.business;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import scau.info.volunteertime.vo.Result;
import scau.info.volunteertime.vo.VoteData;
import android.util.Log;
import cn.trinea.android.common.util.HttpUtils;

/**
 * @author 蔡超敏
 * 
 */
public class voteCenterBO {

	/**
	 * @return
	 */
	public static String VoteCenterAddress = BOConstant.ROOT_URL
			+ "/VolunteerTimeWeb/VolunteerTimeVoteCenterServlet";

	public ArrayList<VoteData> getVotesData(int lastId) {
		// TODO Auto-generated method stub

		URL url;
		String allData = "";
		try {
			url = new URL(VoteCenterAddress + "?lastId=" + lastId);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String data;

			while ((data = br.readLine()) != null) {
				allData = allData + data;
			}
			br.close();
			con.disconnect();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("拿到数据吗" + allData);
		ArrayList<VoteData> list = new ArrayList<VoteData>();
		try {
			JSONArray array = new JSONArray(allData);

			for (int i = 0; i < array.length(); i++) { // 把json数据全部转换成vo数据

				JSONObject jsonObject = array.getJSONObject(i);
				VoteData s = new VoteData();

				if (jsonObject.get("single").equals("1"))
					s.setSingle(true);
				else
					s.setSingle(false);

				s.setTitle((String) jsonObject.get("title"));

				s.setId(Integer.valueOf((String) jsonObject.get("id")));

				String choices = (String) jsonObject.get("choice");
				String[] cho = choices.split("\\|");

				ArrayList<String> choiceList = new ArrayList<String>();
				for (int j = 0; j < cho.length; j++)
					choiceList.add(cho[j]);
				s.setChoice(choiceList);

				String vote = (String) jsonObject.get("vote");

				String[] votes = vote.split("\\|");
				ArrayList<Integer> voteList = new ArrayList<Integer>();
				System.out.println("----  " + vote + "   " + votes.length);
				for (int j = 0; j < votes.length; j++) {
					voteList.add(Integer.valueOf(votes[j]));
				}
				s.setVotes(voteList);

				System.out.println(jsonObject.get("id"));
				System.out.println(jsonObject.get("single"));
				System.out.println(jsonObject.get("title"));
				System.out.println(jsonObject.get("choice"));
				System.out.println(jsonObject.get("vote"));

				list.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("votesCenter的json解析出错");
		}
		return list;
	}

	/**
	 * @param userId
	 * @return
	 */
	public ArrayList<VoteData> getNewData(String userId) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 0 + "");

		String jsonStr = HttpUtils.httpPostString(BOConstant.VOTES_URL, maps);

		try {
			return jsonToList(jsonStr, userId);
		} catch (ParseException e) {
			Log.d("ResultBO-getNewData", "ParseException-err");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param jsonStr
	 * @param userId
	 * @return ArrayList<VoteData>
	 */
	private ArrayList<VoteData> jsonToList(String jsonStr, String userId)
			throws ParseException {
		JSONArray jaryProducts = null;
		int pageSize = 10;
		try {
			JSONObject jsonResults = new JSONObject(jsonStr);
			jaryProducts = jsonResults.getJSONArray("records");
			pageSize = jsonResults.getInt("pageSize");

			Log.d("ResultBO-jsonToPagination", "return size is" + pageSize
					+ ": the json is " + jsonStr);

			ArrayList<VoteData> list = new ArrayList<VoteData>();
			JSONObject jsonResult = null;
			VoteData voteData;

			for (int i = 0; i < jaryProducts.length(); i++) {
				jsonResult = jaryProducts.getJSONObject(i);
				voteData = new VoteData();

				voteData.setId(jsonResult.getInt("id"));
				voteData.setSingle(jsonResult.getInt("type") == 1 ? true
						: false);
				voteData.setTitle(jsonResult.getString("content"));
				voteData.setChoiceStr(jsonResult.getString("choice"));
				voteData.setVoteStr(jsonResult.getString("votes"));
				voteData.setUserIds(jsonResult.getString("user_ids"));
				String time = jsonResult.getString("publish_date");
				voteData.setDate(Long.parseLong(time));
				time = jsonResult.getString("end_date");
				voteData.setEndTime(Long.parseLong(time));

				// 解析data
				ArrayList<String> strings = new ArrayList<String>();
				try {
					JSONArray array;
					JSONObject json = new JSONObject(voteData.getChoiceStr());
					array = json.getJSONArray("choices");
					int len = array.length();
					for (int j = 0; j < len; j++) {
						JSONObject jsonObj = array.getJSONObject(j);
						String choiceByJson = jsonObj.getString("choice");
						strings.add(choiceByJson);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				voteData.setChoice(strings);

				ArrayList<Integer> integers = new ArrayList<Integer>();
				try {
					JSONArray array;
					JSONObject json = new JSONObject(voteData.getVoteStr());
					array = json.getJSONArray("votes");
					int len = array.length();
					for (int j = 0; j < len; j++) {
						JSONObject jsonObj = array.getJSONObject(j);
						int choiceByJson = jsonObj.getInt("vote");
						integers.add(choiceByJson);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				voteData.setVotes(integers);

				voteData.setChecked(voteData.getUserIds().contains(
						"\"" + userId + "\"")
						|| voteData.getEndTime() <= System.currentTimeMillis());

				Log.d("jsonToList",
						"voteData getChecked = " + voteData.getChecked());

				list.add(voteData);

				Log.d("jsonToList", " : result.getDate = " + voteData.getDate());
			}

			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			Log.d("debug", "json出问题");
		}
		return null;
	}

	/**
	 * @param userId
	 * @param votes
	 * @param id
	 * @return
	 */
	public String commitData(String userId, String votes, int id) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 1 + "");
		maps.put("userId", userId);
		maps.put("votes", votes);
		maps.put("id", id + "");

		String jsonStr = HttpUtils.httpPostString(BOConstant.VOTES_URL, maps);
		Log.d("commitData", "jsonStr = " + jsonStr);
		return jsonStr;
	}

}
