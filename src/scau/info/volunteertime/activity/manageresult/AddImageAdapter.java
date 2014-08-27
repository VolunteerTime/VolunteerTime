/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-8-26
 */
package scau.info.volunteertime.activity.manageresult;

import java.util.List;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.selectPhoto.MyImageView;
import scau.info.volunteertime.activity.selectPhoto.MyImageView.OnMeasureListener;
import scau.info.volunteertime.activity.selectPhoto.NativeImageLoader;
import scau.info.volunteertime.activity.selectPhoto.NativeImageLoader.NativeImageCallBack;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * @author 蔡超敏
 * 
 */
public class AddImageAdapter extends BaseAdapter {

	private Point mPoint = new Point(0, 0);// 用来封装ImageView的宽和高的对象

	private GridView mGridView;
	private List<String> list;
	protected LayoutInflater mInflater;

	private int maxCount = 4;

	private OnAddClickListener addClickListener;
	private OnDeleteClickListener deleteClickListener;

	public AddImageAdapter(Context context, List<String> list,
			GridView mGridView) {
		this.list = list;
		this.mGridView = mGridView;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (list != null)
			if (list.size() >= maxCount)
				return maxCount;
			else
				return list.size() + 1;
		else
			return 1;
	}

	@Override
	public Object getItem(int position) {
		if (list != null && position >= 0 && position < list.size())
			return list.get(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		if (list != null)
			if (list.size() >= maxCount)
				return maxCount;
			else
				return list.size() + 1;
		else
			return 1;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		MyImageView imageView;
		convertView = mInflater.inflate(R.layout.item_add_grid, null);

		imageView = (MyImageView) convertView.findViewById(R.id.add_image);
		// 用来监听ImageView的宽和高
		imageView.setOnMeasureListener(new OnMeasureListener() {

			@Override
			public void onMeasureSize(int width, int height) {
				mPoint.set(width, height);
			}
		});
		convertView.setTag(imageView);
		if (list == null || position >= list.size()) {
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.d("AddImageAdapter-setOnClickListener", "onAddClick");
					if (addClickListener != null) {
						addClickListener.onClick();
					}
				}
			});
		} else {
			String path = list.get(position);
			imageView.setTag(path);

			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.d("AddImageAdapter-setOnClickListener", "onDeleteClick");
					if (deleteClickListener != null) {
						deleteClickListener.onClick(position);
					}
				}
			});

			// 利用NativeImageLoader类加载本地图片
			Bitmap bitmap = NativeImageLoader.getInstance().loadNativeImage(
					path, mPoint, new NativeImageCallBack() {

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
				imageView.setImageBitmap(bitmap);
			} else {
				imageView
						.setImageResource(R.drawable.friends_sends_pictures_no);
			}
		}

		return convertView;
	}

	public void setMaxCount(int count) {
		maxCount = count;
	}

	public void setOnAddClickListener(OnAddClickListener addClickListener) {
		this.addClickListener = addClickListener;
	}

	public void setOnDeleteClickListener(
			OnDeleteClickListener deleteClickListener) {
		this.deleteClickListener = deleteClickListener;
	}

	public interface OnAddClickListener {
		public void onClick();
	}

	public interface OnDeleteClickListener {
		public void onClick(int position);
	}

}
