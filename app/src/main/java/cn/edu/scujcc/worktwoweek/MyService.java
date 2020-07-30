package cn.edu.scujcc.worktwoweek;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
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
//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
//        //Notification notification=new NotificationCompat.Builder(this);
//        Notification notification = new NotificationCompat.Builder(this, "default")
//                .setContentTitle("This is content Title")
//                .setContentText("This is content text")
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.ic_launcher_round)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
//                .setContentIntent(pi)
//                .build();
//        //调用startForeground让Myservice变成一个前台服务，在系统状态栏显示出来
//        startForeground(1, notification);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = mNotificationManager.getNotificationChannel("MyService");
            if (notificationChannel == null) {
                NotificationChannel channel = new NotificationChannel("MyService",
                        "com.example.liyun.testservice", NotificationManager.IMPORTANCE_HIGH);
                //是否在桌面icon右上角展示小红点
                channel.enableLights(true);
                //小红点颜色
                channel.setLightColor(Color.RED);
                //通知显示
                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                //是否在久按桌面图标时显示此渠道的通知
                //channel.setShowBadge(true);
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        int notifyId = (int) System.currentTimeMillis();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "MyService");
        mBuilder.setSmallIcon(R.drawable.ic_launcher_background);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mBuilder.setContentTitle(getResources().getString(R.string.app_name));
        }
//当id设置为0时，隐藏不显示通知，那么服务在60s后一样时会被杀死的。
//要如何隐藏通知而服务不被杀死，这个还在学习中。
        startForeground(1, mBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG", "onStartCommand");

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
        stopForeground(true);

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
