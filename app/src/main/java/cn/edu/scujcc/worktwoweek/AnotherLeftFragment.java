package cn.edu.scujcc.worktwoweek;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AnotherLeftFragment extends Fragment {
    Button receiveButton;
    TextView receiveText;
    Bundle bundle;
    String message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.another_left_fragment, container, false);
        receiveText = view.findViewById(R.id.receive_activity);
        receiveButton = view.findViewById(R.id.receive_activity_button);
        // 步骤1:通过getArgments()获取从Activity传过来的全部值
        bundle = this.getArguments();

        // 步骤2:获取某一值
        message = bundle.getString("message");

        // 步骤3:设置按钮,将设置的值显示出来
        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 显示传递过来的值
                receiveText.setText(message);

            }
        });
        return view;

    }

    /**
     * @param callBack 设置接口回调
     */
    public void sendMessage(ICallBack callBack) {
        callBack.getMessageFromFragment("消息:我来自Fragment");
    }
}