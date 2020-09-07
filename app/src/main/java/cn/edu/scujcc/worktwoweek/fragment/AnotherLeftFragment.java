package cn.edu.scujcc.worktwoweek.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import cn.edu.scujcc.worktwoweek.R;

/**
 * @author Administrator
 */
public class AnotherLeftFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.another_left_fragment, container, false);
    }

    /**
     * @param callBack 设置接口回调
     */
    public void sendMessage(ICallBack callBack) {
        callBack.getMessageFromFragment("消息:我来自Fragment");
    }
}