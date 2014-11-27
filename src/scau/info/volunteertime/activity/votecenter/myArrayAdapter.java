/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.activity.votecenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.MainActivity;
import scau.info.volunteertime.vo.VoteData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * @author 林锡鑫
 * @param <VoteData>
 * 
 */
public class myArrayAdapter extends ArrayAdapter<VoteData> {

	/**
	 * @param context
	 * @param resource
	 * @param textViewResourceId
	 * @param objects
	 */
	final int ITEM_FONT_SIZE = 15;
	final int TITLE_FONT_SIZE = 18;
	int Pcolor[];
	HashMap<Integer, View> map = new HashMap<Integer, View>();

	List<VoteData> votedata;
	Context context;
	int layout;
	LayoutInflater inflater;

	private OnSendVoteDataListener sendVoteDataListener;

	public myArrayAdapter(Context context, int resource,
			int textViewResourceId, List<VoteData> objects) {
		super(context, resource, textViewResourceId, objects);
		votedata = objects;
		layout = resource;
		this.context = context;
		Pcolor = new int[] { 0xFFFF8C05, 0xFF43A102, 0xFF4499EE, 0xFFEED205,
				0xFF1291A9, 0xFF712704, 0xFF70E1FF };

		inflater = LayoutInflater.from(context);
	}

	private int getAllVotes(ArrayList<Integer> votes) {
		int sum = 0;

		for (int i : votes) {
			sum += i;
		}
		return sum;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		System.out.println("position" + position + "   "
				+ votedata.get(position).getVotes());

		View view = map.get(position);
		if (votedata.get(position).isChange
				|| votedata.get(position).getChecked()) {

			view = this.inflater.inflate(layout, parent, false);
			view.findViewById(R.id.expandable_toggle_button).setBackgroundResource(R.drawable.buttonvote3);
			view.findViewById(R.id.expandable_toggle_button).setTag("forbidden");
			
			TextView text = (TextView) view.findViewById(R.id.voteTitle);
			text.setTextSize(TITLE_FONT_SIZE);
			text.setPadding(5, 5, 5, 0);
			text.setText("    "+votedata.get(position).getTitle());
			TextView voteSum = (TextView) view.findViewById(R.id.voteSum);
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			voteSum.setText(getAllVotes(votedata.get(position).getVotes()) + "人已经投票  "+format.format(new Date(votedata.get(position).getEndTime()))+" 结束");
			
			ViewGroup expandable = (ViewGroup) view
					.findViewById(R.id.expandable);

			setCheckView(expandable, position, parent);

			votedata.get(position).isChange = false;
			map.put(position, view);
			return view;
		} else if (view == null) {
			view = this.inflater.inflate(layout, parent, false);

			ViewGroup expandable = (ViewGroup) view
					.findViewById(R.id.expandable);

			TextView text = (TextView) view.findViewById(R.id.voteTitle);
			text.setText("    "+votedata.get(position).getTitle() );
			text.setTextSize(TITLE_FONT_SIZE);
			text.setPadding(5, 5, 5, 0);

			TextView voteSum = (TextView) view.findViewById(R.id.voteSum);
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			voteSum.setText(getAllVotes(votedata.get(position).getVotes()) + "人已经投票  "+format.format(new Date(votedata.get(position).getEndTime()))+" 结束");
			
			if (votedata.get(position).getSingle())
				setUncheckView(expandable, position, parent);
			else
				setUncheckUnSignalView(expandable, position, parent);
			map.put(position, view);
			return view;
		}

		return view;
	}

