/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-11-23
 */
package scau.info.volunteertime.vo;

/**
 * @author 蔡超敏
 * 
 */
public class ActivityGroup {
	private int id;
	private String principalId;
	private String participators;

	/**
	 * @param id
	 * @param principalId
	 * @param participators
	 */
	public ActivityGroup(int id, String principalId, String participators) {
		super();
		this.id = id;
		this.principalId = principalId;
		this.participators = participators;
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
	 * @return the principalId
	 */
	public String getPrincipalId() {
		return principalId;
	}

	/**
	 * @param principalId
	 *            the principalId to set
	 */
	public void setPrincipalId(String principalId) {
		this.principalId = principalId;
	}

	/**
	 * @return the participators
	 */
	public String getParticipators() {
		return participators;
	}

	/**
	 * @param participators
	 *            the participators to set
	 */
	public void setParticipators(String participators) {
		this.participators = participators;
	}

}
