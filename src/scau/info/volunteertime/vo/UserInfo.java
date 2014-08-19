/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.vo;

/**
 * @author 蔡超敏
 * 
 */
public class UserInfo {
	private int id;
	private String userId;// ѧ����
	private String userName;

	private String email;
	private String cellPhone;// ����
	private String briefPhone;// �̺�
	private String telephone;// �̶��绰
	private String address;// ��ַ
	private String gradeAndMajor;// �꼶��רҵ

	/**
	 * ����һ�����췽��
	 * 
	 * @param id
	 * @param userId
	 * @param userName
	 * @param email
	 * @param cellPhone
	 * @param briefPhone
	 * @param telephone
	 * @param address
	 * @param gradeAndMajor
	 */
	public UserInfo(int id, String userId, String userName, String email,
			String cellPhone, String briefPhone, String telephone,
			String address, String gradeAndMajor) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.cellPhone = cellPhone;
		this.briefPhone = briefPhone;
		this.telephone = telephone;
		this.address = address;
		this.gradeAndMajor = gradeAndMajor;
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the cellPhone
	 */
	public String getCellPhone() {
		return cellPhone;
	}

	/**
	 * @param cellPhone
	 *            the cellPhone to set
	 */
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	/**
	 * @return the briefPhone
	 */
	public String getBriefPhone() {
		return briefPhone;
	}

	/**
	 * @param briefPhone
	 *            the briefPhone to set
	 */
	public void setBriefPhone(String briefPhone) {
		this.briefPhone = briefPhone;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone
	 *            the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the gradeAndMajor
	 */
	public String getGradeAndMajor() {
		return gradeAndMajor;
	}

	/**
	 * @param gradeAndMajor
	 *            the gradeAndMajor to set
	 */
	public void setGradeAndMajor(String gradeAndMajor) {
		this.gradeAndMajor = gradeAndMajor;
	}

}
