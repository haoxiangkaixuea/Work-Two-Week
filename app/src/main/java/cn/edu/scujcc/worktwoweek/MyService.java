package cn.edu.scujcc.worktwoweek;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    public static final String TAG = "Service";

    private DownLoadBinder mBinder = new DownLoadBinder();

    public MyService() {
    }

    //创建前台服务
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG", "onCreate");
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        //Notification notification=new NotificationCompat.Builder(this);
        Notification notification = new NotificationCompat.Builder(this, "default")
                .setContentTitle("This is content Title")
                .setContentText("This is content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
                .setContentIntent(pi)
                .build();
        //调用startForeground让Myservice变成一个前台服务，在系统状态栏显示出来
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG", "onStartCommand");
        //多线程
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                stopSelf();
//            }
//        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
        // throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "onDestroy");
    }

    //服务与活动之间的通信，在DownLoadBinder里提供一个下载方法
    public class DownLoadBinder extends Binder {
        //开始下载
        public void startDownload() {
            Log.d("TAG", "startDownload");
        }

        //查看下载进度
        public int seeProgress() {
            Log.d("TAG", "seeProgress");
            return 0;
        }
    }
//
//    public void excute() {
//        System.out.println("通过Binder得到Service的引用来调用Service内部的方法");
//    }
//
//    @Override
//    public boolean onUnbind(Intent intent) {
//        // 当调用者退出(即使没有调用unbindService)或者主动停止服务时会调用
//        System.out.println("调用者退出了");
//        return super.onUnbind(intent);
//
//    }
}
