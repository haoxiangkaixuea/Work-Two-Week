package cn.edu.scujcc.worktwoweek;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * @author Administrator
 */
public class MyIntentService extends IntentService {
    public static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyIntentService");//调用父类的有参构造函数
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //打印当前线程的Id
        Log.d("MyIntentService", "Thread id is" + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyIntentService", "onDestroy");
    }
}
