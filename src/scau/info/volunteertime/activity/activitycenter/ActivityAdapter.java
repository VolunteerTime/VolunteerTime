/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014��7��22��
 */
package scau.info.volunteertime.activity.activitycenter;

import java.util.LinkedList;

import scau.info.volunteertime.R;
import scau.info.volunteertime.application.Ding9App;
import scau.info.volunteertime.vo.ActivityDate;
import scau.info.volunteertime.vo.ActivityGroup;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.util.TimeUtils;

/**
 * @author 林锡鑫
 * 
 */
public class ActivityAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ImageCache IMAGE_CACHE;// ͼƬ����
	private Ding9App ding9App;
	private LinkedList<ActivityDate> linkedList;
	private Context mContext;

	private OnParticipateButtonListener clickListener;

	private String userId;

	public ActivityAdapter(Context context, LinkedList<ActivityDate> linkedList) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.linkedList = linkedList;
		this.ding9App = (Ding9App) context.getApplicationContext();
		this.IMAGE_CACHE = ding9App.IMAGE_CACHE;
		this.mContext = context;
		userId = ding9App.getUserId();
	}

	public void setListData(LinkedList<ActivityDate> linkedList) {
		this.linkedList = linkedList;
	}

	@Override
	public int getCount() {
		if (linkedList != null) {
			return linkedList.size();
		} else {
			Log.d("ActivityAdapter-getCount", "2");
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;

		Log.d("ActivityAdapter-getView", "position = " + position);
		final ActivityDate activityCenter = linkedList.get(position);
		if (view == null) {
			view = inflater.inflate(R.layout.item_activity_center, null);
			holder = new ViewHolder();
			holder.title = (TextView) view.findViewById(R.id.activity_title);
			holder.readNum = (TextView) view
					.findViewById(R.id.activity_read_num);
			holder.time = (TextView) view.findViewById(R.id.activity_time);
			holder.partLimitNum = (TextView) view
					.findViewById(R.id.activity_in_num);

			holder.participate = (Button) view.findViewById(R.id.activity_in);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.title.setText(activityCenter.getTitle());

		holder.readNum.setText("阅读 " + activityCenter.getReadNum());

		holder.time.setText(TimeUtils.getTime(activityCenter.getPublishTime(),
				TimeUtils.DATE_FORMAT_DATE)
				+ "~"
				+ TimeUtils.getTime(activityCenter.getEndTime(),
						TimeUtils.DATE_FORMAT_DATE));

		holder.partLimitNum.setText("参加人数 "
				+ activityCenter.getParticipatorsNum() + "/"
				+ activityCenter.getLimitNum());

		if (activityCenter.getEndTime() < System.currentTimeMillis()
				|| activityCenter.getActivityGroup() != null
				&& activityCenter.getActivityGroup().getPrincipalId().trim()
						.equals(userId)) {
			holder.participate.setEnabled(false);
		} else {
			holder.participate.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(final View v) {
					

					Mycamera animation = new Mycamera(true); //设置报名特效
					animation.setAnimationListener(new AnimationListener() {
						
						@Override
						public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationEnd(Animation animation) {
							// TODO Auto-generated method stub
							v.startAnimation(new Mycamera(false));
						}
					});
					v.startAnimation(animation);	 
					
					
					String text = (String) ((Button) v).getText();
					Log.d("getView-setOnClickListener", "text = " + text);
					if (text.trim().equals("报名"))
						clickListener.onParticipate(activityCenter.getId(),
								position, v);
					else
						clickListener.onQuit(activityCenter.getId(), position,
								v);
				}
			});

		}
		if (activityCenter.getGroupId() != 0
				&& checkGroup(activityCenter.getActivityGroup())) {
			Log.d("getView", "checkGroup = true = 取消报名 position = " + position);
			holder.participate.setText("取消报名");
			holder.participate.setBackgroundResource(R.drawable.unbaoming);
		}else 
			holder.participate.setBackgroundResource(R.drawable.baoming);

		if (activityCenter.getPublishTime() > System.currentTimeMillis()) {
			holder.participate.setEnabled(false);
			holder.participate.setBackgroundResource(R.drawable.baoming);
			holder.participate.setText("期待报名");
		}

		return view;

	}

	/**
	 * @param activityGroup
	 * @return boolean
	 */
	private boolean checkGroup(ActivityGroup activityGroup) {
		Log.d("checkGroup",
				"userId = " + userId + " activityGroup.getParticipators() = "
						+ activityGroup.getParticipators());
		if (activityGroup.getParticipators().contains("\"" + userId + "\"")) {
			return true;
		}
		return false;
	}

	public void setParticipateButtonListener(
			OnParticipateButtonListener listener) {
		this.clickListener = listener;
	};

	public interface OnParticipateButtonListener {
		public boolean isPartIn = true;

		public void onParticipate(int activityId, int position, View v);

		public void onQuit(int activityId, int position, View v);
	}

	static class ViewHolder {
		TextView title;
		TextView readNum;
		TextView time;
		TextView partLimitNum;
		Button participate;
	}

}
