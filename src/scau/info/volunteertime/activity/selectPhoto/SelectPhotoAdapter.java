/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-8-26
 */
package scau.info.volunteertime.activity.selectPhoto;

import java.util.ArrayList;
import java.util.List;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.selectPhoto.MyImageView.OnMeasureListener;
import scau.info.volunteertime.activity.selectPhoto.NativeImageLoader.NativeImageCallBack;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author 蔡超敏
 * 
 */

public class SelectPhotoAdapter extends BaseAdapter {

	private Point mPoint = new Point(0, 0);// 用来封装ImageView的宽和高的对象

	private GridView mGridView;
	private List<String> list;
	protected LayoutInflater mInflater;

	private int nowPhotoCount = 0;

	private int maxCount = 4;

	/**
	 * 用来存储图片的选中情况
	 */
	private ArrayList<Integer> saveStateList = new ArrayList<Integer>();

	private OnSelectClickListener selectClickListener;

	public SelectPhotoAdapter(Context context, List<String> list,
			GridView mGridView) {
		this.list = list;
		this.mGridView = mGridView;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder viewHolder;
		String path = list.get(position);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.grid_child_item, null);
			viewHolder = new ViewHolder();
			viewHolder.mImageView = (MyImageView) convertView
					.findViewById(R.id.child_image);
			viewHolder.mCheckBox = (CheckBox) convertView
					.findViewById(R.id.child_checkbox);

			// 用来监听ImageView的宽和高
			viewHolder.mImageView.setOnMeasureListener(new OnMeasureListener() {

				@Override
				public void onMeasureSize(int width, int height) {
					mPoint.set(width, height);
				}
			});
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.mImageView
					.setImageResource(R.drawable.friends_sends_pictures_no);
		}
		viewHolder.mImageView.setTag(path);
		viewHolder.mCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						nowPhotoCount = saveStateList.size();

						if (!saveStateList.contains(Integer.valueOf(position))
								&& isChecked) {
							if (nowPhotoCount >= maxCount) {
								viewHolder.mCheckBox.setChecked(false);
								Toast.makeText(mInflater.getContext(),
										"最多可选" + maxCount + "个图片",
										Toast.LENGTH_LONG).show();
							} else {
								addAnimation(viewHolder.mCheckBox);
								saveStateList.add(position);
							}
						} else if (saveStateList.contains(Integer
								.valueOf(position)) && !isChecked) {
							saveStateList.remove(Integer.valueOf(position));
						}

						if (selectClickListener != null) {
							selectClickListener.onClick(saveStateList.size());
						}

					}
				});

		viewHolder.mCheckBox.setChecked(saveStateList.contains(Integer
				.valueOf(position)));

		// 利用NativeImageLoader类加载本地图片
		Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(path,
				mPoint, new NativeImageCallBack() {

					@Override
					public void onImageLoader(Bitmap bitmap, String path) {
						ImageView mImageView = (ImageView) mGridView
								.findViewWithTag(path);
						if (bitmap != null && mImageView != null) {
							mImageView.setImageBitmap(bitmap);
						}
					}
				});

		if (bitmap != null) {
			viewHolder.mImageView.setImageBitmap(bitmap);
		} else {
			viewHolder.mImageView
					.setImageResource(R.drawable.friends_sends_pictures_no);
		}

		return convertView;
	}

	/**
	 * 给CheckBox加点击动画，利用开源库nineoldandroids设置动画
	 * 
	 * @param view
	 */
	private void addAnimation(View view) {
		float[] vaules = new float[] { 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1.0f,
				1.1f, 1.2f, 1.3f, 1.25f, 1.2f, 1.15f, 1.1f, 1.0f };
		AnimatorSet set = new AnimatorSet();
		set.playTogether(ObjectAnimator.ofFloat(view, "scaleX", vaules),
				ObjectAnimator.ofFloat(view, "scaleY", vaules));
		set.setDuration(150);
		set.start();
	}

	/**
	 * 获取选中的Item的position
	 * 
	 * @return
	 */
	public ArrayList<Integer> getSelectItems() {
		return saveStateList;
	}

	public void setMaxCount(int count) {
		maxCount = count;
	}

	public static class ViewHolder {
		public MyImageView mImageView;
		public CheckBox mCheckBox;
	}

	public void setOnSelectClickListener(
			OnSelectClickListener onSelectClickListener) {
		selectClickListener = onSelectClickListener;
	}

	public static interface OnSelectClickListener {
		public void onClick(int count);
	}

}
