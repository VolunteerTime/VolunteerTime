/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.activity;

import java.util.ArrayList;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.PopupMainMenu.OnItemOnClickListener;
import scau.info.volunteertime.activity.personalinfo.PersonalInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * @author 蔡超敏
 * 
 */
public class MainActivity extends BaseActionBarActivity {

	public static int ScreenWidth;
	private RelativeLayout actionView;// ActionBar的view
	MainActivityFragment mainfragment;
	private PopupMainMenu titlePopup;

	private ArrayList<ActionItem> actionItems;

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
		getSupportActionBar().setTitle("首页");

		setContentView(R.layout.activity_main);
		FragmentManager fragmentManager = getSupportFragmentManager();

		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		mainfragment = new MainActivityFragment();

		fragmentTransaction.replace(R.id.mainActivityFragmentLayout,
				mainfragment).commit();

		init();

	}

	private void init() {
		actionItems = new ArrayList<ActionItem>();

		actionItems.add(new ActionItem(this, "个人信息",
				R.drawable.mm_title_btn_receiver_normal));

		actionItems.add(new ActionItem(this, "设置",
				R.drawable.mm_title_btn_set_normal));

		actionItems.add(new ActionItem(this, "我的活动",
				R.drawable.mm_title_btn_share_normal));

		actionItems.add(new ActionItem(this, "管理权限",
				R.drawable.mm_title_btn_set_normal));

		actionItems.add(new ActionItem(this, "管理成员",
				R.drawable.mm_title_btn_share_normal));

		actionItems.add(new ActionItem(this, "管理活动",
				R.drawable.mm_title_btn_set_normal));

		actionItems.add(new ActionItem(this, "管理投票",
				R.drawable.mm_title_btn_share_normal));

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
					Log.d("MainActivity-setItemOnClickListener", "设置");
					toSetting();
					break;
				case 2:
					Log.d("MainActivity-setItemOnClickListener", "我的活动");
					toMyActivityManagement();
					break;
				case 3:
					Log.d("MainActivity-setItemOnClickListener", "管理权限");
					toManageAuthority();
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
		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		fragmentTransaction
				.replace(R.id.mainActivityFragmentLayout, new PersonalInfo())
				.addToBackStack(null).commit();
	}

	private void toSetting() {
		// TODO Auto-generated method stub

	}

	private void toMyActivityManagement() {
		// TODO Auto-generated method stub

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
