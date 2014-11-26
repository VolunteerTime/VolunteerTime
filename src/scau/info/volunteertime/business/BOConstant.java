/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.business;

/**
 * @author 蔡超敏
 * 
 */
public class BOConstant {
	// 验证用户返回值
	// 大于0：登陆正确，返回id 0：账号密码为空 -1：密码不正确 -2：账号不存在
	public static final int REP_VAR_PARA_ERR = 0;// 帐号密码为空
	public static final int PASSWORD_ERROR = -1;// 密码不合法
	public static final int USER_NOT_EXIST = -2;// 用户不存在
	public static final int USER_NOT_ACTIVATED = -3;// 用户未激活
	public static final int USER_REGISTER_SUCCESS = 1;// 用户注册成功

	public static final String ROOT_URL = "http://192.168.253.1:8080";

	public static final String GET_NEW_RESULTS_DATA_URL = ROOT_URL
			+ "/VolunteerTimeWeb/VolunteerTimeResultExhibitionServlet";

	public static final String GET_NEW_ACTIVITIES_DATA_URL = ROOT_URL
			+ "/VolunteerTimeWeb/VolunteerTimeActivityCenterServlet";

	public static final int USER_HAS_EXIST = 10;
	public static final int REPASSWORD_ERROR = 11;
	public static final int REGISTER_SUCCESS = 12;
	public static final String GET_USER_INFO_URL = ROOT_URL
			+ "/VolunteerTimeWeb/VolunteerTimeUserInfoServlet";
	public static final String ANDROID_VERSION_URL = ROOT_URL
			+ "/VolunteerTimeWeb/VolunteerTimeVersionServlet";
	public static final String FEED_BACK_URL = ROOT_URL
			+ "/VolunteerTimeWeb/VolunteerTimeFeedBackServlet";
	public static final String MESSAGES_URL = ROOT_URL
			+ "/VolunteerTimeWeb/VolunteerTimeMessagesServlet";
	public static int CHANGE_SUCCESS = 2;

}
