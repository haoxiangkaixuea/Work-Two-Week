package cn.edu.scujcc.worktwoweek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mLeft, changButton;
    private Button mStart, mStop;
    private Button bindService, onBindService;
    private Button startIntentSerice;
    private MyService myService;
    private MyService.DownLoadBinder downLoadBinder;
    public static final String TAG = "Service";
    private IntentFilter intentFilter;
    private NetworkChangeRecevier networkChangeRecevier;
//    private String fragmentName;
//    private OnButtonClickedListener buttonClickedListener;
//    /**
//     * 定义一个Handler用于接收黄色碎片给Activity发出来的指令
//     */
//    public Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg != null) {
//                switch (msg.what) {
//                    case 101:
//                        /**
//                         * 接收到黄色碎片发来的指令,Activity执行替换操作
//                         */
//                        fragmentName = LeftFragment.class.getName();
//                        replaceFragment(R.id.left_fragment, fragmentName);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Fragment
        mLeft = findViewById(R.id.left);
        mLeft.setOnClickListener(this);
        replaceFragment(new RightFragment());

        //Service
        mStart = findViewById(R.id.startservice);
        mStop = findViewById(R.id.stopservice);
        mStart.setOnClickListener(this);
        mStop.setOnClickListener(this);

        // Intent startIntent = new Intent(this, MyService.class);
        // bindService(startIntent, connection, Context.BIND_AUTO_CREATE);


        //service与activity 之间的通信
        bindService = findViewById(R.id.bind_service);
        onBindService = findViewById(R.id.onbind_service);
        bindService.setOnClickListener(this);
        onBindService.setOnClickListener(this);

        //IntentService
        startIntentSerice = findViewById(R.id.start_intent_service);
        startIntentSerice.setOnClickListener(this);

        //动态注册广播
        intentFilter = new IntentFilter();
        //当网路发生变化是，系统会发出下面的广播，我们接收器要监听什么广播，就添加什么action
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        //创建NetworkChangeRecevier实例
        networkChangeRecevier = new NetworkChangeRecevier();
        //调用registerReceiver注册，把前面两个实例对象都传进去。
        registerReceiver(networkChangeRecevier, intentFilter);
        //最后NetworkChangeRecevier会接收到一条值为android.net.conn.CONNECTIVITY_CHANGE的广播
        //实现了监听网路变化的功能
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downLoadBinder = ((MyService.DownLoadBinder) service);
            downLoadBinder.startDownload();
            downLoadBinder.seeProgress();
            //System.out.println("Service连接成功");
            // 执行Service内部自己的方法
            //myService.excute();
        }
    };

    @Override
    protected void onDestroy() {
        Log.d("TAG", "onDestroy");
        super.onDestroy();
        //动态注册的广播接收器在最后一定要在onDestroy中取消注册,调用unbindService方法取消注册
        unregisterReceiver(networkChangeRecevier);
        //unbindService(connection);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //点击左侧按钮，把右侧right换成anotherright
            case R.id.left:
                replaceFragment(new AnotherRight());
                break;

            //启动服务
            case R.id.startservice:
                Intent startIntent = new Intent(this, MyService.class);
                startService(startIntent);
                break;
            //停止服务
            case R.id.stopservice:
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent);
                break;
            //服务与活动之间的通信
            case R.id.bind_service:
                Intent bindintent = new Intent(this, MyService.class);
                bindService(bindintent, connection, BIND_AUTO_CREATE);//绑定服务
                break;
            case R.id.onbind_service:
                unbindService(connection);//解绑服务
                break;

            //IntentService
            case R.id.start_intent_service:
                Log.d("MainActivity", "Thread id is" + Thread.currentThread().getId());
                Intent intentservice = new Intent(this, MyIntentService.class);
                startService(intentservice);
            default:
                break;

        }
    }


    private void replaceFragment(Fragment Fragment) {
        //获取碎片可以直接通过getSupportFragmentManager调用
        FragmentManager fragmentManager = getSupportFragmentManager();
        //通过beginTransaction()开启一个事务
        //获取碎片实例
        //RightFragment right=(RightFragment)getSupportFragmentManager().findFragmentById(R.id.right_layout);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //通过replace来获取待替换的碎片id和实例
        fragmentTransaction.replace(R.id.right_layout, Fragment);
        //按下back不会直接退出程序，而是建回到上一个碎片
        fragmentManager.addOnBackStackChangedListener(null);
        //使用commit进行提交事务
        fragmentTransaction.commit();

    }

    //动态注册广播
    public class NetworkChangeRecevier extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (connectivity != null) {
                    Network networks = connectivity.getActiveNetwork();
                    NetworkCapabilities networkCapabilities = connectivity.getNetworkCapabilities(networks);
                    if (networkCapabilities != null) {
                        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            Toast.makeText(context, "-----------wifi", Toast.LENGTH_SHORT).show();
                            Log.e("-----------wifi", "wifi");
                        } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            Toast.makeText(context, "-----------流量", Toast.LENGTH_SHORT).show();
                            Log.e("-----------流量", "手机流量");
                        }
                    } else {
                        Toast.makeText(context, "-----------没有网路", Toast.LENGTH_SHORT).show();
                        Log.e("------------没有网络", "没有网络");
                    }
                } } }
    }

}
