/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author 蔡超敏
 * 
 */
public class NetworkStateUtil {

	/**
	 * ��������Ƿ����
	 * 
	 * @param context
	 * @return true,���� ; false,������
	 */

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connect = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connect == null)
			return false;
		NetworkInfo netinfo = connect.getActiveNetworkInfo();
		if (netinfo == null)
			return false;
		if (netinfo.isConnected())
			return true;
		return false;
	}

	/**
	 * �Ƿ�����wifi����
	 * 
	 * @param context
	 * @return
	 */

	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * �Ƿ������ƶ�����
	 * 
	 * @param context
	 * @return
	 */

	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * ��ȡ��������
	 * 
	 * @param context
	 * @return TYPE_MOBILE,�ƶ����磻 TYPE_WIFI,wifi���磻 TYPE_WIMAX,��������
	 *         TYPE_ETHERNET,��̫���� TYPE_BLUETOOTH������
	 */

	public static int getConnectedType(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
				return mNetworkInfo.getType();
			}
		}
		return -1;
	}

}
