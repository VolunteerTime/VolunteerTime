package scau.info.volunteertime.activity.settings;

import java.io.File;
import java.text.DecimalFormat;

import scau.info.volunteertime.R;
import scau.info.volunteertime.business.VersionBO;
import scau.info.volunteertime.util.NetworkStateUtil;
import scau.info.volunteertime.vo.Version;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.trinea.android.common.util.DownloadManagerPro;
import cn.trinea.android.common.util.PreferencesUtils;
import cn.trinea.android.common.util.ToastUtils;

public class ApkUpdateManager extends ActionBarActivity {
	public static final String DOWNLOAD_FOLDER_NAME = "volunteerTimedownload";// 保存文件夹名称
	public static final String KEY_NAME_DOWNLOAD_ID = "downloadId";
	public static final String VERSION_CODE = "versionCode";
	public static final String VERSION_INFO = "versionInfo";
	public static String APK_URL;
	public static String DOWNLOAD_FILE_NAME;

	private DownloadManager downloadManager;
	private DownloadManagerPro downloadManagerPro;
	private long downloadId = 0;

	private Button downloadButton;
	private ProgressBar downloadProgress;
	private TextView downloadTip;
	private TextView downloadSize;
	private TextView downloadPrecent;
	private ImageButton downloadCancel;

	private Context context;
	private MyHandler handler;

	private DownloadChangeObserver downloadObserver;// 下载进度监听器
	private CompleteReceiver completeReceiver;// 下载完成监听器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 隐藏标题
		setContentView(R.layout.activity_apk_update_manager);

