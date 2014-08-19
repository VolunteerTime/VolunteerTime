package scau.info.volunteertime.activity.personalinfo;

/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014年8月18日
 */ 

import scau.info.volunteertime.R;
import android.os.Bundle;  
import android.support.v4.preference.PreferenceFragment;

/**
 * @author 蔡超敏
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
