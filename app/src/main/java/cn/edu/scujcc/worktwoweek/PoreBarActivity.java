package cn.edu.scujcc.worktwoweek;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author Administrator
 */
public class PoreBarActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progre_bar);

        final ProgressBar bar = (ProgressBar) findViewById(R.id.progress);
        final TextView textView = (TextView) findViewById(R.id.tvProgress);
        new Thread() {
            @Override
            public void run() {
                int i = 0;
                while (i < 100) {
                    i++;
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int j = i;
                    bar.setProgress(i);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(j + "%");
                        }
                    });
                }
            }
        }.start();
    }
}