	private void setCheckView(ViewGroup expandable, int position,
			ViewGroup parent) {
		ArrayList<String> sum = votedata.get(position).getChoice();
		System.out.println(position + "changeeeee " + sum.size() + "    "
				+ votedata.get(position).getVotes());
		int allVotes = getAllVotes(votedata.get(position).getVotes());
		int i = 0;
		for (String s : sum) {
			TextView textView = new TextView(context);

			textView.setText(i + ". " + s);
			textView.setTextSize(ITEM_FONT_SIZE);
			textView.setLines(1);
			textView.setPadding(30, 19, 30, 3);

			expandable.addView(textView);

			LinearLayout layout = new LinearLayout(context);

			layout.setLayoutParams(new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, 19));
			layout.setPadding(0, 0, 0, 0);

			layout.setOrientation(LinearLayout.HORIZONTAL);

			ImageView progress = new ImageView(context);
			progress.setBackgroundColor(Pcolor[i % Pcolor.length]);
			int votes = votedata.get(position).getVotes().get(i++);
			int pro = (int) (MainActivity.ScreenWidth * 0.8 * votes / allVotes);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					pro, 16);
			layoutParams.setMargins(30, 0, 30, 0);

			ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 1f, 1f,
					Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
					0f);// ????????1-0
			scaleAnimation.setDuration(300);
			progress.setAnimation(scaleAnimation);
			progress.setLayoutParams(layoutParams);

			TextView pen = new TextView(context);

			pen.setText(votes * 100 / allVotes + "%");
			pen.setTextSize(14);
			pen.setPadding(0, -8, 0, 0);
			layout.addView(progress);
			layout.addView(pen);
			expandable.addView(layout);
		}

	}

	abstract class MyOnClickListener implements OnClickListener {
		List<CheckBox> list;

		MyOnClickListener(List<CheckBox> list) {
			this.list = list;
		}

		public abstract void onClick(View v);

	}

	private void setUncheckUnSignalView(ViewGroup expandable, int position,
			ViewGroup parent) {
		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		ArrayList<String> sum = votedata.get(position).getChoice();
		int j = 1;
		List<CheckBox> li = new ArrayList<CheckBox>();

		for (String i : sum) {
			CheckBox checkBox = new CheckBox(context);
			li.add(checkBox);
			checkBox.setLayoutParams(layoutParams);
			checkBox.setPadding(30, 0, 50, 0);
			checkBox.setTag(position);
			checkBox.setTextSize(ITEM_FONT_SIZE);
			checkBox.setText((j++) + ". " + i);
			checkBox.setLines(1);
			expandable.addView(checkBox);
		}
		final Button button = new Button(context);
		button.setTag(position);

		button.setOnClickListener(new MyOnClickListener(li) {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int sum = 0;
				for (int i = 0; i < list.size(); i++) {
					CheckBox ch = list.get(i);
					if (ch.isChecked()) {
						ArrayList<Integer> integers = votedata.get(
								(Integer) button.getTag()).getVotes();
						Integer num = integers.get(i);
						num++;
						integers.set(i, num);
						sendVoteDataListener.onSendVoteData(votedata
								.get((Integer) button.getTag()));
						sum++;
					}
				}
				if (sum > 0) {
					votedata.get((Integer) button.getTag()).isChange = true;
					// 需要发送数据，多选的......................................................
					notifyDataSetChanged();
				}
			}
		});

		button.setText("确定");

		expandable.addView(button);

	}

	private void setUncheckView(ViewGroup expandable, int position,
			ViewGroup parent) {

		RadioGroup radioGroup = new RadioGroup(context);

		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		radioGroup.setLayoutParams(layoutParams);
		radioGroup.setTag(position);

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				votedata.get((Integer) group.getTag()).isChange = true;

				// 需要发送数据，单选......................................................
				for (int i = 0; i < group.getChildCount(); i++) {
					if (group.getChildAt(i).getId() == checkedId) {
						ArrayList<Integer> integers = votedata.get(
								(Integer) group.getTag()).getVotes();
						Integer num = integers.get(i);
						num++;
						integers.set(i, num);
						sendVoteDataListener.onSendVoteData(votedata
								.get((Integer) group.getTag()));
						System.out.println("选择了 " + i);
					}
				}

				notifyDataSetChanged();
			}
		});
		ArrayList<String> sum = votedata.get(position).getChoice();
		int j = 1;
		for (String i : sum) {
			RadioButton radioButton = (RadioButton) inflater.inflate(
					R.layout.radio_button, parent, false);

			radioButton.setLayoutParams(layoutParams);
			radioButton.setPadding(30, 0, 30, 0);
			radioButton.setTextSize(ITEM_FONT_SIZE);
			radioButton.setText((j++) + ". " + i);
			radioButton.setLines(1);
			radioGroup.addView(radioButton);
		}
		expandable.addView(radioGroup);
	}

	public void setOnSendVoteDataListener(
			OnSendVoteDataListener sendVoteDataListener) {
		this.sendVoteDataListener = sendVoteDataListener;
	}

	public interface OnSendVoteDataListener {
		public void onSendVoteData(VoteData voteData);
	}

	/**
	 * @param sortedLinkList
	 */
	public void setListData(LinkedList<VoteData> sortedLinkList) {
		votedata = sortedLinkList;
	}

}
