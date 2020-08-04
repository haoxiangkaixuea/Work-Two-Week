package cn.edu.scujcc.worktwoweek;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Administrator
 */
public class AnotherBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "Another";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "received in orderAnotherBroadcastReceiver", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "AnotherBroadcastReceiver");
    }
}
