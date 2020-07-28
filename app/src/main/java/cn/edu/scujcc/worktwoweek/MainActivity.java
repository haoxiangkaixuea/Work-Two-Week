package cn.edu.scujcc.worktwoweek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLeft = findViewById(R.id.left);
        mLeft.setOnClickListener(this);
        //调用replaceFragment把RightFragment换成AnotherFragment。
        replaceFragment(new RightFragment());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //点击左侧按钮，把右侧right换成anotherright
            case R.id.left:
                replaceFragment(new AnotherRight());
                break;
            default:
                break;
        }
    }

    //创建待添加的碎片实例
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
}