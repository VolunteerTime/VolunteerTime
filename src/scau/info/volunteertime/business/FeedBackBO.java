/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-11-25
 */
package scau.info.volunteertime.business;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import cn.trinea.android.common.util.HttpUtils;

/**
 * @author 蔡超敏
 * 
 */
public class FeedBackBO {
	/**
	 * 发送反馈 返回 int型 成功则返回feed_back_id，失败返回0
	 * 
	 * @param content
	 * @param userId
	 * @param pubTime
	 */
	public int postFeedBack(String content, String userId) {
		Log.d("FeedBackBO-postFeedBack", "in");
		// 设置查询条件
		Map<String, String> maps = new HashMap<String, String>();

		maps.put("action_type", 0 + "");
		maps.put("content", content);
		maps.put("userId", userId);
		String jstrFeedBack = null;
		try {
			jstrFeedBack = HttpUtils.httpPostString(
					BOConstant.FEED_BACK_URL, maps);
		} catch (SocketTimeoutException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Log.d("postFeedBack", "jstrFeedBack = " + jstrFeedBack);

		int temp = 0;
		if (jstrFeedBack != null && !jstrFeedBack.trim().equals("")) {
			try {
				temp = Integer.parseInt(jstrFeedBack.trim());// 去掉字符串前后的空格

			} catch (Exception e) {
				temp = 0;
				System.out.println("数字转换异常，信息：" + e.getMessage());
			}
		}
		Log.v("temp", String.valueOf(temp));
		// int temp=Integer.parseInt(jstrShowThings);
		return temp;
	}
}
