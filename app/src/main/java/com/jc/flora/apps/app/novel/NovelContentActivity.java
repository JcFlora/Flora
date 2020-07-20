package com.jc.flora.apps.app.novel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.jc.flora.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samurai on 2017/6/24.
 */
public class NovelContentActivity extends AppCompatActivity {

    private TextView tv9;
    public static final String ENCODING = "gb2312";
    private Handler handler;
    private List<Integer> list = new ArrayList<>();
    private ZoomControls zc;
    float textSize = 15;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra("title"));
        setContentView(R.layout.activity_novel_content);
        tv9 = (TextView) findViewById(R.id.tv9);
        zc = (ZoomControls) findViewById(R.id.zc);
        tv9.setTextSize(textSize);

        list.add(R.raw.jtzy_1);
        list.add(R.raw.jtzy_2);
        list.add(R.raw.jtzy_3);
        list.add(R.raw.jtzy_4);
        list.add(R.raw.jtzy_5);
        list.add(R.raw.jtzy_6);
        list.add(R.raw.jtzy_7);
        list.add(R.raw.jtzy_8);
        list.add(R.raw.jtzy_9);
        list.add(R.raw.jtzy_10);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                tv9.setText(String.valueOf(msg.obj));
            }
        };

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = getIntent();
                InputStream in = getResources().openRawResource(list.get(intent.getIntExtra("id", 0)));
                try {
                    byte[] data = new byte[in.available()];
                    in.read(data);
                    String str = new String(data,ENCODING).replace("\r", "");
                    Message msg = new Message();
                    msg.obj = str;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };
        new Thread(myRunnable).start();

        zc.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textSize < 25) {
                    textSize += 1;
                    tv9.setTextSize(textSize);
                } else {
                    Toast.makeText(NovelContentActivity.this, "不能再大了", 1500).show();
                }
            }
        });

        zc.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textSize > 10) {
                    textSize -= 1;
                    tv9.setTextSize(textSize);
                } else {
                    Toast.makeText(NovelContentActivity.this, "不能再小了", 1500).show();
                }
            }
        });
    }

}
