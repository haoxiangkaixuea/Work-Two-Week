package cn.edu.scujcc.worktwoweek;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Administrator
 */
public class MyBroadcastsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "received in standerMyBroadcastReceiver", Toast.LENGTH_SHORT).show();
        Log.d("stander", "standerMyBroadcastReceiver");
        //在onReceive中调用abortBroadcast方法表示截断这条广播，后面的广播接收器无法收到这条广播。
        //abortBroadcast();
    }
}
