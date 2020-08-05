package cn.edu.scujcc.worktwoweek;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyBindService extends Service {
    public int data = 0;
    private boolean connecting = false;
    private Callback callback;

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        connecting = true;
        //开启一个线程，对数据进行处理
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (connecting) {
                    if (callback != null) {
                        callback.onDataChange(data + "");
                    }
                    data++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        connecting = false;
    }

    public interface Callback {
        /**
         * onDataChange
         *
         * @param data 改变数据
         */
        void onDataChange(String data);
    }

    class MyBinder extends Binder {

        //向Activity返回MyService2实例
        MyBindService getService() {
            return MyBindService.this;
        }

        //获取从Activity传来的数据
        void transferData(int mData) {
            data = mData;
        }

        //将data数值传递给Activity
        int getData() {
            return data;
        }

    }
}