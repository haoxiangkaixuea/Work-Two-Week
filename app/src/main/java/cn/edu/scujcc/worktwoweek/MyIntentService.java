package cn.edu.scujcc.worktwoweek;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * @author Administrator
 */
public class MyIntentService extends IntentService {
    public static final String TAG = "MyIntentService";
    private int count = 0;
    private boolean isRunning = true;
    private LocalBroadcastManager mLocalBroadcastManager;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */

    public MyIntentService() {
        super("MyIntentService");
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        setIntentRedelivery(true);
    }


//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        MainActivity mainActivity = new MainActivity();
//        // intent已经通过IntentService内部的Handler传递过来
//        int taskId = intent.getIntExtra("Progrebar", 0);
//        startThread(0);
//    }
//
//    //根据不同的taskId来标记不同的进度
//    private void startThread(int taskId) {
//        try {
//            Thread.sleep(1000);
//            //发送线程状态
//            MainActivity mainActivity = new MainActivity();
//            mainActivity.sendThreadStatus("线程启动 --> startThread()");
//            boolean ruinIna = true;
//            int[] mProgress = new int[0];
//            mProgress[taskId] = 0;
//            while (ruinIna) {
//                mProgress[taskId]++;
//                if (mProgress[taskId] >= 100) {
//                    ruinIna = false;
//                }
//                mainActivity.sendThreadStatus("线程Running --> startThread()");
//                Thread.sleep(30);
//            }
//            mainActivity.sendThreadStatus("线程结束 --> startThread()");
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    // }

//        while (status < 100) {
//            // 获取耗时操作的完成百分比
//            status = doWork();
//            // 发送消息
//            mHandler.sendEmptyMessage(0x111);
//        }
//
//        //打印当前线程的Id
//        Log.d(TAG, "Thread id is" + Thread.currentThread().getId());
//    }
//
//    // 模拟一个耗时的操作
//    public int doWork() {
//        //得到progressBar的最大刻度
//        int progressBarMax = mProgressBar.getMax();
//        hasData++;
//        try {
//            while (progressBarMax != mProgressBar.getProgress()) {
//                //progressBar的最大刻度细分为十份
//                int everyProgress = progressBarMax / 10;
//                //得到当前刻度
//                int currentprogress = mProgressBar.getProgress();
//                //设置更新后的刻度
//                mProgressBar.setProgress(currentprogress + everyProgress);
//                //线程睡眠一秒
//                Thread.sleep(150);
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return hasData;

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        count = 0;
        isRunning = true;
        while (isRunning) {
            try {
                count++;
                if (count >= 100) {
                    isRunning = false;
                }
                Thread.sleep(100);
                sendThreadStatus(count);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendThreadStatus(int progress) {
        Intent intent = new Intent(MainActivity.ACTION);
        intent.putExtra("progress", progress);
        mLocalBroadcastManager.sendBroadcast(intent);
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
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
