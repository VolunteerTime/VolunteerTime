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

import scau.info.volunteertime.vo.UserInfo;
import android.util.Log;
import cn.trinea.android.common.util.HttpUtils;

/**
 * @author 蔡超敏
 * 
 */
public class UserBO {

	/**
	 * 
	 * public static final int REP_VAR_PARA_ERR = 0;// 帐号密码为空 public static
	 * final int PASSWORD_ERROR = -1;// 密码不合法 public static final int
	 * USER_NOT_EXIST = -2;// 用户不存在 public static final int
	 * USER_REGISTER_SUCCESS = 1;// 用户注册成功
	 * 
	 * @param userId
	 * @param password
	 * @return int
	 */
	public int CheckUserLoginResult(String userId, String password) {

		if (userId == null || password == null || userId.equals("")
				|| password.equals(""))
			return BOConstant.REP_VAR_PARA_ERR; // 返回-1 帐号\密码为空的情况

		// 设置查询条件
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 0 + "");
		maps.put("userId", userId);
		maps.put("password", password);
		String servletStr = HttpUtils.httpPostString(
				BOConstant.GET_USER_INFO_URL, maps);
		servletStr = servletStr.trim();
		Log.d("CheckUserLoginResult", "servletStr = " + servletStr);
		if (servletStr.length() < 10) {
			if (servletStr.equals("-2"))// 用户不存在
				return BOConstant.USER_NOT_EXIST;
			else if (servletStr.equals("-1"))// 密码不合法
				return BOConstant.PASSWORD_ERROR;
			else if (servletStr.equals("0"))// 帐号密码为空
				return BOConstant.REP_VAR_PARA_ERR;
			else if (servletStr.equals("1"))// 用户注册成功
				return BOConstant.USER_REGISTER_SUCCESS;
		}
		return BOConstant.REP_VAR_PARA_ERR;// 其他错误。

	}

	/**
	 * BOConstant.USER_HAS_EXIST 用户名已存在
	 * 
	 * BOConstant.REPASSWORD_ERROR 两次密码不一致
	 * 
	 * BOConstant.REGISTER_SUCCESS 成功
	 * 
	 * 
	 * @param userId
	 * @param password
	 * @param repassword
	 * @return
	 */
	public Integer CheckUserRegisterResult(String userId, String password,
			String repassword) {
		if (userId == null || password == null || repassword == null
				|| userId.equals("") || password.equals("")
				|| repassword.equals(""))
			return BOConstant.REP_VAR_PARA_ERR; // 返回0 帐号\密码为空的情况

		// 设置查询条件
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 1 + "");
		maps.put("userId", userId);
		maps.put("password", password);
		String servletStr = HttpUtils.httpPostString(
				BOConstant.GET_USER_INFO_URL, maps);
		servletStr = servletStr.trim();
		Log.d("CheckUserLoginResult", "servletStr = " + servletStr);
		if (servletStr.length() < 10) {
			if (servletStr.equals("10"))// 用户名已存在
				return BOConstant.USER_HAS_EXIST;
			else if (servletStr.equals("12"))// 成功
				return BOConstant.REGISTER_SUCCESS;
		}
		return BOConstant.REP_VAR_PARA_ERR;// 其他错误。

	}

	/**
	 * @param userInfo
	 * @param password
	 * @return
	 */
	public Integer commitUserData(UserInfo userInfo, String password) {
		String userId = userInfo.getUserId();

		if (userId == null || password == null || userId.equals("")
				|| password.equals(""))
			return BOConstant.REP_VAR_PARA_ERR; // 返回0 帐号\密码为空的情况

		// 设置查询条件
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 2 + "");
		maps.put("userId", userId);
		maps.put("password", password);

		String name = userInfo.getUserName();
		String sex = userInfo.getSex();
		String className = userInfo.getGradeAndMajor();
		String longPhone = userInfo.getCellPhone();
		String briefPhone = userInfo.getBriefPhone();
		String qq = userInfo.getQq();
		String wechant = userInfo.getWechant();

		maps.put("name", name);
		maps.put("sex", sex);
		maps.put("className", className);
		maps.put("longPhone", longPhone);
		maps.put("briefPhone", briefPhone);
		maps.put("qq", qq);
		maps.put("wechant", wechant);

		String servletStr = HttpUtils.httpPostString(
				BOConstant.GET_USER_INFO_URL, maps);
		servletStr = servletStr.trim();
		Log.d("CheckUserLoginResult", "servletStr = " + servletStr);
		if (servletStr.length() < 10) {
			if (servletStr.equals("10"))// 用户名已存在
				return BOConstant.USER_HAS_EXIST;
			else if (servletStr.equals("12"))// 成功
				return BOConstant.REGISTER_SUCCESS;
		}
		return BOConstant.REP_VAR_PARA_ERR;// 其他错误。
	}

	/**
	 * @param userId
	 * @return
	 */
	public UserInfo getUserInfo(String userId) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 3 + "");
		maps.put("userId", userId);

		String jsonStr = HttpUtils.httpPostString(BOConstant.GET_USER_INFO_URL,
				maps);

		Log.d("MessageBO-getNewData", "jsonStr = " + jsonStr);
		try {
			return jsonToUserInfo(jsonStr);
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
	private UserInfo jsonToUserInfo(String jsonStr) throws ParseException {
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

			ArrayList<UserInfo> list = new ArrayList<UserInfo>();
			JSONObject jsonResult = null;
			UserInfo userInfo;

			for (int i = 0; i < jaryProducts.length(); i++) {
				jsonResult = jaryProducts.getJSONObject(i);
				userInfo = new UserInfo(jsonResult.getInt("id"),
						jsonResult.getString("login_name"),
						jsonResult.getString("name"), "",
						jsonResult.getString("long_cell_phone"),
						jsonResult.getString("brief_cell_phone"), "", "",
						jsonResult.getString("class_name"));
				userInfo.setQq(jsonResult.getString("qq"));
				userInfo.setWechant(jsonResult.getString("wechat"));
				userInfo.setSex(jsonResult.getString("sex"));

				list.add(userInfo);

				Log.d("jsonToList", "getPublishTime = " + userInfo.getId());
			}

			return list.get(0);
		} catch (JSONException e) {
			e.printStackTrace();
			Log.d("debug", "json出问题");
		}
		return null;
	}

	/**
	 * @param userId
	 * @param key
	 * @param value
	 * @return
	 */
	public String commitUserInfo(String userId, String key, String value) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 4 + "");
		maps.put("userId", userId);
		maps.put("key", key);
		maps.put("value", value);

		String jsonStr = HttpUtils.httpPostString(BOConstant.GET_USER_INFO_URL,
				maps);

		Log.d("MessageBO-getNewData", "jsonStr = " + jsonStr);
		return jsonStr;
	}

	/**
	 * @param userId
	 * @param orignPassword
	 * @param newPassword
	 * @return
	 */
	public Integer changePassword(String userId, String orignPassword,
			String newPassword) {
		// 设置查询条件
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 5 + "");
		maps.put("userId", userId);
		maps.put("orignPassword", orignPassword);
		maps.put("newPassword", newPassword);
		String servletStr = HttpUtils.httpPostString(
				BOConstant.GET_USER_INFO_URL, maps);
		servletStr = servletStr.trim();
		Log.d("CheckUserLoginResult", "servletStr = " + servletStr);
		if (servletStr.length() < 10) {
			if (servletStr.equals("2"))
				return BOConstant.CHANGE_SUCCESS;
			else if (servletStr.equals("-1"))
				return BOConstant.PASSWORD_ERROR;
		}
		return BOConstant.REP_VAR_PARA_ERR;// 其他错误。
	}
}
