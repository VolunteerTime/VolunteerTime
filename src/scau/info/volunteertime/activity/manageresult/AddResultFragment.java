/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-8-22
 */
package scau.info.volunteertime.activity.manageresult;

import java.util.ArrayList;
import java.util.List;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.manageresult.AddImageAdapter.OnAddClickListener;
import scau.info.volunteertime.activity.manageresult.AddImageAdapter.OnDeleteClickListener;
import scau.info.volunteertime.activity.selectPhoto.SelectPhotoActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * @author 蔡超敏
 * 
 */
public class AddResultFragment extends Fragment {

	private static final int REQUEST_CODE = 1;

	private int nowPhotoCount = 0;

	private GridView mGridView;
	private List<String> list;
	private AddImageAdapter adapter;

	public AddResultFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_manage_result,
				container, false);

		mGridView = (GridView) rootView
				.findViewById(R.id.manage_result_images_grid);

		list = new ArrayList<String>();

		adapter = new AddImageAdapter(getActivity(), list, mGridView);

		adapter.setMaxCount(4);

		adapter.setOnAddClickListener(new OnAddClickListener() {

			@Override
			public void onClick() {
				Intent intent = new Intent(getActivity(),
						SelectPhotoActivity.class);

				intent.putExtra("nowPhotoCount", nowPhotoCount);

				startActivityForResult(intent, REQUEST_CODE);
			}
		});

		adapter.setOnDeleteClickListener(new OnDeleteClickListener() {

			@Override
			public void onClick(int position) {
				list.remove(position);
				nowPhotoCount--;
				adapter.notifyDataSetChanged();
			}
		});

		mGridView.setAdapter(adapter);

		// Button button = (Button) rootView
		// .findViewById(R.id.manage_result_choose_image);
		// button.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Log.d("AddResultFragment-onClick", "images");
		// Intent intent = new Intent(getActivity(),
		// SelectPhotoActivity.class);
		//
		// intent.putExtra("nowPhotoCount", nowPhotoCount);
		//
		// startActivityForResult(intent, REQUEST_CODE);
		// }
		// });
		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE) {

			if (resultCode == SelectPhotoActivity.RESULT_CODE) {

				Bundle bundle = data.getExtras();

				list.addAll(bundle.getStringArrayList("data"));

				adapter.notifyDataSetChanged();

				nowPhotoCount = list.size();

			}

		}
	}

}
