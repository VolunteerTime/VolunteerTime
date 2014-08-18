/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-23
 */
package scau.info.volunteertime.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import scau.info.volunteertime.vo.Result;
import android.util.Log;
import cn.trinea.android.common.util.HttpUtils;

/**
 * Results的BO
 * 
 * @author 蔡超敏
 * 
 */
public class ResultBO {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	/**
	 * 构造方法，初始化静态数据
	 */
	public ResultBO() {
		// List<Result> allResults = new ArrayList<Result>();
		// for (int i = 0; i < 30; i++) {// 建立包含30个result的list
		// allResults
		// .add(new Result(
		// i,
		// "测试title" + i,
		// "测试内容：什么东西啊，我不会写很多东西的，这个是第" + i + "个例子",
		// "http://img.hb.aicdn.com/fda4bb25fc546ed22c9ff137ac72b7ba27e62e9916225-2ywtVp_fw658",
		// "chaoKing" + i, new Date(i * 100000).toGMTString()));
		// }
	}

	/**
	 * 返回endDate前的数据
	 * 
	 * @param endTime
	 * @return ArrayList<Result>
	 */
	public ArrayList<Result> getDownData(String endTime, int currentPageSize) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 1 + "");
		maps.put("currentPageSize", currentPageSize + "");
		maps.put("endTime", endTime);

		String jsonStr = HttpUtils.httpPostString(
				BOConstant.GET_NEW_RESULTS_DATA_URL, maps);

		Log.d("getDownData", "获取信息列表" + currentPageSize + ":" + jsonStr);

		try {
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

		String jsonStr = HttpUtils.httpPostString(
				BOConstant.GET_NEW_RESULTS_DATA_URL, maps);

		Log.d("getDownData", "获取信息列表" + currentPageSize + ":" + jsonStr);

		try {
			return jsonToList(jsonStr);
		} catch (ParseException e) {
			Log.d("ResultBO-getDropDownData", "ParseException-err");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 第一次更新数据，更新的是最前面currentPageSize条数据
	 * 
	 * @param currentPageSize
	 * @return ArrayList<Result>
	 */
	public ArrayList<Result> getNewData(int currentPageSize) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 0 + "");
		maps.put("currentPageSize", currentPageSize + "");

		String jsonStr = HttpUtils.httpPostString(
				BOConstant.GET_NEW_RESULTS_DATA_URL, maps);

		try {
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
			JSONObject jsonResults = new JSONObject(jsonStr); // 根据字符串转成JSONObject对象
			jaryProducts = jsonResults.getJSONArray("records");
			pageSize = jsonResults.getInt("pageSize"); // 得到pagesize的值

			Log.d("ResultBO-jsonToPagination", "获取信息列表" + pageSize + ":"
					+ jsonStr);

			ArrayList<Result> list = new ArrayList<Result>(); // 得到Result的List
			JSONObject jsonResult = null; // 用于保存CheapCard的json对象
			Result result;

			for (int i = 0; i < jaryProducts.length(); i++) {
				jsonResult = jaryProducts.getJSONObject(i);
				result = new Result(jsonResult.getInt("id"));
				result.setTitle(jsonResult.getString("title"));
				result.setContent(jsonResult.getString("content"));
				String time = jsonResult.getString("publishTime");
				result.setPublishTime(Long.parseLong(time));
				result.setImage(jsonResult.getString("image"));
				result.setEditor(jsonResult.getString("editor"));
				list.add(result);
				
				Log.d("jsonToList", "getPublishTime = "+result.getPublishTime()+" : result.getDate = "+result.getDate());
			}

			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			Log.d("debug", "服务器返回非json");
		}
		return null;
	}

}
