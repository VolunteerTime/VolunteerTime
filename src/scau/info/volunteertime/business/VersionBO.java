/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-11-25
 */
package scau.info.volunteertime.business;

import org.json.JSONObject;

import scau.info.volunteertime.vo.Version;
import android.util.Log;
import cn.trinea.android.common.util.HttpUtils;

/**
 * @author 蔡超敏
 *
 */
public class VersionBO {
	public Version getLatestVersion() {
	    Version version = new Version();
	    try {
	      Log.d("version_url", BOConstant.ANDROID_VERSION_URL);
	      String jstrAndrVersion = HttpUtils.httpGetString(BOConstant.ANDROID_VERSION_URL);

	      Log.d("jstr", jstrAndrVersion);


	      JSONObject jobjObjectVersion = new JSONObject(jstrAndrVersion); // 根据字符串转成JSONObject对象
	      // 将json字符串内容转成Version对象
	      version.setVersionName(jobjObjectVersion.getString("versionName"));
	      version.setVersionCode(jobjObjectVersion.getInt("versionCode"));
	      version.setVersionTip(jobjObjectVersion.getString("versionTip"));
	      version.setDownloadUrl(jobjObjectVersion.getString("downloadUrl"));

	    } catch (Exception e) {
	      e.printStackTrace();
	    }

	    Log.d("version_code", String.valueOf(version.getVersionCode()));
	    return version;

	  }

}
