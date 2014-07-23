/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-7-23
 */
package scau.info.volunteertime.vo;

import java.sql.Date;

/**
 * �ɹ�չʾ��VO
 * 
 * @author �̳���
 * 
 */
public class Result {

	private int id;// ��ʶid

	private String title;// ��Ŀ
	private String content;// ����
	private String image;// ͼƬ
	private String editor;// ����
	private Date publishTime;// ����ʱ��

	/**
	 * @param id
	 * @param title
	 * @param content
	 * @param image
	 * @param editor
	 * @param publishTime
	 */
	public Result(int id, String title, String content, String image,
			String editor, Date publishTime) {
		super();
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
			Date publishTime) {
		super();
		this.title = title;
		this.content = content;
		this.image = image;
		this.editor = editor;
		this.publishTime = publishTime;
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
	public Date getPublishTime() {
		return publishTime;
	}

	/**
	 * @param publishTime
	 *            the publishTime to set
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

}
