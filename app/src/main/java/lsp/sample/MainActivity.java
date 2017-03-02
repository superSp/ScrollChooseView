package lsp.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import lsp.scrollchooseview.ScrollChooseView;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ScrollChooseView scrollChooseView;
    String titles[] = new String[]{"早餐前", "早餐后", "午餐前", "午餐后", "晚餐前", "晚餐后", "睡前"};
    private int picIds[] = new int[]{
            R.mipmap.time_bg_breakfastbefore, R.mipmap.time_bg_breakfastafter,
            R.mipmap.time_bg_lunchbefore, R.mipmap.time_bg_lunchafter,
            R.mipmap.time_bg_dinnerbefor, R.mipmap.time_bg_dinnerafter,
            R.mipmap.time_bg_sleep

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollChooseView = (ScrollChooseView) findViewById(R.id.id_scv);

        scrollChooseView.setTitles(titles);

        scrollChooseView.setPicIds(picIds);

        scrollChooseView.setOnScrollEndListener(new ScrollChooseView.OnScrollEndListener() {
            @Override
            public void currentPosition(int position) {
                Log.d(TAG, "当前positin=" + position + " " + titles[position]);
            }
        });

    }
}
