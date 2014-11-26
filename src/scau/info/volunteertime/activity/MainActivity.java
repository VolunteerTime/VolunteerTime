/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.activity;

import java.util.ArrayList;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.PopupMainMenu.OnItemOnClickListener;
import scau.info.volunteertime.activity.manageactivity.ManageActivity;
import scau.info.volunteertime.activity.manageactivity.MessagesActivity;
import scau.info.volunteertime.activity.manageresult.ManageResult;
import scau.info.volunteertime.activity.manageresult.ManageResultsExhibitionFragment;
import scau.info.volunteertime.activity.personalinfo.PersonalInfo;
import scau.info.volunteertime.activity.personalinfo.PersonalInfoActivity;
import scau.info.volunteertime.activity.settings.MessagesService;
import scau.info.volunteertime.activity.settings.Settings;
import scau.info.volunteertime.activity.settings.SlideSwitch;
import scau.info.volunteertime.application.Ding9App;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * @author 蔡超敏
 * 
 */
public class MainActivity extends BaseActionBarActivity {

	public static int ScreenWidth;
	private RelativeLayout actionView;// ActionBar的view
	private MainActivityFragment mainfragment;
	private PopupMainMenu titlePopup;

	private ArrayList<ActionItem> actionItems;

	FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;

	// userinfo
	private PersonalInfo personalInfo;

