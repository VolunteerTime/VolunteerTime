/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.business;

import android.util.Log;

/**
 * @author 蔡超敏
 * 
 */
public class UserBO {

	/**
	 * 大于0：登陆正确，返回id 0：账号密码为空 -1：密码不正确 -2：账号不存在
	 * 
	 * @param userId
	 * @param password
	 * @return int
	 */
	public int CheckUserLoginResult(String userId, String password) {
		if ("201230560202".equals(userId) && "123".equals(password)) {
			return 1;
		} else if (!"123".equals(password)) {
			return -1;
		} else if (!"201230560202".equals(userId)) {
			return -2;
		} else if (userId == null || userId.equals("") || password == null
				|| password.equals("")) {
			return 0;
		}
		Log.d("CheckUserLoginResult", "漏了其他可能性");
		return -1;
	}

}
