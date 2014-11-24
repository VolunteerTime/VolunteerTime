/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-11-21
 */
package scau.info.volunteertime.vo;

/**
 * @author 蔡超敏
 * 
 */
public class ActivityDate implements VolunteertimeData {

	private int id;// id

	private String title;// 标题
	private String content;// 内容
	private String image;// 图片
	private String editor;// 作者
	private long publishTime;// 时间
	private long endTime; // 结束时间
	private int limitNum; // 限制人数
	private int readNum;// 阅读数
	private int groupId;// 小组id
	private int participatorsNum;

	private ActivityGroup activityGroup;

	/**
	 * @param id
	 */
	public ActivityDate(int id) {
		super();
		this.id = id;
	}

	/**
	 * @param id
	 * @param title
	 * @param content
	 * @param image
	 * @param editor
	 * @param publishTime
	 * @param endTime
	 * @param limitNum
	 * @param readNum
	 * @param groupId
	 * @param participatorsNum
	 */
	public ActivityDate(int id, String title, String content, String image,
			String editor, long publishTime, long endTime, int limitNum,
			int readNum, int groupId, int participatorsNum) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.image = image;
		this.editor = editor;
		this.publishTime = publishTime;
		this.endTime = endTime;
		this.limitNum = limitNum;
		this.readNum = readNum;
		this.groupId = groupId;
		this.participatorsNum = participatorsNum;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the editor
	 */
	public String getEditor() {
		return editor;
	}

	/**
	 * @param editor
	 *            the editor to set
	 */
	public void setEditor(String editor) {
		this.editor = editor;
	}

	/**
	 * @return the publishTime
	 */
	public long getPublishTime() {
		return publishTime;
	}

	/**
	 * @param publishTime
	 *            the publishTime to set
	 */
	public void setPublishTime(long publishTime) {
		this.publishTime = publishTime;
	}

	/**
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the limitNum
	 */
	public int getLimitNum() {
		return limitNum;
	}

	/**
	 * @param limitNum
	 *            the limitNum to set
	 */
	public void setLimitNum(int limitNum) {
		this.limitNum = limitNum;
	}

	/**
	 * @return the readNum
	 */
	public int getReadNum() {
		return readNum;
	}

	/**
	 * @param readNum
	 *            the readNum to set
	 */
	public void setReadNum(int readNum) {
		this.readNum = readNum;
	}

	/**
	 * @return the participatorsNum
	 */
	public int getParticipatorsNum() {
		return participatorsNum;
	}

	/**
	 * @param participatorsNum
	 *            the participatorsNum to set
	 */
	public void setParticipatorsNum(int participatorsNum) {
		this.participatorsNum = participatorsNum;
	}

	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the activityGroup
	 */
	public ActivityGroup getActivityGroup() {
		return activityGroup;
	}

	/**
	 * @param activityGroup
	 *            the activityGroup to set
	 */
	public void setActivityGroup(ActivityGroup activityGroup) {
		this.activityGroup = activityGroup;
	}

	public boolean equals(Object o) {
		if (o instanceof ActivityDate) {
			if (((ActivityDate) o).getId() == this.getId()) {
				return true;
			}
		}
		return super.equals(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see scau.info.volunteertime.vo.VolunteertimeData#getDate()
	 */
	@Override
	public long getDate() {
		return getPublishTime();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see scau.info.volunteertime.vo.VolunteertimeData#setDate(long)
	 */
	@Override
	public void setDate(long date) {
		setPublishTime(date);
	}

}
