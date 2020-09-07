package cn.edu.scujcc.worktwoweek.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import cn.edu.scujcc.worktwoweek.R;

/**
 * @author Administrator
 */
public class TwoLeftFragment extends Fragment {
    private TextView receiveText;
    private String message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_two_left_fragment, container, false);
        receiveText = view.findViewById(R.id.receive_activity);
        Button receiveButton = view.findViewById(R.id.receive_activity_button);
        // 步骤1:通过getArguments()获取从Activity传过来的全部值
        Bundle bundle = this.getArguments();
        // 步骤2:获取某一值
        message = bundle.getString("message");
        // 步骤3:设置按钮,将设置的值显示出来
        receiveButton.setOnClickListener(v -> {
            // 显示传递过来的值
            receiveText.setText(message);
        });
        return view;
    }
}