package cn.edu.scujcc.worktwoweek;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {
    public static final String TAG = "Service";

    //private final IBinder binder = new MyBinder();
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG", "onCreate");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TAG", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "onDestroy");
    }

//    public class MyBinder extends Binder {
//        MyService getService() {
//            return MyService.this;
//        }
//    }
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return binder;
//    }
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
