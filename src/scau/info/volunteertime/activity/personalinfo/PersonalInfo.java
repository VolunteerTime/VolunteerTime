package scau.info.volunteertime.activity.personalinfo;

/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014��8��18��
 */ 

import scau.info.volunteertime.R;
import android.os.Bundle;  
import android.support.v4.preference.PreferenceFragment;

/**
 * @author �̳���
 *
 */
public class PersonalInfo extends PreferenceFragment{
	
	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 addPreferencesFromResource(R.layout.activity_personal_info);
		 findPreference("edit_0"); 
		 
		
		
	}
	

}
