/**
 * Copyright (c) 广州顶九信息技术有限公司 2014 版权所有
 * 
 * 文件创建时间：2014-6-1
 */

package scau.info.volunteertime.vo;

import java.io.Serializable;


/**
 * android应用的版本类，不存入数据库中
 * 
 * @author zhangchao
 * @since 1.0
 */

public class Version implements Serializable{

  private static final long serialVersionUID = 1L;//表明实现序列化类的不同版本间的兼容性
  private String versionName;// 版本名称,用来比对版本,/WEB-INF/classes/
  // latest_version.properties文件中的version_name行
  private int versionCode;// 版本号，用来比对版本,/WEB-INF/classes/
  // latest_version.properties文件中的version_number行
  private String versionTip;// 该版本提示（或介绍）,/WEB-INF/classes/
  // latest_version.properties文件中的version_tip行
  private String downloadUrl;// 该版本下载地址, /WEB-INF/classes/

  // latest_version.properties文件中的download_url行,暂 取值为http://115.28.243.203/update/ding9android.apk
  public String getVersionName() {
    return versionName;
  }

  public void setVersionName(String versionName) {
    this.versionName = versionName;
  }


  public int getVersionCode() {
    return versionCode;
  }

  public void setVersionCode(int versionCode) {
    this.versionCode = versionCode;
  }

  public String getVersionTip() {
    return versionTip;
  }

  public void setVersionTip(String versionTip) {
    this.versionTip = versionTip;
  }

  public String getDownloadUrl() {
    return downloadUrl;
  }

  public void setDownloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
  }
}
