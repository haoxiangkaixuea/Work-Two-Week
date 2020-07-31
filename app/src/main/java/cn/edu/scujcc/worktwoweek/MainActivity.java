package cn.edu.scujcc.worktwoweek;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int SEND_NOTICE = 1;
    public static final String TAG = "Service";
    private Button mLeft, changButton;
    private Button mStart, mStop;
    private Button bindService, onBindService;
    private Button startIntentSerice;
    private Button sendStandardBroadcast, sendOrderBroadcast;
    private Button sendNotica;
    private MyService myService;
    private MyService.DownLoadBinder downLoadBinder;
    private IntentFilter intentFilter;
    private NetworkChangeRecevier networkChangeRecevier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //给左侧碎片添加一个点击实例，点击左侧的按钮就会把右侧碎片替换为新的碎片
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
        //最后NetworkChangeReceiver会接收到一条值为android.net.conn.CONNECTIVITY_CHANGE的广播
        //实现了监听网路变化的功能


        //标准广播
        sendStandardBroadcast = findViewById(R.id.send_broadcast);
        sendStandardBroadcast.setOnClickListener(v -> {
            Intent intentStander = new Intent("com.example.broadcasttest.MY_BROADCAST");
            intentStander.setComponent(new ComponentName("cn.edu.scujcc.worktwoweek",
                    "cn.edu.scujcc.worktwoweek.MyBroaodcastReceiver"));
            sendBroadcast(intentStander);
        });
        //有序广播
        sendOrderBroadcast = findViewById(R.id.send_orderbroadcast);
        sendOrderBroadcast.setOnClickListener(v -> {
            Intent intentOrder = new Intent("com.example.broadcasttest.MY_BROADCAST");
            //sendOrderedBroadcast(intentOrder, null);
        });

        //Notification通知
        sendNotica = findViewById(R.id.send_notice);
        sendNotica.setOnClickListener(this);
        //方法二：取消通知
        // NotificationManager manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        // manager.cancel(1);
    }

    //bindService
    //创建ServiceConnection匿名类（匿名内部类只能使用一次，它通常用来简化代码编写，
    // 但使用匿名内部类还有个前提条件：必须继承一个父类或实现一个接口）
    //重写onServiceDisconnected（解绑服务时调用），onServiceConnected（绑定服务时调用）方法，、
    //向下转型的得到DownLoadBinder实例，然后调用DownLoadBinder中的两个方法，
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
                //绑定服务
                bindService(bindintent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.onbind_service:
                //解绑服务
                unbindService(connection);
                break;

            //IntentService
            case R.id.start_intent_service:
                Log.d("MainActivity", "Thread id is" + Thread.currentThread().getId());
                Intent intentservice = new Intent(this, MyIntentService.class);
                startService(intentservice);
                break;

            //notification
            case R.id.send_notice:
                // 通知渠道组的id.
                String group = "my_group_01";
                // 用户可见的通知渠道组名称.
                CharSequence name = getString(R.string.group_name);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.createNotificationChannelGroup(new NotificationChannelGroup(group, name));
                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // 通知渠道的id
                String id = "my_channel_01";
                // 用户可以看到的通知渠道的名字.
                CharSequence name1 = getString(R.string.channel_name);
                // 用户可以看到的通知渠道的描述
                String description = getString(R.string.channel_description);
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(id, name1, importance);
                // 配置通知渠道的属性
                mChannel.setDescription(description);
                // 设置通知出现时的闪灯（如果 android 设备支持的话）
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                // 设置通知出现时的震动（如果 android 设备支持的话）
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                //最后在notificationmanager中创建该通知渠道
                mNotificationManager.createNotificationChannel(mChannel);
                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // 为该通知设置一个id
                int notifyId = 1;
                // 通知渠道的id
                String channalId = "my_channel_01";
                // Create a notification and set the notification channel.
                // 发布通知
                //实现通知的点击效果，使用PendingIntent来启动一个通知活动
                Intent intentnotice = new Intent(this, NotificationActivity.class);
                PendingIntent pi = PendingIntent.getActivity(this, 0, intentnotice, 0);

                //创建通知首先要创建一个NotificationManager来对通知进行管理，通过getSystemService获取到
                //里面需要穿一个字符串，一般传Context.NOTIFICATION_SERVICE
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                //接下来要用Bulider来构造Notification对象，这里我们使用NotificationCompat类来构造创建Notification对象
                //确保我们的程序字啊所有android系统版本都能运行
                Notification notification = new NotificationCompat.Builder(this, "default")
                        //通知的标题
                        .setContentTitle("This is content Title")
                        //通知的内容
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("method int androidx.appcompat.widget." +
                                "DropDownListView.lookForSelectablePosition(int, boolean) \" +\n" +
                                "  \"would have incorrectly overridden the package-private " +
                                "method in android.widget.ListView<"))
                        //显示大图
                        // .setStyle(new NotificationCompat.BigPictureStyle().bigPicture
                        //(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)))
                        //发出通知的时间
                        .setWhen(System.currentTimeMillis())
                        //通知的小标题
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        //通知点开后的大标题
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        //启动通知活动
                        .setContentIntent(pi)
                        //设置取消通知，方法一
                        .setAutoCancel(true)
                        //设置通知的重要程度
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setChannelId(channalId)
                        //通知是发出震动
                        .setVibrate(new long[]{0, 1000, 1000, 1000})
                        .build();
                //notify让通知显示出来
                manager.notify(SEND_NOTICE, notification);
                break;
            default:
                break;
        }
    }

    //动态添加碎片
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
                }
            }
        }
    }

}
