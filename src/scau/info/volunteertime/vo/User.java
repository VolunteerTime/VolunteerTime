/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-7-15
 */
package scau.info.volunteertime.vo;

/**
 * @author �̳���
 * 
 */
public class User {
	private int id;
	private String userId;
	private String password;

	/**
	 * ����һ�����췽��
	 * 
	 * @param id
	 * @param userId
	 * @param password
	 */
	public User(int id, String userId, String password) {
		super();
		this.id = id;
		this.userId = userId;
		this.password = password;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
