package cn.edu.scujcc.worktwoweek;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity implements LeftFragment.DataListener {
    RightFragment rightFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        rightFragment = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.right_fragment);
    }

    @Override
    public void showData(String data) {
        rightFragment.showData(data);
    }
}