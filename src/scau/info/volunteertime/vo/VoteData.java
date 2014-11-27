/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-8-14
 */

package scau.info.volunteertime.vo;

import java.util.ArrayList;

/**
 * @author 蔡超敏
 * 
 */
public class VoteData implements VolunteertimeData {

	private int id; // 投票编号
	private boolean single; // 单选是true，复选是false
	private String title; // 评论的主题
	private ArrayList<String> choice; // 投票选项
	private ArrayList<Integer> votes; // 对应的投票结果
	private boolean checked = false; // 是否已经选择过了，从本地数据库获得,如果
	public boolean isChange = false; // 判断是否按下了按键，如果按下了那么会变成true，变成true画面会改变，但很快又会变成false
	public long date; // 更新时间

	public long endTime;

	public String userIds;
	public String choiceStr;
	public String voteStr;

	private int type;

	
	
	/**
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the choiceStr
	 */
	public String getChoiceStr() {
		return choiceStr;
	}

	/**
	 * @param choiceStr
	 *            the choiceStr to set
	 */
	public void setChoiceStr(String choiceStr) {
		this.choiceStr = choiceStr;
	}

	/**
	 * @return the voteStr
	 */
	public String getVoteStr() {
		return voteStr;
	}

	/**
	 * @param voteStr
	 *            the voteStr to set
	 */
	public void setVoteStr(String voteStr) {
		this.voteStr = voteStr;
	}

	/**
	 * @return the isChange
	 */
	public boolean isChange() {
		return isChange;
	}

	/**
	 * @param isChange
	 *            the isChange to set
	 */
	public void setChange(boolean isChange) {
		this.isChange = isChange;
	}

	/**
	 * @return the userIds
	 */
	public String getUserIds() {
		return userIds;
	}

	/**
	 * @param userIds
	 *            the userIds to set
	 */
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean getChecked() {
		return checked;
	}

	public void changeChecked() {
		checked = !checked;
	}

	public int getId() {
		return id;
	}

	public void setId(int num) {
		id = num;
	}

	public boolean getSingle() {
		return single;
	}

	public void setSingle(boolean t) {
		single = t;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String t) {
		title = t;
	}

	public ArrayList<String> getChoice() {
		return choice;
	}

	public void setChoice(ArrayList<String> c) {
		choice = c;
	}

	public ArrayList<Integer> getVotes() {
		return votes;
	}

	public void setVotes(ArrayList<Integer> v) {
		votes = v;
	}

	@Override
	public long getDate() {
		return date;
	}

	public boolean equals(Object o) {
		if (o instanceof ActivityDate) {
			if (((VoteData) o).getId() == this.getId()) {
				return true;
			}
		}
		return super.equals(o);
	}

	public int setDate() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see scau.info.volunteertime.vo.VolunteertimeData#setDate(long)
	 */
	@Override
	public void setDate(long date) {
		this.date = date;

	}

}
