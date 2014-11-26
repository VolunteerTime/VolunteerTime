/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-8-18
 */
package scau.info.volunteertime.activity.personalinfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import scau.info.volunteertime.R;
import scau.info.volunteertime.application.Ding9App;
import scau.info.volunteertime.business.UserBO;
import scau.info.volunteertime.util.NetworkStateUtil;
import scau.info.volunteertime.vo.UserInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author 蔡超敏
 * 
 */
public class PersonalInfo extends Fragment {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	private TextView personal_lnumber_detail;
	private TextView personal_username_detail;
	private TextView personal_class_detail;
	private TextView personal_snumber_detail;
	private TextView personal_qq_detail;
	private TextView personal_weixin_detail;
	private RadioGroup sexRadioGroup;
	private RadioButton male, female;

	private String username, sex, classname, longPhone, briefPhone, qq,
			wechant;

	private UserInfo userInfo;

	private Context mContext;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_personal_info,
				container, false);

		// get a reference to the listview, needed in order
		// to call setItemActionListener on it

		mContext = getActivity();
		personal_username_detail = (TextView) view
				.findViewById(R.id.personal_username_detail);
		personal_username_detail.setText("username");

		sexRadioGroup = (RadioGroup) view.findViewById(R.id.sexRadioGroup);
		sexRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;" + checkedId
						+ "  " + male.getId());
				if (checkedId == female.getId()) {
					Log.d("sexRadioGroup", "女");
					new CommitUserInfoDataTask("sex", "女").execute();
				} else {
					Log.d("sexRadioGroup", "男");
					new CommitUserInfoDataTask("sex", "男").execute();
				}
			}
		});
		personal_class_detail = (TextView) view
				.findViewById(R.id.personal_class_detail);
		personal_class_detail.setText("personal_class_detail");
		personal_lnumber_detail = (TextView) view
				.findViewById(R.id.personal_lnumber_detail);
		personal_lnumber_detail.setText("personal_lnumber_detail");
		personal_snumber_detail = (TextView) view
				.findViewById(R.id.personal_snumber_detail);
		personal_snumber_detail.setText("personal_snumber_detail");
		personal_qq_detail = (TextView) view
				.findViewById(R.id.personal_qq_detail);

		personal_qq_detail.setText("personal_qq_detail");

		personal_weixin_detail = (TextView) view
				.findViewById(R.id.personal_weixin_detail);

		male = (RadioButton) view.findViewById(R.id.male);
		female = (RadioButton) view.findViewById(R.id.female);

		initTextValue();

		// personal_head_imageview = (ImageView)
		// findViewById(R.id.personal_head_imageview);

		// listen for events in the two buttons for every list item.
		// the 'position' var will tell which list item is clicked
		RelativeLayout layout_personal_username = (RelativeLayout) view
				.findViewById(R.id.layout_personal_username);
		RelativeLayout layout_personal_class = (RelativeLayout) view
				.findViewById(R.id.layout_personal_class);
		RelativeLayout layout_personal_lnumber = (RelativeLayout) view
				.findViewById(R.id.layout_personal_lnumber);
		RelativeLayout layout_personal_snumber = (RelativeLayout) view
				.findViewById(R.id.layout_personal_snumber);
		RelativeLayout layout_personal_qq = (RelativeLayout) view
				.findViewById(R.id.layout_personal_qq);
		RelativeLayout layout_personal_weixin = (RelativeLayout) view
				.findViewById(R.id.layout_personal_weixin);

		RelativeLayout layout_personal_sex = (RelativeLayout) view
				.findViewById(R.id.layout_personal_sex);
		layout_personal_sex.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (male.isChecked()) {
					female.setChecked(true);
				} else
					male.setChecked(true);
			}
		});
		layout_personal_username.setOnClickListener(new MyClickListener(this
				.getActivity(), personal_username_detail) {

			@Override
			public void onClick(View v) {
				EditText e = new EditText(activity);

				e.setHint("真名");
				e.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// TODO Auto-generated method stub

					}

					private boolean checkString(String s) {
						return s.matches("[\u4E00-\u9FA5]+");
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
						// TODO Auto-generated method stub

					}

					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						if (s.length() > 0 && !checkString(s.toString()))
							s.delete(s.length() - 1, s.length());

					}
				});
				showDialog(e, "真名");

			}
		});

		layout_personal_class.setOnClickListener(new MyClickListener(this
				.getActivity(), personal_class_detail) {

			@Override
			public void onClick(View v) {
				final EditText e = new EditText(activity);
				e.setHint("班级(12级软工2班)");
				e.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// TODO Auto-generated method stub

					}

					private boolean checkString(String s) {
						return s.matches("[0-9]*[级]?[\u4E00-\u9FA5]*[0-9]*[班]?");
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
						// TODO Auto-generated method stub

					}

					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						System.out.println(s.toString());
						if (s.length() > 0 && !checkString(s.toString()))
							s.delete(s.length() - 1, s.length());
						else {
							Pattern p = Pattern
									.compile("([0-9]*)([级]?)([\u4E00-\u9FA5]*)([0,9]*)([班]?)");
							Matcher m = p.matcher(s.toString());
							boolean found = m.find();

							if (found) {
								String group1 = m.group(1);
								if (s.length() > 0 && group1.equals("")) {
									s.delete(s.length() - 1, s.length());
									return;
								}
								String group2 = m.group(2);

								String group3 = m.group(3);
								if (!group3.equals("")) {
									if (group2.equals(""))
										s.insert(group1.length(), "级");
								}

							}
						}
					}
				});
				showDialog(e, "班级");
			}
		});

		layout_personal_lnumber.setOnClickListener(new MyClickListener(this
				.getActivity(), personal_lnumber_detail) {

			@Override
			public void onClick(View v) {
				EditText e = new EditText(activity);
				e.setHint("长号");
				e.setInputType(InputType.TYPE_CLASS_NUMBER);
				e.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// TODO Auto-generated method stub

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
						// TODO Auto-generated method stub

					}

					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						if (s.length() > 11) {
							s.delete(s.length() - 1, s.length());
							return;
						}

					}
				});
				showDialog(e, "长号");

			}
		});

		layout_personal_snumber.setOnClickListener(new MyClickListener(this
				.getActivity(), personal_snumber_detail) {

			@Override
			public void onClick(View v) {
				EditText e = new EditText(activity);
				e.setInputType(InputType.TYPE_CLASS_NUMBER);
				e.setHint("短号");
				showDialog(e, "短号");

			}
		});

		layout_personal_qq.setOnClickListener(new MyClickListener(this
				.getActivity(), personal_qq_detail) {

			@Override
			public void onClick(View v) {
				EditText e = new EditText(activity);
				e.setInputType(InputType.TYPE_CLASS_NUMBER);
				e.setHint("QQ");
				showDialog(e, "QQ");

			}
		});

		layout_personal_weixin.setOnClickListener(new MyClickListener(this
				.getActivity(), personal_weixin_detail) {

			@Override
			public void onClick(View v) {
				final EditText e = new EditText(activity);
				e.setHint("微信号");
				showDialog(e, "微信号");
			}
		});
		return view;

	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * 修改值
	 */
	private void initTextValue() {
		personal_username_detail.setText(userInfo.getUserName());
		personal_qq_detail.setText(userInfo.getQq());
		personal_weixin_detail.setText(userInfo.getWechant());
		personal_class_detail.setText(userInfo.getGradeAndMajor());
		personal_lnumber_detail.setText(userInfo.getCellPhone());
		personal_snumber_detail.setText(userInfo.getBriefPhone());

		if (userInfo.getSex().trim().equals("男")) {
			male.setChecked(true);
		} else {
			female.setChecked(true);
		}

	}

	abstract class MyClickListener implements OnClickListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		FragmentActivity activity;
		TextView view;

		public MyClickListener(FragmentActivity activity, TextView view) {
			this.activity = activity;
			this.view = view;
		}

		public String showDialog(final EditText e, final String type) {

			new AlertDialog.Builder(activity)
					.setTitle("请输入")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setView(e)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									// TODO Auto-generated method stub

									String editText = e.getText().toString();
									System.out.println(type);
									if (type.equals("真名")) {
										new CommitUserInfoDataTask("name",
												editText).execute();
									} else if (type.equals("班级")) {
										if (!editText.substring(
												editText.length() - 1).equals(
												'班'))
											;
										editText = editText + "班";
										System.out.println(editText);
										new CommitUserInfoDataTask(
												"class_name", editText)
												.execute();
									} else if (type.equals("长号")) {
										new CommitUserInfoDataTask(
												"long_cell_phone", editText)
												.execute();
									} else if (type.equals("短号")) {
										new CommitUserInfoDataTask(
												"brief_cell_phone", editText)
												.execute();
									} else if (type.equals("QQ")) {
										new CommitUserInfoDataTask("qq",
												editText).execute();
									} else if (type.equals("微信号")) {
										new CommitUserInfoDataTask("wechat",
												editText).execute();
									}
									view.setText(editText);
									// switch(type)
									// {
									// case "真名": break;
									// case "班级":
									// break;
									// case "长号": break;
									// case "短号": break;
									// case "QQ": break;
									// case "微信号": break;
									// }
								}
							}).setNegativeButton("取消", null).show();

			return e.getText().toString();
		}

	}

	private class CommitUserInfoDataTask extends AsyncTask<Void, Void, String> {

		private boolean isConnect;

		private String key;
		private String value;

		/**
		 * @param string
		 * @param editText
		 */
		public CommitUserInfoDataTask(String string, String editText) {
			this.key = string;
			this.value = editText;
		}

		@Override
		protected String doInBackground(Void... params) {
			isConnect = NetworkStateUtil.isNetworkAvailable(mContext);

			Log.d("UpdateNowDataTask-doInBackground", "isConnect = "
					+ isConnect);
			if (!isConnect) {
				Log.d("doInBackground", "isConnect not");
				cancel(true);
				return null;
			}

			Log.d("UpdateNowDataTask-doInBackground", "in");

			return doInBackgroundFunction();
		}

		@Override
		protected void onCancelled() {
			cancelledFunction();
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(String result) {
			if (isCancelled()) {
				Log.d("Cancle", "call");
				cancelledFunction();
			} else {
				postFunction(result);
			}
			super.onPostExecute(result);
		}

		/**
		 * @param result
		 */
		private void postFunction(String result) {
			if (result != null && result.trim().equals("success")) {
				Log.d("getUserInfoDataTask", "personalInfo success!");
			} else {
				Log.d("getUserInfoDataTask", "personalInfo failure!");
			}
		}

		/**
		 * 
		 */
		private void cancelledFunction() {
			Log.d("UpdateNowDataTask", "wrong");
		}

		/**
		 * 
		 */
		private String doInBackgroundFunction() {
			return new UserBO().commitUserInfo(
					((Ding9App) mContext.getApplicationContext()).getUserId(),
					key, value);
		}
	}
}
