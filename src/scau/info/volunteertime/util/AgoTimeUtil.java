/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-11-19
 */
package scau.info.volunteertime.util;

import cn.trinea.android.common.util.TimeUtils;

/**
 * @author 蔡超敏
 * 
 */
public class AgoTimeUtil extends TimeUtils {
	public static String getTimeAgoFromCurren(long timeInMillis) {
		long tempTime;
		tempTime = getCurrentTimeInLong() - timeInMillis;
		if (tempTime < 60000) {// <1m
			return "刚刚";
		} else if (tempTime > 60000 && tempTime < 3600000) {// 1m~1h
			return String.valueOf(tempTime / 60000) + "分钟前";
		} else if (tempTime > 3600000 && tempTime < 86400000) {// 1h~1d
			return String.valueOf(tempTime / 3600000) + "小时前";
		} else if (tempTime > 86400000 && tempTime < 1728000000) {// 1d~0.5y
			return String.valueOf(tempTime / 86400000) + "天前";
		} else {
			return TimeUtils.getTime(timeInMillis);
		}

	}
}
