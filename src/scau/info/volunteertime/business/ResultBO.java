/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.business;

import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import scau.info.volunteertime.vo.Result;
import android.util.Log;
import cn.trinea.android.common.util.HttpUtils;

/**
 * Results的BO 还没设置延迟问题
 * 
 * @author 蔡超敏
 * 
 */
public class ResultBO {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	/**
	 * 创建一个构造方法
	 */
	public ResultBO() {
	}

	/**
	 * ����endDateǰ������
	 * 
	 * @param endTime
	 * @return ArrayList<Result>
	 */
	public ArrayList<Result> getDownData(String endTime, int currentPageSize) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 1 + "");
		maps.put("currentPageSize", currentPageSize + "");
		maps.put("endTime", endTime);

		String jsonStr = null;
		try {
			jsonStr = HttpUtils.httpPostString(
					BOConstant.GET_NEW_RESULTS_DATA_URL, maps);
		} catch (SocketTimeoutException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Log.d("getDownData", "page size is " + currentPageSize
				+ ": the json is " + jsonStr);

		try {
			if (jsonStr != null)
				return jsonToList(jsonStr);
		} catch (ParseException e) {
			Log.d("ResultBO-getDownData", "ParseException-err");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param firstTime
	 * @param currentPageSize
	 * @return ArrayList<Result>
	 */
	public ArrayList<Result> getDropDownData(String firstTime,
			int currentPageSize) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 2 + "");
		maps.put("currentPageSize", currentPageSize + "");
		maps.put("firstTime", firstTime);

		String jsonStr = null;
		try {
			jsonStr = HttpUtils.httpPostString(
					BOConstant.GET_NEW_RESULTS_DATA_URL, maps);
		} catch (SocketTimeoutException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Log.d("getDownData", "page size is " + currentPageSize
				+ ": the json is " + jsonStr);

		try {
			if (jsonStr != null)
				return jsonToList(jsonStr);
		} catch (ParseException e) {
			Log.d("ResultBO-getDropDownData", "ParseException-err");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��һ�θ������ݣ����µ�����ǰ��currentPageSize������
	 * 
	 * @param currentPageSize
	 * @return ArrayList<Result>
	 */
	public ArrayList<Result> getNewData(int currentPageSize) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 0 + "");
		maps.put("currentPageSize", currentPageSize + "");

		String jsonStr = null;
		try {
			jsonStr = HttpUtils.httpPostString(
					BOConstant.GET_NEW_RESULTS_DATA_URL, maps);
		} catch (SocketTimeoutException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			if (jsonStr != null)
				return jsonToList(jsonStr);
		} catch (ParseException e) {
			Log.d("ResultBO-getNewData", "ParseException-err");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param jsonStr
	 * @return ArrayList<Result>
	 * @throws ParseException
	 */
	private ArrayList<Result> jsonToList(String jsonStr) throws ParseException {
		JSONArray jaryProducts = null;
		int pageSize = 10;
		try {
			JSONObject jsonResults = new JSONObject(jsonStr); // �����ַ���ת��JSONObject����
			jaryProducts = jsonResults.getJSONArray("records");
			pageSize = jsonResults.getInt("pageSize"); // �õ�pagesize��ֵ

			Log.d("ResultBO-jsonToPagination", "return size is" + pageSize
					+ ": the json is " + jsonStr);

			ArrayList<Result> list = new ArrayList<Result>(); // �õ�Result��List
			JSONObject jsonResult = null; // ���ڱ���CheapCard��json����
			Result result;

			for (int i = 0; i < jaryProducts.length(); i++) {
				jsonResult = jaryProducts.getJSONObject(i);
				result = new Result(jsonResult.getInt("id"));
				result.setTitle(jsonResult.getString("title"));
				result.setContent(jsonResult.getString("content"));
				String time = jsonResult.getString("publishTime");
				result.setPublishTime(Long.parseLong(time));
				result.setImage(BOConstant.IMAGE_PATH_URL
						+ jsonResult.getString("image"));
				result.setEditor(jsonResult.getString("editor"));
				result.setReadNum(jsonResult.getInt("read_num"));
				list.add(result);

				Log.d("jsonToList",
						"getPublishTime = " + result.getPublishTime()
								+ " : result.getDate = " + result.getDate());
			}

			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			Log.d("debug", "json出问题");
		}
		return null;
	}

	/**
	 * @param newResult
	 * @return String
	 */
	public String addNewResult(Result newResult) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param string
	 * @return string
	 */
	public String getUpdateDate(String string) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 3 + "");
		maps.put("ids", string);

		String jsonStr = null;
		try {
			jsonStr = HttpUtils.httpPostString(
					BOConstant.GET_NEW_RESULTS_DATA_URL, maps);
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.d("getUpdateDate", "jsonStr = " + jsonStr);

		return jsonStr;
	}

	/**
	 * @param id
	 */
	public void updateReadNum(int id) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 4 + "");
		maps.put("id", id + "");

		String jsonStr = null;
		try {
			jsonStr = HttpUtils.httpPostString(
					BOConstant.GET_NEW_RESULTS_DATA_URL, maps);
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.d("updateReadNum", "jsonStr = " + jsonStr);
	}

}
