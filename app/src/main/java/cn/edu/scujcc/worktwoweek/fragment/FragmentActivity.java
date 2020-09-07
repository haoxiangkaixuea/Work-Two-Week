package cn.edu.scujcc.worktwoweek.fragment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import cn.edu.scujcc.worktwoweek.R;

public class FragmentActivity extends AppCompatActivity {
    private TextView textFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        //  Fragment ----> Activity
        textFragment = findViewById(R.id.receive_fragment);
        Button sendActivity = findViewById(R.id.receive_fragment_button);
        // 步骤1：获取FragmentManager
        FragmentManager manager = getSupportFragmentManager();
        // 步骤2：获取FragmentTransaction
        FragmentTransaction transaction = manager.beginTransaction();
        // 步骤3：创建需要添加的Fragment
        final AnotherLeftFragment aFragment = new AnotherLeftFragment();
        // 步骤4：动态添加fragment
        // 即将创建的fragment添加到Activity布局文件中定义的占位符中（FrameLayout）
        transaction.add(R.id.fragment_container, aFragment);
        transaction.commit();
        sendActivity.setOnClickListener(v -> {
            // 通过接口回调将消息从fragment发送到Activity
            aFragment.sendMessage(string -> textFragment.setText(string));
        });
    }
}

