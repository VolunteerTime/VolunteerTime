package scau.info.volunteertime.activity.selectPhoto;

import java.util.ArrayList;
import java.util.List;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.selectPhoto.SelectPhotoAdapter.OnSelectClickListener;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class SelectPhotoActivity extends ActionBarActivity {

	public static final int RESULT_CODE = 1234;

	private MenuItem menuItem;

	private int nowPhotoCount = 0;

	private int maxCount = 4;

	private GridView mGridView;
	private List<String> list;
	private SelectPhotoAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActionBar();
		setContentView(R.layout.activity_select_photo);

		initCount();

		initGridView();

	}

	/**
	 * 初始化GridView
	 */
	private void initGridView() {
		mGridView = (GridView) findViewById(R.id.child_grid);

		list = gePhotoPathList();

		adapter = new SelectPhotoAdapter(this, list, mGridView);

		adapter.setMaxCount(maxCount);

		adapter.setOnSelectClickListener(new OnSelectClickListener() {

			@Override
			public void onClick(int count) {
				menuItem.setTitle("确定(" + count + "/" + maxCount + ")");
			}
		});

		mGridView.setAdapter(adapter);

	}

	/**
	 * 返回手机里面所有的图片地址
	 * 
	 * @return ArrayList<String>
	 */
	private ArrayList<String> gePhotoPathList() {
		ArrayList<String> list = new ArrayList<String>();

		list.addAll(gePhotoPathListByOne(MediaStore.Images.Media.INTERNAL_CONTENT_URI));
		list.addAll(gePhotoPathListByOne(MediaStore.Images.Media.EXTERNAL_CONTENT_URI));

		return list;
	}

	private ArrayList<String> gePhotoPathListByOne(Uri mImageUri) {
		ArrayList<String> list = new ArrayList<String>();

		ContentResolver mContentResolver = SelectPhotoActivity.this
				.getContentResolver();

		// 只查询jpeg和png的图片
		Cursor mCursor = mContentResolver.query(mImageUri, null,
				MediaStore.Images.Media.MIME_TYPE + "=? or "
						+ MediaStore.Images.Media.MIME_TYPE + "=?",
				new String[] { "image/jpeg", "image/png" },
				MediaStore.Images.Media.DATE_MODIFIED);

		while (mCursor.moveToNext()) {
			// 获取图片的路径
			String path = mCursor.getString(mCursor
					.getColumnIndex(MediaStore.Images.Media.DATA));

			list.add(path);
		}

		mCursor.close();

		return list;
	}

	/**
	 * 初始化选择的图片数
	 */
	private void initCount() {
		Intent intent = getIntent();

		nowPhotoCount = intent.getIntExtra("nowPhotoCount", 0);

		maxCount -= nowPhotoCount;
	}

	/**
	 * 初始化actionBar
	 */
	private void initActionBar() {
		ActionBar bar = this.getSupportActionBar();
		this.setTitle("选择图片");
		bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE
				| ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_CUSTOM);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_photo, menu);
		menuItem = menu.findItem(R.id.action_ok);
		menuItem.setTitle("确定(" + 0 + "/" + maxCount + ")");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: // 返回首页
			onBackPressed();
			return true;
		case R.id.action_ok:
			toFinishSeleted();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 完成选择图片
	 */
	private void toFinishSeleted() {
		ArrayList<String> childList = new ArrayList<String>();

		ArrayList<Integer> saveStateList = adapter.getSelectItems();

		for (Integer i : saveStateList) {
			childList.add(list.get(i));
		}

		Intent intent = new Intent();

		intent.putStringArrayListExtra("data", childList);

		setResult(RESULT_CODE, intent);

		finish();
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_select_photo,
					container, false);
			return rootView;
		}
	}

}
