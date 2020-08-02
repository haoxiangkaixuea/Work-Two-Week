package cn.edu.scujcc.worktwoweek;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author Administrator
 */
public class LeftFragment extends Fragment {
    EditText etFmLeft;
    Button buLeft;
    DataListener dataListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_left_fragment, container, false);
        etFmLeft = view.findViewById(R.id.ed);
        buLeft = view.findViewById(R.id.left);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etFmLeft.getText().toString();
                dataListener.showData(data);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataListener = (DataListener) getActivity();
    }

    public interface DataListener {
        void showData(String data);
    }
}
