package cn.edu.scujcc.worktwoweek.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import cn.edu.scujcc.worktwoweek.R;

/**
 * @author Administrator
 */
public class MyService extends Service {
    private DownLoadBinder mBinder = new DownLoadBinder();

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = null;
            if (mNotificationManager != null) {
                notificationChannel = mNotificationManager.getNotificationChannel("MyService");
            }
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
                if (mNotificationManager != null) {
                    mNotificationManager.createNotificationChannel(channel);
                }
            }
        }
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
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    public static class DownLoadBinder extends Binder {
        //开始下载
        public void startDownload() {
        }

        //查看下载进度
        public void seeProgress() {
        }
    }
}
