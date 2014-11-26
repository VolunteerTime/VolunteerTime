/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.business;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import scau.info.volunteertime.vo.ActivityDate;
import scau.info.volunteertime.vo.ActivityGroup;
import android.util.Log;
import cn.trinea.android.common.util.HttpUtils;

/**
 * @author 林锡鑫
 * 
 */
public class ActivityCenterBO {

	/**
	 * @return
	 */
	public ArrayList<ActivityDate> getNewData() {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 0 + "");

		String jsonStr = HttpUtils.httpPostString(
				BOConstant.GET_NEW_ACTIVITIES_DATA_URL, maps);

		try {
			return jsonToList(jsonStr);
		} catch (ParseException e) {
			Log.d("ActivityCenterBO-getNewData", "ParseException-err");
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<ActivityDate> jsonToList(String jsonStr)
			throws ParseException {
		JSONArray jaryProducts = null;
		int pageSize = 10;
		try {
			JSONObject jsonResults = new JSONObject(jsonStr);
			jaryProducts = jsonResults.getJSONArray("records");
			pageSize = jsonResults.getInt("pageSize");

			Log.d("ActivityCenterBO-jsonToPagination", "return size is"
					+ pageSize + ": the json is " + jsonStr);

			ArrayList<ActivityDate> list = new ArrayList<ActivityDate>();
			JSONObject jsonActivityDate = null;
			ActivityDate activityDate;

			for (int i = 0; i < jaryProducts.length(); i++) {
				jsonActivityDate = jaryProducts.getJSONObject(i);
				activityDate = new ActivityDate(jsonActivityDate.getInt("id"));
				activityDate.setTitle(jsonActivityDate.getString("title"));
				activityDate.setContent(jsonActivityDate.getString("content"));
				String time = jsonActivityDate.getString("publish_time");
				activityDate.setPublishTime(Long.parseLong(time));
				time = jsonActivityDate.getString("end_time");
				activityDate.setEndTime(Long.parseLong(time));

				activityDate.setLimitNum(jsonActivityDate.getInt("limit_num"));
				activityDate.setReadNum(jsonActivityDate.getInt("read_num"));
				int groupId = jsonActivityDate.getInt("group_id");
				activityDate.setGroupId(groupId);
				activityDate.setImage(jsonActivityDate.getString("image"));
				activityDate.setEditor(jsonActivityDate.getString("editor"));
				activityDate.setParticipatorsNum(jsonActivityDate
						.getInt("participators_num"));

				if (groupId != 0) {
					ActivityGroup activityGroup = new ActivityGroup(groupId,
							jsonActivityDate.getString("principal_id"),
							jsonActivityDate.getString("participators"));
					activityDate.setActivityGroup(activityGroup);
				}

				list.add(activityDate);

				Log.d("jsonToList",
						"getPublishTime = " + activityDate.getPublishTime()
								+ " : activityDate.getLimitNum = "
								+ activityDate.getLimitNum());
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
	 * @return String
	 */
	public String participateActivity(String userId, int activityId) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 1 + "");
		maps.put("userId", userId);
		maps.put("activityId", activityId + "");

		String jsonStr = HttpUtils.httpPostString(
				BOConstant.GET_NEW_ACTIVITIES_DATA_URL, maps);
		Log.d("participateActivity", "jsonStr = " + jsonStr);
		return jsonStr;
	}

	/**
	 * @param userId
	 * @param activityId
	 * @return
	 */
	public String quitParticipateActivity(String userId, int activityId) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 2 + "");
		maps.put("userId", userId);
		maps.put("activityId", activityId + "");

		String jsonStr = HttpUtils.httpPostString(
				BOConstant.GET_NEW_ACTIVITIES_DATA_URL, maps);
		Log.d("quitParticipateActivity", "jsonStr = " + jsonStr);
		return jsonStr;
	}

	/**
	 * @param id
	 */
	public void updateReadNum(int id) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 3 + "");
		maps.put("id", id + "");

		String jsonStr = HttpUtils.httpPostString(
				BOConstant.GET_NEW_ACTIVITIES_DATA_URL, maps);

		Log.d("updateReadNum", "jsonStr = " + jsonStr);
	}

	/**
	 * @param userId
	 * @return
	 */
	public ArrayList<ActivityDate> getNewData(String userId) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 4 + "");
		maps.put("userId", userId);

		String jsonStr = HttpUtils.httpPostString(
				BOConstant.GET_NEW_ACTIVITIES_DATA_URL, maps);

		try {
			return jsonToList(jsonStr);
		} catch (ParseException e) {
			Log.d("ActivityCenterBO-getNewData", "ParseException-err");
			e.printStackTrace();
		}
		return null;
	}
}
