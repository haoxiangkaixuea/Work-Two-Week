package cn.edu.scujcc.worktwoweek.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import cn.edu.scujcc.worktwoweek.R;
import cn.edu.scujcc.worktwoweek.fragment.AnotherRight;
import cn.edu.scujcc.worktwoweek.fragment.RightFragment;
import cn.edu.scujcc.worktwoweek.service.MyIntentService;
import cn.edu.scujcc.worktwoweek.service.MyService;
import cn.edu.scujcc.worktwoweek.util.NoticeUtils;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int SEND_NOTICE = 1;
    public static final String ACTION = "com.iffiness.intensified.metrication";
    private TextView tv;
    private ProgressBar pb;
    private NetworkChangeReceiver networkChangeReceiver;
    private LocalBroadcastManager mLocalBroadcastManager;
    private MyBroadcastReceiver mMyBroadcastReceiver;

    /**
     * bindService
     * 创建ServiceConnection匿名类（匿名内部类只能使用一次，它通常用来简化代码编写，
     * 但使用匿名内部类还有个前提条件：必须继承一个父类或实现一个接口）
     * 重写onServiceDisconnected（解绑服务时调用），onServiceConnected（绑定服务时调用）方法，、
     * 向下转型的得到DownLoadBinder实例，然后调用DownLoadBinder中的两个方法，
     */
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.DownLoadBinder downLoadBinder = ((MyService.DownLoadBinder) service);
            downLoadBinder.startDownload();
            downLoadBinder.seeProgress();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //给左侧碎片添加一个点击实例，点击左侧的按钮就会把右侧碎片替换为新的碎片
        Button mLeft = findViewById(R.id.left);
        mLeft.setOnClickListener(this);
        replaceFragment(new RightFragment());

        //Service
        Button mStart = findViewById(R.id.startserivce);
        Button mStop = findViewById(R.id.stopserivce);
        mStart.setOnClickListener(this);
        mStop.setOnClickListener(this);

        // Intent startIntent = new Intent(this, MyService.class);
        // bindService(startIntent, connection, Context.BIND_AUTO_CREATE);

        //service与activity 之间的通信
        Button bindService = findViewById(R.id.bind_service);
        Button unBindService = findViewById(R.id.unbind_service);
        bindService.setOnClickListener(this);
        unBindService.setOnClickListener(this);

        //IntentService
        tv = findViewById(R.id.tv);
        pb = findViewById(R.id.prb);
        Button startIntentService = findViewById(R.id.start_intent_service);
        startIntentService.setOnClickListener(this);
        initBroadcastReceiver();

        //动态注册广播
        IntentFilter intentFilter = new IntentFilter();
        //当网路发生变化是，系统会发出下面的广播，我们接收器要监听什么广播，就添加什么action
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        //创建NetworkChangeReceiver实例
        networkChangeReceiver = new NetworkChangeReceiver();
        //调用registerReceiver注册，把前面两个实例对象都传进去。
        registerReceiver(networkChangeReceiver, intentFilter);
        //最后NetworkChangeReceiver会接收到一条值为android.net.conn.CONNECTIVITY_CHANGE的广播
        //实现了监听网路变化的功能

        //标准广播
        Button sendStandardBroadcast = findViewById(R.id.send_broadcast);
        sendStandardBroadcast.setOnClickListener(v -> {
            //Intent intentStander = new Intent("com.example.broadcasttest.MY_BROADCAST");
            Intent intentStander = new Intent("android.intent.action.BOOT_COMPLETED");
            intentStander.setComponent(new ComponentName("cn.edu.scujcc.worktwoweek",
                    "cn.edu.scujcc.worktwoweek.BootCompleteReceiver"));
            sendBroadcast(intentStander);
        });

        //有序广播
//        sendStandardBroadcast.setOnClickListener(v -> {
//            //Intent intentStander = new Intent("com.example.broadcasttest.MY_BROADCAST");
//            Intent intentOrder = new Intent("com.example.broadcasttest.MY_BROADCAST");
//            intentOrder.setComponent(new ComponentName("cn.edu.scujcc.worktwoweek",
//                    "cn.edu.scujcc.worktwoweek.MyBroadcastsReceiver"));
//            sendOrderedBroadcast(intentOrder,null);
//        });

        //Notification通知
        Button sendNotice = findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(this);
        //方法二：取消通知
        // NotificationManager manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        // manager.cancel(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mLocalBroadcastManager != null && mMyBroadcastReceiver != null) {
            mLocalBroadcastManager.unregisterReceiver(mMyBroadcastReceiver);
        }
        //动态注册的广播接收器在最后一定要在onDestroy中取消注册,调用unregisterReceiver方法取消注册
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //点击左侧按钮，把右侧right换成anotherRight
            case R.id.left:
                replaceFragment(new AnotherRight());
                break;

            //启动服务
            case R.id.startserivce:
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent);
                break;
            //停止服务
            case R.id.stopserivce:
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent);
                break;
            //服务与活动之间的通信
            case R.id.bind_service:
                Intent bindIntent = new Intent(this, MyService.class);
                //绑定服务
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                //解绑服务
                unbindService(connection);
                break;

            //IntentService
            case R.id.start_intent_service:
                pb.setVisibility(View.VISIBLE);
                Intent intentService = new Intent(this, MyIntentService.class);
                startService(intentService);
                break;

            //notification
            case R.id.send_notice:
                NoticeUtils noticeUtils = new NoticeUtils();
                noticeUtils.sendNotice(this);
                break;
            default:
                break;
        }
    }

    /**
     * 动态添加碎片
     *
     * @param fragment 碎片实例
     */
    private void replaceFragment(Fragment fragment) {
        //获取碎片可以直接通过getSupportFragmentManager调用
        FragmentManager fragmentManager = getSupportFragmentManager();
        //通过beginTransaction()开启一个事务
        //获取碎片实例
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //通过replace来获取待替换的碎片id和实例
        fragmentTransaction.replace(R.id.right_layout, fragment);
        //按下back不会直接退出程序，而是建回到上一个碎片
        fragmentTransaction.addToBackStack(null);
        //使用commit进行提交事务
        fragmentTransaction.commit();
    }

    private void initBroadcastReceiver() {
        if (mLocalBroadcastManager == null) {
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        }
        if (mMyBroadcastReceiver == null) {
            mMyBroadcastReceiver = new MyBroadcastReceiver();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION);
        mLocalBroadcastManager.registerReceiver(mMyBroadcastReceiver, intentFilter);
    }

    /**
     * 动态注册广播
     */
    public class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (connectivity != null) {
                    Network networks = connectivity.getActiveNetwork();
                    NetworkCapabilities networkCapabilities = connectivity.getNetworkCapabilities(networks);
                    if (networkCapabilities != null) {
                        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            Toast.makeText(context, "wifi", Toast.LENGTH_SHORT).show();
                        } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            Toast.makeText(context, "流量", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "没有网路", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //根据IntentService中的发送的广播来进行UI更新
            if (ACTION.equals(intent.getAction())) {
                int progress = intent.getIntExtra("progress", 0);
                if (progress > 0 && progress < 100) {
                    tv.setText(getResources().getString(R.string.progress));
                } else if (progress >= 100) {
                    tv.setText(getResources().getString(R.string.unprogress));
                    pb.setVisibility(View.GONE);
                }
                pb.setProgress(progress);
            }
        }
    }
}
