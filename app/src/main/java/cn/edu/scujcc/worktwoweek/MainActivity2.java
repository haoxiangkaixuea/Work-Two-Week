package cn.edu.scujcc.worktwoweek;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity2 extends AppCompatActivity {
    Button sendActivity;
    TextView textActivity;
    TextView textFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //  Activity ----> Fragment
        textActivity = findViewById(R.id.text_activity);
        // 步骤1：获取FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 步骤2：获取FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // 步骤3：创建需要添加的Fragment
        final LeftFragment fragment = new LeftFragment();
        // 步骤4:创建Bundle对象
        // 作用:存储数据，并传递到Fragment中
        Bundle bundle = new Bundle();
        // 步骤5:往bundle中添加数据
        bundle.putString("message", "I love Google");
        // 步骤6:把数据设置到Fragment中
        fragment.setArguments(bundle);
        // 步骤7：动态添加fragment
        // 即将创建的fragment添加到Activity布局文件中定义的占位符中（FrameLayout）
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        //  Fragment ----> Activity
        textFragment = findViewById(R.id.text_fragment);
        sendActivity = findViewById(R.id.send_activity);
        // 步骤1：获取FragmentManager
        FragmentManager manager = getSupportFragmentManager();
        // 步骤2：获取FragmentTransaction
        FragmentTransaction transaction = manager.beginTransaction();
        // 步骤3：创建需要添加的Fragment
        final LeftFragment aFragment = new LeftFragment();
        // 步骤4：动态添加fragment
        // 即将创建的fragment添加到Activity布局文件中定义的占位符中（FrameLayout）
        transaction.add(R.id.fragment_container, aFragment);
        transaction.commit();
        sendActivity.setOnClickListener(v -> {
            // 通过接口回调将消息从fragment发送到Activity
            aFragment.sendMessage(new ICallBack() {
                @Override
                public void getMessageFromFragment(String string) {
                    textFragment.setText(string);
                }
            });
        });
    }
}

