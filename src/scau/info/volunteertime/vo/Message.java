/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-11-26
 */
package scau.info.volunteertime.vo;

/**
 * @author 蔡超敏
 * 
 */
public class Message implements VolunteertimeData {

	private int id;
	private String receive_user_id;
	private String launch_user_id;
	private String title;
	private String content;
	private long launch_time;
	private int is_send;

	/**
	 * @param id
	 * @param receive_user_id
	 * @param launch_user_id
	 * @param title
	 * @param content
	 * @param launch_time
	 * @param is_send
	 */
	public Message(int id, String receive_user_id, String launch_user_id,
			String title, String content, long launch_time, int is_send) {
		super();
		this.id = id;
		this.receive_user_id = receive_user_id;
		this.launch_user_id = launch_user_id;
		this.title = title;
		this.content = content;
		this.launch_time = launch_time;
		this.is_send = is_send;
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
	 * @return the receive_user_id
	 */
	public String getReceive_user_id() {
		return receive_user_id;
	}

	/**
	 * @param receive_user_id
	 *            the receive_user_id to set
	 */
	public void setReceive_user_id(String receive_user_id) {
		this.receive_user_id = receive_user_id;
	}

	/**
	 * @return the launch_user_id
	 */
	public String getLaunch_user_id() {
		return launch_user_id;
	}

	/**
	 * @param launch_user_id
	 *            the launch_user_id to set
	 */
	public void setLaunch_user_id(String launch_user_id) {
		this.launch_user_id = launch_user_id;
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
	 * @return the launch_time
	 */
	public long getLaunch_time() {
		return launch_time;
	}

	/**
	 * @param launch_time
	 *            the launch_time to set
	 */
	public void setLaunch_time(long launch_time) {
		this.launch_time = launch_time;
	}

	/**
	 * @return the is_send
	 */
	public int getIs_send() {
		return is_send;
	}

	/**
	 * @param is_send
	 *            the is_send to set
	 */
	public void setIs_send(int is_send) {
		this.is_send = is_send;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see scau.info.volunteertime.vo.VolunteertimeData#getDate()
	 */
	@Override
	public long getDate() {
		return getLaunch_time();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see scau.info.volunteertime.vo.VolunteertimeData#setDate(long)
	 */
	@Override
	public void setDate(long date) {
		setLaunch_time(date);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Result) {
			if (((Message) o).getId() == this.getId()) {
				return true;
			}
		}
		return super.equals(o);
	}

}
