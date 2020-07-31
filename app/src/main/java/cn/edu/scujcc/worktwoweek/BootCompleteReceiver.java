package cn.edu.scujcc.worktwoweek;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author Administrator
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    //静态注册
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "BootCompleteReceiver", Toast.LENGTH_SHORT).show();
    }
}
