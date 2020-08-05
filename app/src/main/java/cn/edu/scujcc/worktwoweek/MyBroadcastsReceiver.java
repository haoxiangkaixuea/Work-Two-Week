package cn.edu.scujcc.worktwoweek;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * @author Administrator
 */
public class MyBroadcastsReceiver extends BroadcastReceiver {
    ProgressBar mProgressBar1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "received in standerMyBroadcastReceiver", Toast.LENGTH_SHORT).show();
    }

}
