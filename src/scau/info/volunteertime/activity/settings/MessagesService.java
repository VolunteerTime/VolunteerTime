package scau.info.volunteertime.activity.settings;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.LoadActivity;
import scau.info.volunteertime.activity.manageactivity.ShowMessageActivity;
import scau.info.volunteertime.business.MessageBO;
import scau.info.volunteertime.vo.Message;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MessagesService extends Service {
	// 获取消息线程
	private messagethread messagethread = null;

	// 点击查看
	private Intent messageintent = null;
	private PendingIntent messagependingintent = null;

	// 通知栏消息
	private int messagenotificationid = 1000;
	private Notification messagenotification = null;
	private NotificationManager messagenotificatiomanager = null;

	// 系统消息
	private MessageBO messagesBO;
	private SharedPreferences sharedPreferences;

	private int sleepTime;

	public MessagesService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	private boolean isOnlyOne;

	@Override
	public void onCreate() {
		isOnlyOne = true;
		sleepTime = 10000;
		Log.d("MessagesService-onCreate", "isOnlyOne= " + isOnlyOne);
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startid) {
		Log.d("MessagesService-onStartCommand", "isOnlyOne= " + isOnlyOne);
		if (isOnlyOne) {
			// 初始化
			messagenotification = new Notification();
			messagenotification.icon = R.drawable.feed_back;
			messagenotification.tickerText = "新消息";
			messagenotification.flags = Notification.FLAG_AUTO_CANCEL;// 点击后去掉
			messagenotification.defaults = Notification.DEFAULT_SOUND;
			messagenotificatiomanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

			messagesBO = new MessageBO();
			sharedPreferences = getSharedPreferences(
					LoadActivity.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
			Log.d("MessagesService", "初始化");

			// 开启线程
			messagethread = new messagethread();
			messagethread.isrunning = true;
			messagethread.start();
			Log.d("MessagesService", "开启线程");
		}

		return super.onStartCommand(intent, flags, startid);
	}

	/**
	 * 从服务器端获取消息
	 * 
	 */
	class messagethread extends Thread {
		public boolean isrunning = true;

		public void run() {
			while (isrunning) {
				try {
					Thread.sleep(sleepTime);
					int status = sharedPreferences.getInt("PUSH_STATUS",
							SlideSwitch.SWITCH_OFF);
					Log.d("messagethread", "status= " + status);
					if (status == SlideSwitch.SWITCH_ON) {
						// 获取服务器消息
						Message servermessage = getservermessage();
						if (servermessage != null) {
							// 更新通知栏
							Log.d("messagethread", "servermessage.getId() = "
									+ servermessage.getId());
							messageintent = new Intent(MessagesService.this,
									ShowMessageActivity.class);
							Bundle mBundle = new Bundle();
							mBundle.putSerializable(
									ShowMessageActivity.SER_KEY, servermessage);
							messageintent.putExtras(mBundle);
							messagependingintent = PendingIntent.getActivity(
									MessagesService.this,
									messagenotificationid, messageintent, 0);
							messagenotification.setLatestEventInfo(
									MessagesService.this,
									servermessage.getLaunch_user_id(),
									servermessage.getTitle(),
									messagependingintent);
							messagenotificatiomanager.notify(
									messagenotificationid, messagenotification);
							// 每次通知完，通知id递增一下，避免消息覆盖掉
							messagenotificationid++;
						}
					} else {
						Log.d("messagethread", "stop");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onDestroy() {
		messagethread.isrunning = false;
		super.onDestroy();
	}

	/**
	 * 这里以此方法为服务器demo，仅作示例
	 * 
	 * @return 返回服务器要推送的消息，否则如果为空的话，不推送
	 */
	public Message getservermessage() {
		String userNameValue = sharedPreferences.getString(
				LoadActivity.SHARE_USERNAME, "").trim();
		String passwordValue = sharedPreferences.getString(
				LoadActivity.SHARE_PASSWORD, "").trim();
		Message message = messagesBO
				.getNewMessage(userNameValue, passwordValue);
		if (message != null) {
			toSaveMessage(message);
			messagesBO.updateSent(message.getId());
		}
		return message;
	}

	/**
	 * @param message
	 */
	private void toSaveMessage(Message message) {
		SQLiteDatabase db = openOrCreateDatabase("volunteertimedatabase.db",
				Context.MODE_PRIVATE, null);
		db.execSQL(
				"REPLACE INTO messages(id, receive_user_id , launch_user_id , title , content  , launch_time , is_send) VALUES(?,?,?,?,?,?,?)",
				new Object[] { message.getId(), message.getReceive_user_id(),
						message.getLaunch_user_id(), message.getTitle(),
						message.getContent(), message.getLaunch_time(),
						message.getIs_send() });

		db.close();
	}

}
