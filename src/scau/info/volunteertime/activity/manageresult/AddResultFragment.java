/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-8-22
 */
package scau.info.volunteertime.activity.manageresult;

import java.util.ArrayList;

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
import android.widget.EditText;
import android.widget.GridView;

/**
 * @author 蔡超敏
 * 
 */
public class AddResultFragment extends Fragment {

	private static final int REQUEST_CODE = 1;

	private int nowPhotoCount = 0;

	private EditText titleText, contentText;

	private GridView mGridView;
	private ArrayList<String> list;
	private AddImageAdapter adapter;

	public AddResultFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_manage_result,
				container, false);

		titleText = (EditText) rootView.findViewById(R.id.manage_result_title);
		contentText = (EditText) rootView
				.findViewById(R.id.manage_result_conent);

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

	public ArrayList<String> getSelectedList() {
		return list;
	}

	public String getTitle() {
		if (titleText != null)
			return titleText.getText().toString();
		else
			return null;
	}

	public String getContent() {
		if (contentText != null)
			return contentText.getText().toString();
		else
			return null;
	}

}
