package cn.edu.scujcc.worktwoweek.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import cn.edu.scujcc.worktwoweek.R;

public class ActivityFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);

        //  Activity ----> Fragment
        // 步骤1：获取FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 步骤2：获取FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // 步骤3：创建需要添加的Fragment
        final TwoLeftFragment fragment = new TwoLeftFragment();
        // 步骤4:创建Bundle对象
        // 作用:存储数据，并传递到Fragment中
        Bundle bundle = new Bundle();
        // 步骤5:往bundle中添加数据
        bundle.putString("message", "消息:我来自Activity");
        // 步骤6:把数据设置到Fragment中
        fragment.setArguments(bundle);
        // 步骤7：动态添加fragment
        // 即将创建的fragment添加到Activity布局文件中定义的占位符中（FrameLayout）
        fragmentTransaction.add(R.id.fragment_two_container, fragment);
        fragmentTransaction.commit();
    }
}