/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.application;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import cn.trinea.android.common.entity.FailedReason;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.service.impl.ImageMemoryCache.OnImageCallbackListener;

/**
 * @author 蔡超敏
 * 
 */
public class Ding9App extends Application {
	/** ���建��ͻص��ӿ� */
	public static final String TAG_CACHE = "image_cache";// �����ʶ
	/** DEFAULT_CACHE_FOLDER��ͼƬ������Ŀ¼ */
	/*
	 * public static final String DEFAULT_CACHE_FOLDER = new StringBuilder()
	 * .append
	 * (Environment.getExternalStorageDirectory().getAbsolutePath()).append
	 * (File.separator)
	 * .append("Ding9").append(File.separator).append("CacheTest"
	 * ).append(File.separator) .append("ImageCache").toString();
	 */
	public static String DEFAULT_CACHE_FOLDER;

	public static int CLASS_NORMAL = 1;
	public static int CLASS_MERCHANT = 2;

	/**
	 * �����û�����
	 */
	private int userClass = 0;

	/**
	 * �û�id
	 */
	private int userId;

	private String username;

	private String logoImageUrl;

	public int getUserClass() {
		return userClass;
	}

	public void setUserClass(int userClass) {
		this.userClass = userClass;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLogoImageUrl() {
		return logoImageUrl;
	}

	public void setLogoImageUrl(String logoImageUrl) {
		this.logoImageUrl = logoImageUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		IMAGE_CACHE.initData(this, TAG_CACHE);// ��ʼ������
		DEFAULT_CACHE_FOLDER = getApplicationContext().getCacheDir()
				.getAbsolutePath();
		IMAGE_CACHE.setCacheFolder(DEFAULT_CACHE_FOLDER);// ���û��汣��Ŀ¼
		IMAGE_CACHE.setContext(getApplicationContext());// ���������粻��ȡ
		// IMAGE_CACHE.setAllowedNetworkTypes(int
		// allowedNetworkTypes);//����������������ͣ���ѡ��PreloadDataCache#NETWORK_MOBILE��PreloadDataCache#NETWORK_WIFI�����߶�����Ĭ�����߶�����
	}

	public static final ImageCache IMAGE_CACHE = new ImageCache(128, 512);
	/** ���建��Ļص��ӿڣ���ȡǰ����ȡ�ɹ���ʧ�ܵĻص��ӿڣ� */
	static {
		/** init icon cache **/
		OnImageCallbackListener imageCallBack = new OnImageCallbackListener() {

			/**
			 * callback function after get image successfully, run on ui thread
			 * 
			 * @param imageUrl
			 *            imageUrl
			 * @param loadedImage
			 *            bitmap
			 * @param view
			 *            view need the image
			 * @param isInCache
			 *            whether already in cache or got realtime
			 */
			@Override
			public void onGetSuccess(String imageUrl, Bitmap loadedImage,
					View view, boolean isInCache) {
				if (view != null && loadedImage != null) {
					ImageView imageView = (ImageView) view;
					imageView.setImageBitmap(loadedImage);
					// first time show with animation
					if (!isInCache) {// ��ʱ����ʱ
						imageView.startAnimation(getInAlphaAnimation(100));
					}

					// ���ڴ˴�˳�����ImageView����
					// imageView.setAdjustViewBounds(true);
					// imageView.setScaleType(ScaleType.CENTER);
				}
			}

			/**
			 * callback function before get image, run on ui thread
			 * 
			 * @param imageUrl
			 *            imageUrl
			 * @param view
			 *            view need the image
			 */
			@Override
			public void onPreGet(String imageUrl, View view) {
				// Log.e(TAG_CACHE, "pre get image");
			}

			/**
			 * callback function after get image failed, run on ui thread
			 * 
			 * @param imageUrl
			 *            imageUrl
			 * @param loadedImage
			 *            bitmap
			 * @param view
			 *            view need the image
			 * @param failedReason
			 *            failed reason for get image
			 */
			@Override
			public void onGetFailed(String imageUrl, Bitmap loadedImage,
					View view, FailedReason failedReason) {
				Log.e(TAG_CACHE,
						new StringBuilder(128).append("get image ")
								.append(imageUrl)
								.append(" error, failed type is: ")
								.append(failedReason.getFailedType())
								.append(", failed reason is: ")
								.append(failedReason.getCause().getMessage())
								.toString());
			}

			@Override
			public void onGetNotInCache(String imageUrl, View view) {
				if (view != null && view instanceof ImageView) {
					// ((ImageView)
					// view).setImageResource(R.drawable.navigation_refresh);
				}
			}
		};
		IMAGE_CACHE.setOnImageCallbackListener(imageCallBack);
		IMAGE_CACHE.setHttpReadTimeOut(10000);
		/**
		 * close connection, default is connect keep-alive to reuse connection.
		 * if image is from different server, you can set this
		 */
		// IMAGE_CACHE.setRequestProperty("Connection", "false");
	}

	/**
	 * ��������������ͼƬ��ʾ�ĵ��붯��
	 * 
	 * @param durationMillis
	 *            ����ʱ��
	 * @return
	 */

	public static AlphaAnimation getInAlphaAnimation(long durationMillis) {
		AlphaAnimation inAlphaAnimation = new AlphaAnimation(0, 1);
		inAlphaAnimation.setDuration(durationMillis);
		return inAlphaAnimation;
	}
}
