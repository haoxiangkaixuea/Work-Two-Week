package cn.edu.scujcc.worktwoweek;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * @author Administrator
 */
public class LeftFragment extends Fragment {
    Button sendFragment;
    TextView text;
    Bundle bundle;
    String message;
    private TextView mTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_left_fragment, container, false);
//        sendFragment =view.findViewById(R.id.send_fragment);
//        text = view.findViewById(R.id.text_activity);
//        // 步骤1:通过getArguments()获取从Activity传过来的全部值
//        bundle = this.getArguments();
//        // 步骤2:获取某一值
//        message = bundle.getString("message");
//        // 步骤3:设置按钮,将设置的值显示出来
//        sendFragment.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                // 显示传递过来的值
//                text.setText(message);
//            }
//        });
        return view;
    }

    // 设置 接口回调 方法
    public void sendMessage(ICallBack callBack) {
        callBack.get_message_from_Fragment("消息:我来自Fragment");
    }
}
