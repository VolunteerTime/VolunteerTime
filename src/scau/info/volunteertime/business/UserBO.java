/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-7-15
 */
package scau.info.volunteertime.business;

import android.util.Log;

/**
 * @author �̳���
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
