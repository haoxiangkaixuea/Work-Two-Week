package cn.edu.scujcc.worktwoweek;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroaodcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "received in standreMyBroaodcastReceiver", Toast.LENGTH_SHORT).show();
        //在onReceive中调用abortBroadcast方法表示截断这条广播，后面的广播接收器无法收到这条广播。
       //abortBroadcast();
    }
}
