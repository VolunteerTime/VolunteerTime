package scau.info.volunteertime.activity.settings;

import scau.info.volunteertime.R;
import scau.info.volunteertime.business.MessageBO;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

public class MessagesService extends Service {
	// 获取消息线程
	private Messagethread messagethread = null;

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
	// private Pagination<SystemMessage> systemMessagesPagination;

	// 获取九九头条线程
	// private Headlinethread headlinethread;

	// 九九头条
	// private HeadlineBO headlineBO;

	// 点击查看
	private Intent headlineintent = null;
	private PendingIntent headlinependingintent = null;

	// 通知栏消息
	private Notification headlinenotification = null;

	// 休息时间
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
			messagenotification.defaults = Notification.DEFAULT_SOUND;
			messagenotificatiomanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

			// messageintent = new Intent(this, MyMessagesActivity.class);
			messagependingintent = PendingIntent.getActivity(this, 0,
					messageintent, 0);

			messagesBO = new MessageBO();
			// sharedPreferences = getSharedPreferences(
			// Login.SHARE_USERINFO, Context.MODE_PRIVATE);
			Log.d("MessagesService", "初始化");

			// 开启线程
			messagethread = new Messagethread();
			messagethread.isrunning = true;
			messagethread.start();
			Log.d("MessagesService", "开启信息线程");

			// 九九头条
			// headlineBO = new HeadlineBO();

			// 初始化
			headlinenotification = new Notification();
			headlinenotification.icon = R.drawable.coupon_temp;
			// headlinenotification.tickerText = "新消息";
			headlinenotification.defaults = Notification.DEFAULT_SOUND;

			// headlineintent = new Intent(this, ShowArticleActivity.class);
			// 开启线程
			// headlinethread = new Headlinethread();
			// headlinethread.isrunning = true;
			// headlinethread.start();
			Log.d("MessagesService", "开启九九头条线程");

			sleepTime = 600000;

		}

		return super.onStartCommand(intent, flags, startid);
	}

	/**
	 * 从服务器端获取消息
	 * 
	 */
	class Messagethread extends Thread {
		public boolean isrunning = true;

		public void run() {
			while (isrunning) {
				try {
					// 休息1分钟
					Thread.sleep(sleepTime);
					int status = sharedPreferences.getInt("PUSH_STATUS", 1);
					// int status = sharedPreferences.getInt("PUSH_STATUS",
					// SlideSwitch.SWITCH_OFF);
					Log.d("messagethread", "status= " + status);
					if (status == 2) {
						// 获取服务器消息
						// String servermessage = getservermessage();
						String servermessage;
						// if (servermessage != null &&
						// !"".equals(servermessage)) {
						// // 更新通知栏
						// messagenotification.setLatestEventInfo(
						// MessagesService.this, "新消息", "ding9!"
						// + servermessage,
						// messagependingintent);
						// messagenotificatiomanager.notify(
						// messagenotificationid, messagenotification);
						// // 每次通知完，通知id递增一下，避免消息覆盖掉
						// messagenotificationid++;
						// }
					} else {
						Log.d("messagethread", "stop");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 从服务器端获取九九头条
	 * 
	 */
	// class Headlinethread extends Thread {
	// public boolean isrunning = true;
	//
	// public void run() {
	// while (isrunning) {
	// try {
	// // 休息1分钟
	// Thread.sleep(sleepTime);
	// int status = sharedPreferences.getInt("PUSH_STATUS",
	// SlideSwitch.SWITCH_OFF);
	// Log.d("messagethread", "status= " + status);
	// if (status == SlideSwitch.SWITCH_ON) {
	// // 获取九九头条消息
	// Article article = getArticleFromServer();
	//
	// if (article != null) {
	// // 更新通知栏
	// Bundle mBundle = new Bundle();
	// mBundle.putSerializable(
	// ShowArticleActivity.SER_KEY, article);
	// headlineintent.putExtras(mBundle);
	//
	// headlinependingintent = PendingIntent.getActivity(
	// MessagesService.this, 0, headlineintent, 0);
	//
	// headlinenotification.setLatestEventInfo(
	// MessagesService.this, "九九头条",
	// article.getTitle(), headlinependingintent);
	// messagenotificatiomanager
	// .notify(messagenotificationid,
	// headlinenotification);
	// // 每次通知完，通知id递增一下，避免消息覆盖掉
	// messagenotificationid++;
	// }
	// } else {
	// Log.d("headlinethread", "stop");
	// }
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	// /**
	// * @return Article
	// */
	// private Article getArticleFromServer() {
	// ArrayList<Article> articles = headlineBO.getTopHeadLine(1);
	// if (articles != null && articles.size() != 0) {
	// Article article = articles.get(0);
	// int articleId = article.getArticleId();
	// boolean isHave = sharedPreferences.getBoolean("ARTICLE"
	// + articleId, false);
	//
	// Log.d("Headlinethread-getArticleFromServer", "articleId= "
	// + articleId + " : isHave= " + isHave);
	//
	// if (isHave)// 如果已经保存了的就不再显示
	// return null;
	//
	// sharedPreferences.edit()
	// .putBoolean("ARTICLE" + articleId, true).commit();// 保存记录
	// return article;
	// }
	// return null;
	// }
	// }
	//
	// @Override
	// public void onDestroy() {
	// messagethread.isrunning = false;
	// headlinethread.isrunning = false;
	// super.onDestroy();
	// }
	//
	// /**
	// * 这里以此方法为服务器demo，仅作示例
	// *
	// * @return 返回服务器要推送的消息，否则如果为空的话，不推送
	// */
	// public String getservermessage() {
	// int userType = sharedPreferences.getInt(
	// CustomerLoginFragment.SHARE_USERCLASS, 1);
	// Log.d("MessagesService-getservermessage", "user_type= " + userType);
	//
	// try {
	// systemMessagesPagination = messagesBO.getMessageByUserId(userType,
	// 1, 1);
	// } catch (Exception ex) {
	// Log.d("MessagesService-getservermessage", "获取数据出错");
	// return null;
	// }
	// SystemMessage message = systemMessagesPagination.getRecords().get(0);
	// if (message == null || message.equals(""))
	// return null;
	//
	// int messageId = message.getMessag_id();
	// boolean isHave = sharedPreferences.getBoolean("MESSAGE" + messageId,
	// false);
	//
	// Log.d("MessagesService-getservermessage", "messageId= " + messageId
	// + " : isHave= " + isHave);
	//
	// if (isHave)// 如果已经保存了的就不再显示
	// return null;
	//
	// sharedPreferences.edit().putBoolean("MESSAGE" + messageId, true)
	// .commit();// 保存记录
	//
	// return message.getContent();
	// }
}
