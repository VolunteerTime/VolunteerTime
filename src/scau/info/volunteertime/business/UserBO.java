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
	 * ����0����½��ȷ������id 0���˺�����Ϊ�� -1�����벻��ȷ -2���˺Ų�����
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
		Log.d("CheckUserLoginResult", "©������������");
		return -1;
	}
	


}
