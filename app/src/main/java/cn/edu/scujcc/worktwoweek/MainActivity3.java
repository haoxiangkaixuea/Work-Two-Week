package cn.edu.scujcc.worktwoweek;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    MyService2.MyBinder binder = null;
    private TextView textService;
    private boolean bind = false;
    private int transformData;
    //    private static class AppHandler extends Handler {
//        //弱引用，在垃圾回收时，被回收
//        WeakReference<Activity> activity;
//
//        AppHandler(Activity activity){
//            this.activity=new WeakReference<Activity>(activity);
//        }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //在此处更新UI
            textService.setText(msg.obj.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        transformData = 0;
        textService = findViewById(R.id.servicetext);
        Button bindBtn = findViewById(R.id.bind);
        Button unBindBtn = findViewById(R.id.onbind);
        Button clearBt = findViewById(R.id.clear);
        bindBtn.setOnClickListener(this);
        unBindBtn.setOnClickListener(this);
        clearBt.setOnClickListener(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        //获取binder对象
        binder = (MyService2.MyBinder) service;

        //向Service传递数据 TransformData
        binder.TransferData(transformData);
        //获取从Service传递的MyService对象
        MyService2 myService2 = binder.getService();
        //接口回调 监控Service中的数据变化 并在handler中更新UI
        myService2.setCallback(new MyService2.Callback() {
            @Override
            public void onDataChange(String data) {
                Message msg = new Message();
                msg.obj = data;
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind:
                if (!bind) {
                    bind = true;
                    //服务绑定后，会调用 onServiceConnected
                    Intent bindIntent = new Intent(this, MyService2.class);
                    bindService(bindIntent, this, BIND_AUTO_CREATE);
                    Toast.makeText(this, "Bind Service Success!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.onbind:
                if (bind) {
                    //从Service中获取data数值
                    transformData = binder.getData();
                    unbindService(this);
                    bind = false;
                    Toast.makeText(this, "unBind Service Success!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.clear:
                if (!bind) {
                    transformData = 0;
                    textService.setText("0");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

}
