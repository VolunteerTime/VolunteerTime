package scau.info.volunteertime.activity.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompletedMessagesReceiver extends BroadcastReceiver {
	public BootCompletedMessagesReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			Log.d("BootCompletedMessagesReceiver", "开机启动推送消息的服务");
			Intent newIntent = new Intent(context, MessagesService.class);
			context.startService(newIntent);
		}
	}
}
