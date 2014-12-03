/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-11-25
 */
package scau.info.volunteertime.business;

import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import scau.info.volunteertime.vo.Message;
import android.util.Log;
import cn.trinea.android.common.util.HttpUtils;

/**
 * @author 蔡超敏
 * 
 */
public class MessageBO {

	/**
	 * @param principalId
	 * @param participators
	 * @param message
	 * @return
	 */
	public String sendMessages(String principalId, String participators,
			String message, String title) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 0 + "");
		maps.put("principalId", principalId);
		maps.put("participators", participators);
		maps.put("message", message);
		maps.put("title", title);

		String jsonStr = null;
		try {
			jsonStr = HttpUtils.httpPostString(BOConstant.MESSAGES_URL, maps);
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.d("sendMessages", "jsonStr = " + jsonStr);
		return jsonStr;
	}

	/**
	 * @param userId
	 * @return
	 */
	public ArrayList<Message> getNewData(String userId) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 1 + "");
		maps.put("userId", userId);

		String jsonStr = null;
		try {
			jsonStr = HttpUtils.httpPostString(BOConstant.MESSAGES_URL, maps);
		} catch (SocketTimeoutException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Log.d("MessageBO-getNewData", "jsonStr = " + jsonStr);
		try {
			if (jsonStr != null)
				return jsonToList(jsonStr);
		} catch (ParseException e) {
			Log.d("MessageBO-getNewData", "ParseException-err");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param jsonStr
	 * @return
	 */
	private ArrayList<Message> jsonToList(String jsonStr) throws ParseException {
		JSONArray jaryProducts = null;
		int pageSize = 10;
		try {
			JSONObject jsonResults = new JSONObject(jsonStr);
			jaryProducts = jsonResults.getJSONArray("records");
			pageSize = jsonResults.getInt("pageSize");

			Log.d("ResultBO-jsonToPagination", "return size is" + pageSize
					+ ": the json is " + jsonStr);

			ArrayList<Message> list = new ArrayList<Message>();
			JSONObject jsonResult = null;
			Message message;

			for (int i = 0; i < jaryProducts.length(); i++) {
				jsonResult = jaryProducts.getJSONObject(i);
				message = new Message(jsonResult.getInt("id"),
						jsonResult.getString("receive_user_id"),
						jsonResult.getString("launch_user_id"),
						jsonResult.getString("title"),
						jsonResult.getString("content"),
						Long.parseLong(jsonResult.getString("launch_time")),
						jsonResult.getInt("is_send"));

				list.add(message);

				Log.d("jsonToList", "getPublishTime = " + message.getIs_send()
						+ " : result.getDate = " + message.getDate());
			}

			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			Log.d("debug", "json出问题");
		}
		return null;
	}

	/**
	 * @param id
	 */
	public void updateReadNum(int id) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 2 + "");
		maps.put("id", id + "");

		String jsonStr = null;
		try {
			jsonStr = HttpUtils.httpPostString(BOConstant.MESSAGES_URL, maps);
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.d("updateReadNum", "jsonStr = " + jsonStr);
	}

	/**
	 * @param userNameValue
	 * @param passwordValue
	 * @return
	 */
	public Message getNewMessage(String userNameValue, String passwordValue) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 3 + "");
		maps.put("userId", userNameValue);
		maps.put("password", passwordValue);

		String jsonStr = null;
		try {
			jsonStr = HttpUtils.httpPostString(BOConstant.MESSAGES_URL, maps);
		} catch (SocketTimeoutException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Log.d("MessageBO-getNewData", "jsonStr = " + jsonStr);
		try {
			if (jsonStr != null)
				return jsonToMessage(jsonStr);
		} catch (ParseException e) {
			Log.d("MessageBO-getNewData", "ParseException-err");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param id
	 */
	public void updateSent(int id) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 4 + "");
		maps.put("id", id + "");

		String jsonStr = null;
		try {
			if (jsonStr != null)
				jsonStr = HttpUtils.httpPostString(BOConstant.MESSAGES_URL,
						maps);
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.d("updateReadNum", "jsonStr = " + jsonStr);
	}

	/**
	 * @param jsonStr
	 * @return
	 */
	private Message jsonToMessage(String jsonStr) throws ParseException {
		JSONArray jaryProducts = null;
		int pageSize = 10;
		try {
			JSONObject jsonResults = new JSONObject(jsonStr);
			jaryProducts = jsonResults.getJSONArray("records");
			pageSize = jsonResults.getInt("pageSize");

			Log.d("ResultBO-jsonToPagination", "return size is" + pageSize
					+ ": the json is " + jsonStr);

			if (pageSize <= 0)
				return null;

			ArrayList<Message> list = new ArrayList<Message>();
			JSONObject jsonResult = null;
			Message message;

			for (int i = 0; i < jaryProducts.length(); i++) {
				jsonResult = jaryProducts.getJSONObject(i);
				message = new Message(jsonResult.getInt("id"),
						jsonResult.getString("receive_user_id"),
						jsonResult.getString("launch_user_id"),
						jsonResult.getString("title"),
						jsonResult.getString("content"),
						Long.parseLong(jsonResult.getString("launch_time")),
						jsonResult.getInt("is_send"));

				list.add(message);

				Log.d("jsonToList", "getPublishTime = " + message.getIs_send()
						+ " : result.getDate = " + message.getDate());
			}

			return list.get(0);
		} catch (JSONException e) {
			e.printStackTrace();
			Log.d("debug", "json出问题");
		}
		return null;
	}
}
