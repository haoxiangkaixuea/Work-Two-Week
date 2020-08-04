package cn.edu.scujcc.worktwoweek;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

/**
 * @author Administrator
 */
public class MyIntentService extends IntentService {
    public static final String TAG = "MyIntentService";
    public ProgressBar mProgressBar;

    public MyIntentService() {
        //调用父类的有参构造函数
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //得到progressBar的最大刻度
        int progressBarMax = mProgressBar.getMax();
        try {
            while (progressBarMax != mProgressBar.getProgress()) {
                //progressBar的最大刻度细分为十份
                int everyProgress = progressBarMax / 10;
                //得到当前刻度
                int currentprogress = mProgressBar.getProgress();
                //设置更新后的刻度
                mProgressBar.setProgress(currentprogress + everyProgress);
                //线程睡眠一秒
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //打印当前线程的Id
        Log.d(TAG, "Thread id is" + Thread.currentThread().getId());
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        super.onCreate();
    }

    /**
     * 复写onStartCommand()方法
     * 默认实现 = 将请求的Intent添加到工作队列里
     **/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