	// result
	private ImageButton contentAdd;
	private ManageResultsExhibitionFragment resultsExhibitionFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		actionView = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.main_action_bar_title, null);
		getSupportActionBar().setCustomView(
				actionView,
				new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT));
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);

		setContentView(R.layout.activity_main);

		fragmentManager = getSupportFragmentManager();

		fragmentTransaction = fragmentManager.beginTransaction();

		mainfragment = new MainActivityFragment();

		fragmentTransaction.replace(R.id.mainActivityFragmentLayout,
				mainfragment).commit();

		personalInfo = new PersonalInfo();

		resultsExhibitionFragment = new ManageResultsExhibitionFragment();

		barUiInit();

		init();

		setPushState();
		fragmentManager
				.addOnBackStackChangedListener(new OnBackStackChangedListener() {

					@Override
					public void onBackStackChanged() {
						Log.d("MainActivity-OnBackStackChangedListener",
								"fragmentManager");
					}
				});

	}

	/**
	 * 设置是否开启推送
	 */
	private void setPushState() {
		SharedPreferences sharedPreferences = getSharedPreferences(
				LoadActivity.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);

		int messageStatus = sharedPreferences.getInt("PUSH_STATUS",
				SlideSwitch.SWITCH_ON);

		if (messageStatus == SlideSwitch.SWITCH_ON) {
			if (!sharedPreferences.contains("PUSH_STATUS")) {
				// 独立sharePreference
				sharedPreferences.edit()
						.putInt("PUSH_STATUS", SlideSwitch.SWITCH_ON).commit();
			}
			// 设置信息推送打开
			Intent intent = new Intent(this, MessagesService.class);
			startService(intent);
		} else {
			// // 设置信息推送关闭
			Intent intent = new Intent(this, MessagesService.class);
			stopService(intent);
		}
	}

	/**
	 * 
	 */
	private void barUiInit() {
		contentAdd = (ImageButton) actionView.findViewById(R.id.conent_new);
		contentAdd.setVisibility(View.GONE);
	}

	private void init() {
		actionItems = new ArrayList<ActionItem>();

		actionItems.add(new ActionItem(this, "个人信息",
				R.drawable.mm_title_btn_receiver_normal));
		actionItems.add(new ActionItem(this, "我的活动",
				R.drawable.mm_title_btn_share_normal));
		actionItems.add(new ActionItem(this, "我的消息",
				R.drawable.mm_title_btn_set_normal));
		actionItems.add(new ActionItem(this, "设置",
				R.drawable.mm_title_btn_set_normal));

		titlePopup = new PopupMainMenu(this, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		titlePopup.addActionItems(actionItems);

		titlePopup.setItemOnClickListener(new OnItemOnClickListener() {

			@Override
			public void onItemClick(ActionItem item, int position) {
				Log.d("MainActivity-setItemOnClickListener", "position = "
						+ position);
				switch (position) {
				case 0:
					Log.d("MainActivity-setItemOnClickListener", "个人信息");
					toPersonInfo();
					break;
				case 1:
					Log.d("MainActivity-setItemOnClickListener", "我的活动");
					toMyActivityManagement();
					break;
				case 2:
					Log.d("MainActivity-setItemOnClickListener", "个人信息");
					toManageMessage();
					break;
				case 3:
					Log.d("MainActivity-setItemOnClickListener", "设置");
					toSetting();
					break;
				case 4:
					Log.d("MainActivity-setItemOnClickListener", "管理成员");
					toManagePeople();
					break;
				case 5:
					Log.d("MainActivity-setItemOnClickListener", "管理活动");
					toManageActivity();
					break;
				case 6:
					Log.d("MainActivity-setItemOnClickListener", "管理成果");
					toManageResults();
					break;
				case 7:
					Log.d("MainActivity-setItemOnClickListener", "管理投票");
					toManageVote();
					break;

				default:
					break;
				}

			}

		});
	}

	private void toPersonInfo() {
		Log.d("MainActivity-toMyActivityManagement", "start");
		Intent intent = new Intent(this, PersonalInfoActivity.class);
		startActivity(intent);
	}

	private void toSetting() {
		((Ding9App) getApplicationContext()).setMainActivity(this);
		Log.d("MainActivity-toSetting", "start");
		Intent intent = new Intent(this, Settings.class);
		startActivity(intent);
	}

	private void toMyActivityManagement() {
		Log.d("MainActivity-toMyActivityManagement", "start");
		Intent intent = new Intent(this, ManageActivity.class);
		startActivity(intent);
	}

	private void toManageMessage() {
		Log.d("MainActivity-toManageMessage", "start");
		Intent intent = new Intent(this, MessagesActivity.class);
		startActivity(intent);
	}

	private void toManageAuthority() {
		// TODO Auto-generated method stub

	}

	private void toManagePeople() {
		// TODO Auto-generated method stub

	}

	private void toManageActivity() {
		// TODO Auto-generated method stub

	}

	private void toManageResults() {
		Intent intent = new Intent(this, ManageResult.class);

		this.startActivity(intent);

		// fragmentTransaction = fragmentManager.beginTransaction();
		//
		// fragmentTransaction.replace(R.id.mainActivityFragmentLayout,
		// resultsExhibitionFragment).addToBackStack(null);
		//
		// fragmentTransaction.commit();
		//
		// contentAdd.setVisibility(View.VISIBLE);
		//
		// contentAdd.setOnClickListener(resultsExhibitionFragment
		// .getOnAddClickListener());
	}

	private void toManageVote() {
		// TODO Auto-generated method stub

	}

	public void showMenu(View view) {
		titlePopup.show(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity, menu);
		return true;

		// // Inflate the menu; this adds items to the action bar if it is
		// present.
		//
		// MenuItemCompat.setShowAsAction(
		// menu.add("个人信息").setIcon(android.R.drawable.ic_menu_rotate),
		// MenuItemCompat.SHOW_AS_ACTION_NEVER);
		// MenuItemCompat.setShowAsAction(
		// menu.add("No.41").setIcon(android.R.drawable.ic_menu_rotate),
		// MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		//
		// return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// switch (item.getItemId()) {
		// case android.R.id.home: {// 返回首页
		// Log.d("onOptionsItemSelected-ManageCouponActivity", "testHome");
		// onBackPressed();
		// return true;
		// }
		// }
		return super.onOptionsItemSelected(item);
		// String name = (String) item.getTitle();
		// if (name.equals("个人信息")) {
		//
		// FragmentTransaction fragmentTransaction = getSupportFragmentManager()
		// .beginTransaction();
		// fragmentTransaction
		// .replace(R.id.mainActivityFragmentLayout,
		// new PersonalInfo()).addToBackStack(null).commit();
		//
		// return true;
		// }
		//
		// return super.onOptionsItemSelected(item);
	}

}
