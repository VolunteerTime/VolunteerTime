/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014��8��18��
 */
package scau.info.volunteertime.activity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.ViewConfiguration;
import android.view.Window;

/**
 * @author �̳���
 *
 */
public class BaseActionBarActivity extends ActionBarActivity{
	/* (non-Javadoc)
	 * @see android.app.Activity#onMenuOpened(int, android.view.Menu)
	 */
	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getOverflowMenu();
	}
	 public boolean onMenuOpened(int featureId, Menu menu) {  //ʹ���²˵�������������ʾͼ��
	        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {  
	            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {  
	                try {  
	                    Method m = menu.getClass().getDeclaredMethod(  
	                            "setOptionalIconsVisible", Boolean.TYPE);  
	                    m.setAccessible(true);  
	                    m.invoke(menu, true);  
	                } catch (Exception e) {  
	                }  
	            }  
	        }  
	        return super.onMenuOpened(featureId, menu);  
	    }  
	 
		private void getOverflowMenu() { //ÿ��С�˵���ļ�����
			try {
				ViewConfiguration config = ViewConfiguration.get(this);
				Field menuKeyField = ViewConfiguration.class
						.getDeclaredField("sHasPermanentMenuKey");
				if (menuKeyField != null) {
					menuKeyField.setAccessible(true);
					menuKeyField.setBoolean(config, false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
