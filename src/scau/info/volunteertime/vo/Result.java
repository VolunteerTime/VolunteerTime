/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-23
 */
package scau.info.volunteertime.vo;

import java.util.List;

/**
 * �ɹ�չʾ��VO
 * 
 * @author 蔡超敏
 * 
 */
public class Result implements VolunteertimeData {

	private int id;// id

	private String title;// 标题
	private String content;// 内容
	private String image;// 图片
	private String editor;// 作者
	private long publishTime;// 时间

	private int readNum;// 阅读数
	private int commentNum;// 评论数
	private String commentIds;// 评价id

	private List<String> images;

	public Result() {
	}

	/**
	 * @param id
	 */
	public Result(int id) {
		this.id = id;
	}

	/**
	 * @param id
	 * @param title
	 * @param content
	 * @param image
	 * @param editor
	 * @param publishTime
	 */
	public Result(int id, String title, String content, String image,
			String editor, long publishTime) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.image = image;
		this.editor = editor;
		this.publishTime = publishTime;
	}

	/**
	 * @param title
	 * @param content
	 * @param image
	 * @param editor
	 * @param publishTime
	 */
	public Result(String title, String content, String image, String editor,
			long publishTime) {
		this.title = title;
		this.content = content;
		this.image = image;
		this.editor = editor;
		this.publishTime = publishTime;
	}

	public void setImages(List<String> imageList) {
		this.images = imageList;
	}

	public List<String> getImages() {
		return images;
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
	 * @return the commentNum
	 */
	public int getCommentNum() {
		return commentNum;
	}

	/**
	 * @param commentNum
	 *            the commentNum to set
	 */
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

	/**
	 * @return the commentIds
	 */
	public String getCommentIds() {
		return commentIds;
	}

	/**
	 * @param commentIds
	 *            the commentIds to set
	 */
	public void setCommentIds(String commentIds) {
		this.commentIds = commentIds;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Result) {
			if (((Result) o).getId() == this.getId()) {
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
	public void setDate(long Date) {
		setPublishTime(Date);
	}

}
