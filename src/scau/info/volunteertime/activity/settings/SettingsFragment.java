/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-11-25
 */
package scau.info.volunteertime.activity.settings;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.LoadActivity;
import scau.info.volunteertime.activity.login.Login;
import scau.info.volunteertime.activity.settings.SlideSwitch.OnSwitchChangedListener;
import scau.info.volunteertime.application.Ding9App;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.util.ToastUtils;

/**
 * @author 蔡超敏
 * 
 */
public class SettingsFragment extends Fragment {

	private SlideSwitch pushSwitcher;
	private Button cleanBufferBt;
	private Button aboutBt;
	private Button logoutBt;
	private Button feedBackBt;
	private Ding9App ding9App;

	private Button resetPasswordBt;

	// 设置信息推送
	private Context mcontext;
	// 独立sharePreference
	private SharedPreferences sharedPreferences;

	public SettingsFragment() {
		mcontext = this.getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_settings, container,
				false);
		ding9App = (Ding9App) getActivity().getApplication();
		pushSwitcher = (SlideSwitch) rootView.findViewById(R.id.push_switcher);
		cleanBufferBt = (Button) rootView.findViewById(R.id.clean_buffer_bt);
		aboutBt = (Button) rootView.findViewById(R.id.about_bt);
		logoutBt = (Button) rootView.findViewById(R.id.logout_bt);
		feedBackBt = (Button) rootView.findViewById(R.id.feed_back_bt);

		resetPasswordBt = (Button) rootView
				.findViewById(R.id.reset_password_bt);

		mcontext = this.getActivity();
		sharedPreferences = mcontext.getSharedPreferences(
				LoadActivity.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);

		resetPasswordBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent toAboutIntent = new Intent(getActivity(),
						ResetPasswordActivity.class);
				getActivity().startActivity(toAboutIntent);
			}
		});

		cleanBufferBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ImageCache IMAGE_CACHE;// 图片缓存
				Ding9App ding9App;// Application
				ding9App = (Ding9App) getActivity().getApplicationContext();
				IMAGE_CACHE = ding9App.IMAGE_CACHE;
				IMAGE_CACHE.clear();
				ToastUtils.show(getActivity(), "已清除缓存~");
			}
		});
		pushSwitcher.setText("打开", "关闭");
		int tempPushStatus = sharedPreferences.getInt("PUSH_STATUS",
				SlideSwitch.SWITCH_ON);
		if (tempPushStatus == SlideSwitch.SWITCH_ON) {
			pushSwitcher.setStatus(true);
		} else {
			pushSwitcher.setStatus(false);
		}
		pushSwitcher.setOnSwitchChangedListener(new OnSwitchChangedListener() {

			@Override
			public void onSwitchChanged(SlideSwitch obj, int status) {
				// TODO Auto-generated method stub
				if (status == SlideSwitch.SWITCH_ON) {
					ToastUtils.show(getActivity(), "打开");
					// 独立sharePreference
					sharedPreferences.edit()
							.putInt("PUSH_STATUS", SlideSwitch.SWITCH_ON)
							.commit();

					// 设置信息推送打开
					Intent intent = new Intent(mcontext, MessagesService.class);
					mcontext.startService(intent);

				} else {
					ToastUtils.show(getActivity(), "关闭");

					// 独立sharePreference
					sharedPreferences.edit()
							.putInt("PUSH_STATUS", SlideSwitch.SWITCH_OFF)
							.commit();
					;

					// // 设置信息推送关闭
					Intent intent = new Intent(mcontext, MessagesService.class);
					mcontext.stopService(intent);
				}
			}
		});

		aboutBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent toAboutIntent = new Intent(getActivity(),
						AboutActivity.class);
				getActivity().startActivity(toAboutIntent);
			}
		});
		feedBackBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent toFeedBackIntent = new Intent(getActivity(),
						FeedBackActivity.class);
				startActivity(toFeedBackIntent);
			}
		});
		String userId = ding9App.getUserId();
		if (userId == null || userId.equals("")) {
			logoutBt.setVisibility(View.GONE);
		} else {
			logoutBt.setVisibility(View.VISIBLE);
			logoutBt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ding9App.setUserId("");
					SharedPreferences sp = getActivity().getSharedPreferences(
							LoadActivity.SHAREDPREFERENCES_NAME,
							Context.MODE_PRIVATE);
					sp.edit().putBoolean(LoadActivity.SHARE_ISCHECK, false)
							.commit();
					ToastUtils.show(getActivity(), "退出成功~");
					// logoutBt.setVisibility(View.GONE);
					Intent intent = new Intent(getActivity(), Login.class);
					startActivity(intent);
					getActivity().finish();
					ding9App.getMainActivity().finish();
				}
			});
		}
		return rootView;
	}
}