		context = getApplicationContext();
		handler = new MyHandler();
		downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
		downloadManagerPro = new DownloadManagerPro(downloadManager);
		downloadObserver = new DownloadChangeObserver();
		completeReceiver = new CompleteReceiver();
		registerReceiver(completeReceiver, new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE));// 注册下载完成监听器

		Intent intent = getIntent();
		if (intent != null) {
			/**
			 * below android 4.2, intent.getDataString() is
			 * file:///storage/sdcard1/Trinea/MeLiShuo.apk<br/>
			 * equal or above 4.2 intent.getDataString() is
			 * content://media/external/file/29669
			 */
			Bundle versionInfo = intent.getExtras();
			Version version = (Version) versionInfo
					.getSerializable(VERSION_INFO);
			APK_URL = version.getDownloadUrl();
			DOWNLOAD_FILE_NAME = version.getVersionName() + ".apk";
		}
		initView();
		initData();
		try {
			startDownload();
		} catch (Exception e) {
			Uri uri = Uri.parse("market://details?id=" + getPackageName());
			Intent toShopIntent = new Intent(Intent.ACTION_VIEW, uri);
			toShopIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(toShopIntent);
			finish();
			e.printStackTrace();
		}

	}

	private void startDownload() {
		// TODO Auto-generated method stub
		// File folder = new File(DOWNLOAD_FOLDER_NAME);
		// 检测folder目录是否存在，apk将下载到folder目录下，不存在则创建
		File folder = Environment
				.getExternalStoragePublicDirectory(DOWNLOAD_FOLDER_NAME);
		if (!folder.exists() || !folder.isDirectory()) {
			folder.mkdirs();
		}
		// 检测下载的文件名是否已存在，存在需先删除
		String targetpath = new StringBuilder(Environment
				.getExternalStorageDirectory().getAbsolutePath())
				.append(File.separator).append(DOWNLOAD_FOLDER_NAME)
				.append(File.separator).append(DOWNLOAD_FILE_NAME).toString();
		File file = new File(targetpath);
		if (file.exists()) {
			// 删除
			file.delete();
		}

		// 路径配置完成，开始下载参数配置
		DownloadManager.Request request = new DownloadManager.Request(
				Uri.parse(APK_URL));
		request.setDestinationInExternalPublicDir(DOWNLOAD_FOLDER_NAME,
				DOWNLOAD_FILE_NAME);

		request.setTitle("志愿宝");
		request.setDescription("青年志愿者的通信宝贝");
		// request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		request.setVisibleInDownloadsUi(false);
		// request.allowScanningByMediaScanner();
		// request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
		// request.setShowRunningNotification(false);
		// request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
		request.setMimeType("application/com.volunteerTime.download.file");
		Log.v("download", "start");
		downloadId = downloadManager.enqueue(request);
		/** save download id to preferences **/
		PreferencesUtils.putLong(context, KEY_NAME_DOWNLOAD_ID, downloadId);
		updateView();
	}

	private void initView() {
		downloadButton = (Button) findViewById(R.id.download_button);
		downloadCancel = (ImageButton) findViewById(R.id.download_cancel);
		downloadProgress = (ProgressBar) findViewById(R.id.download_progress);
		downloadTip = (TextView) findViewById(R.id.download_tip);
		downloadTip
				.setText("安装文件会保存到sdcard，路径是："
						+ Environment
								.getExternalStoragePublicDirectory(DOWNLOAD_FOLDER_NAME));
		downloadSize = (TextView) findViewById(R.id.download_size);
		downloadPrecent = (TextView) findViewById(R.id.download_precent);
	}

	private void initData() {
		/**
		 * get download id from preferences.<br/>
		 * if download id bigger than 0, means it has been downloaded, then
		 * query status and show right text;
		 */
		downloadId = PreferencesUtils.getLong(context, KEY_NAME_DOWNLOAD_ID);
		updateView();
		downloadButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					startDownload();
				} catch (Exception e) {
					Uri uri = Uri.parse("market://details?id="
							+ getPackageName());
					Intent toShopIntent = new Intent(Intent.ACTION_VIEW, uri);
					toShopIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(toShopIntent);
					finish();
					e.printStackTrace();
				}
			}
		});
		downloadCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				downloadManager.remove(downloadId);
				updateView();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		/** observer download change **/
		getContentResolver().registerContentObserver(
				DownloadManagerPro.CONTENT_URI, true, downloadObserver);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getContentResolver().unregisterContentObserver(downloadObserver);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(completeReceiver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.apk_update_manager, menu);
		return true;
	}

	/**
	 * 安装filePath指向的应用
	 * 
	 * @param context
	 * @param filePath
	 * @return
	 */

	public static boolean install(Context context, String filePath) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		File file = new File(filePath);
		if (file != null && file.length() > 0 && file.exists() && file.isFile()) {
			i.setDataAndType(Uri.parse("file://" + filePath),
					"application/vnd.android.package-archive");
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
			Log.v("true", "yes");
			return true;
		}
		Log.v("fileLength", String.valueOf(file.length()));
		Log.v("fileExist", String.valueOf(file.exists()));
		Log.v("isfile", String.valueOf(file.isFile()));
		return false;
	}

	/**
	 * 下载进度状态监听及查询
	 * 
	 * @author 谢文斌
	 * @since 1.0
	 */

	class DownloadChangeObserver extends ContentObserver {

		public DownloadChangeObserver() {
			super(handler);
		}

		/*
		 * 下载进度变化
		 */
		@Override
		public void onChange(boolean selfChange) {
			Log.v("download", "ing");
			updateView();
		}

	}

	/**
	 * 下载完成监听
	 * 
	 * @author 谢文斌
	 * @since 1.0
	 */

	class CompleteReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			/**
			 * 获得下载成功的任务的downloadId,若得到的id与我们正在下载的任务id相同且下载状态是successful，则安装应用
			 **/
			long completeDownloadId = intent.getLongExtra(
					DownloadManager.EXTRA_DOWNLOAD_ID, -1);
			if (completeDownloadId == downloadId) {
				Log.v("download", "done");
				initData();
				updateView();
				// if download successful, install apk
				if (downloadManagerPro.getStatusById(downloadId) == DownloadManager.STATUS_SUCCESSFUL) {
					String apkFilePath = // 安装路径
					new StringBuilder(Environment.getExternalStorageDirectory()
							.getAbsolutePath()).append(File.separator)
							.append(DOWNLOAD_FOLDER_NAME)
							.append(File.separator).append(DOWNLOAD_FILE_NAME)
							.toString();
					install(context, apkFilePath);
					Log.v("install", "ok");
				}
			}
		}
	}

	/**
	 * 通知Handler更新UI
	 */
	public void updateView() {
		int[] bytesAndStatus = downloadManagerPro.getBytesAndStatus(downloadId);
		Log.v("reason",
				String.valueOf(downloadManagerPro.getReason(downloadId)));
		handler.sendMessage(handler.obtainMessage(0, bytesAndStatus[0],
				bytesAndStatus[1], bytesAndStatus[2]));
	}

	private class MyHandler extends Handler {
		// 设置进度条和状态等
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case 0:
				int status = (Integer) msg.obj;
				if (isDownloading(status)) {// 正在下载

					downloadProgress.setVisibility(View.VISIBLE);
					downloadProgress.setMax(0);
					downloadProgress.setProgress(0);
					downloadButton.setVisibility(View.GONE);
					downloadSize.setVisibility(View.VISIBLE);
					downloadPrecent.setVisibility(View.VISIBLE);
					downloadCancel.setVisibility(View.VISIBLE);

					if (msg.arg2 < 0) {

						downloadProgress.setIndeterminate(true);
						downloadPrecent.setText("0%");
						downloadSize.setText("0M/0M");

					} else {

						downloadProgress.setIndeterminate(false);
						downloadProgress.setMax(msg.arg2);
						downloadProgress.setProgress(msg.arg1);
						downloadPrecent.setText(getNotiPercent(msg.arg1,
								msg.arg2));
						downloadSize.setText(getAppSize(msg.arg1) + "/"
								+ getAppSize(msg.arg2));

					}
				} else {// 非正在下载

					downloadProgress.setVisibility(View.GONE);
					downloadProgress.setMax(0);
					downloadProgress.setProgress(0);
					downloadButton.setVisibility(View.VISIBLE);
					downloadSize.setVisibility(View.GONE);
					downloadPrecent.setVisibility(View.GONE);
					downloadCancel.setVisibility(View.GONE);

					if (status == DownloadManager.STATUS_FAILED) {// 下载失败
						downloadButton.setText("下载失败，点击重新下载");
					} else if (status == DownloadManager.STATUS_SUCCESSFUL) {// 下载成功
						downloadButton.setText("下载成功，点击再次下载");
					} else {
						downloadButton.setText("点击下载");
					}
				}
				break;
			}
		}
	}

	static final DecimalFormat DOUBLE_DECIMAL_FORMAT = new DecimalFormat("0.##");

	public static final int MB_2_BYTE = 1024 * 1024;
	public static final int KB_2_BYTE = 1024;

	/**
	 * long型 size 转换为具体大小字符串
	 * 
	 * @param size
	 * @return
	 */
	public static CharSequence getAppSize(long size) {
		if (size <= 0) {
			return "0M";
		}

		if (size >= MB_2_BYTE) {
			return new StringBuilder(16).append(
					DOUBLE_DECIMAL_FORMAT.format((double) size / MB_2_BYTE))
					.append("M");
		} else if (size >= KB_2_BYTE) {
			return new StringBuilder(16).append(
					DOUBLE_DECIMAL_FORMAT.format((double) size / KB_2_BYTE))
					.append("K");
		} else {
			return size + "B";
		}
	}

	public static String getNotiPercent(long progress, long max) {
		int rate = 0;
		if (progress <= 0 || max <= 0) {
			rate = 0;
		} else if (progress > max) {
			rate = 100;
		} else {
			rate = (int) ((double) progress / max * 100);
		}
		return new StringBuilder(16).append(rate).append("%").toString();
	}

	public static boolean isDownloading(int downloadManagerStatus) {
		return downloadManagerStatus == DownloadManager.STATUS_RUNNING
				|| downloadManagerStatus == DownloadManager.STATUS_PAUSED
				|| downloadManagerStatus == DownloadManager.STATUS_PENDING;
	}

	public static void autoCheckUpdate(final Context context) {
		new AutoGetServerVersionTask(context).execute();
	}

	public static void handCheckUpdate(final Context context) {
		new HandGetServerVersionTask(context).execute();
	}

	/**
	 * 获取本地版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "未知的版本";
		}
	}

	/**
	 * 获取本地版本号(内部识别号)
	 * 
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return pi.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 自动检测新版本，若存在新版本则提示
	 * 
	 * @author wenbin
	 * 
	 */
	private static class AutoGetServerVersionTask extends
			AsyncTask<Void, Void, Version> {

		private boolean isConnect;
		private Context context;
		private boolean isLatest;

		public AutoGetServerVersionTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			isLatest = true;
			super.onPreExecute();
		}

		@Override
		protected Version doInBackground(Void... params) {

			isConnect = NetworkStateUtil.isNetworkAvailable(context);// 获取连接状况
			if (!isConnect)// 无网络则取消任务
			{
				cancel(true);
			}
			if (isCancelled()) {

				return null;
			}

			Version serverVersion = new VersionBO().getLatestVersion();

			int serverVersionCode;
			try {
				serverVersionCode = serverVersion.getVersionCode();

				PackageManager pm = context.getPackageManager();
				try {
					PackageInfo pinfo = pm.getPackageInfo(
							context.getPackageName(),
							PackageManager.GET_CONFIGURATIONS);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				int localVersionCode = getVersionCode(context);
				Log.v("serverVersion", String.valueOf(serverVersionCode));
				Log.v("localVersionCode", String.valueOf(localVersionCode));
				if (serverVersionCode > localVersionCode) {// 若版本号不一致，则弹出对话框提示更新
					isLatest = false;
				}
			} catch (Exception e) {
				ToastUtils.show(context, "抱歉，版本解析错误");
				e.printStackTrace();
			}
			return serverVersion;
		}

		@Override
		protected void onPostExecute(final Version serverVersion) {

			if (!isLatest) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				AlertDialog alertDialog;
				alertDialog = builder
						.setTitle("发现新版本~")
						.setMessage(serverVersion.getVersionTip())
						.setPositiveButton("我要下载",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										Bundle versionInfo = new Bundle();
										versionInfo.putSerializable(
												VERSION_INFO, serverVersion);
										Intent toUpdateIntent = new Intent(
												context, ApkUpdateManager.class);
										toUpdateIntent.putExtras(versionInfo);
										context.startActivity(toUpdateIntent);
									}

								})
						.setNegativeButton("下次再说",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub

									}

								}).create();
				alertDialog.show();
			}
			super.onPostExecute(serverVersion);
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			// Toast.makeText(getBaseContext(), "哎呦，没网耶~",
			// Toast.LENGTH_SHORT).show();
			ToastUtils.show(context, "网络连接出现问题");
			super.onCancelled();
		}
	}

	/**
	 * 手动检测新版本时的检测任务，存在新版本则提示下载，否则提示已是最新
	 * 
	 * @author wenbin
	 * 
	 */
	private static class HandGetServerVersionTask extends
			AsyncTask<Void, Void, Version> {

		private boolean isConnect;
		private Context context;
		private boolean isLatest;

		public HandGetServerVersionTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			isLatest = true;
			super.onPreExecute();
		}

		@Override
		protected Version doInBackground(Void... params) {

			isConnect = NetworkStateUtil.isNetworkAvailable(context);// 获取连接状况
			if (!isConnect)// 无网络则取消任务
			{
				cancel(true);
			}
			if (isCancelled()) {

				return null;
			}

			Version serverVersion = new VersionBO().getLatestVersion();

			int serverVersionCode;
			try {
				serverVersionCode = serverVersion.getVersionCode();

				PackageManager pm = context.getPackageManager();
				try {
					PackageInfo pinfo = pm.getPackageInfo(
							context.getPackageName(),
							PackageManager.GET_CONFIGURATIONS);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				int localVersionCode = getVersionCode(context);
				Log.v("serverVersion", String.valueOf(serverVersionCode));
				Log.v("localVersionCode", String.valueOf(localVersionCode));
				if (serverVersionCode > localVersionCode) {// 若版本号不一致，则弹出对话框提示更新
					isLatest = false;
				}
			} catch (Exception e) {
				ToastUtils.show(context, "抱歉，版本解析错误");
				e.printStackTrace();
			}
			return serverVersion;
		}

		@Override
		protected void onPostExecute(final Version serverVersion) {

			if (!isLatest) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				AlertDialog alertDialog;
				alertDialog = builder
						.setTitle("发现新版本~")
						.setMessage(serverVersion.getVersionTip())
						.setPositiveButton("我要下载",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										Bundle versionInfo = new Bundle();
										versionInfo.putSerializable(
												VERSION_INFO, serverVersion);
										Intent toUpdateIntent = new Intent(
												context, ApkUpdateManager.class);
										toUpdateIntent.putExtras(versionInfo);
										context.startActivity(toUpdateIntent);
									}

								})
						.setNegativeButton("下次再说",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub

									}

								}).create();
				alertDialog.show();
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				AlertDialog alertDialog;
				alertDialog = builder.setTitle("已经是最新版了~").create();
				alertDialog.show();
			}
			super.onPostExecute(serverVersion);
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			// Toast.makeText(getBaseContext(), "哎呦，没网耶~",
			// Toast.LENGTH_SHORT).show();
			ToastUtils.show(context, "网络连接出现问题");
			super.onCancelled();
		}
	}

}
