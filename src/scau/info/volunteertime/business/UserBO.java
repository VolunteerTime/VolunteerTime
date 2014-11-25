/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.business;

import java.util.HashMap;
import java.util.Map;

import scau.info.volunteertime.vo.UserInfo;

import android.util.Log;
import android.widget.Toast;

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

}
