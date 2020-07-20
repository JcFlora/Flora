package com.jc.flora.apps.component.vi.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.vi.fidelity.Fidelity;

/**
 * Created by Samurai on 2017/6/7.
 */
public class ViFidelityActivity extends AppCompatActivity {

    private TextView mTvHifiResolution;
    private TextView mTvScreenResolution;
    private ImageView mIvViImage;
    private TextView mTvViText14;
    private TextView mTvViText20;
    private TextView mTvViText30;
    private TextView mTvViDimens200px;
    private TextView mTvViDimens400px;
    private TextView mTvViDimens200dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用Fidelity组件适配");
        setContentView(R.layout.activity_vi_fidelity);
        findViews();
        initViews();
    }

    private void findViews(){
        mTvHifiResolution = (TextView) findViewById(R.id.tv_hifi_resolution);
        mTvScreenResolution = (TextView) findViewById(R.id.tv_screen_resolution);
        mIvViImage = (ImageView) findViewById(R.id.iv_vi_image);
        mTvViText14 = (TextView) findViewById(R.id.tv_vi_text_14);
        mTvViText20 = (TextView) findViewById(R.id.tv_vi_text_20);
        mTvViText30 = (TextView) findViewById(R.id.tv_vi_text_30);
        mTvViDimens200px = (TextView) findViewById(R.id.tv_vi_dimens_200px);
        mTvViDimens400px = (TextView) findViewById(R.id.tv_vi_dimens_400px);
        mTvViDimens200dp = (TextView) findViewById(R.id.tv_vi_dimens_200dp);
    }

    private void initViews(){
        // 高保真组件初始化，真实项目中请在Application的onCreate()方法中调用
        Fidelity.init(this,720,1280);
        // 打印设计稿的标准分辨率
        mTvHifiResolution.setText("设计稿标准分辨率：" + 720 + "X" + 1280);

        // 获取默认的高保真组件（默认以宽度基准进行适配）
        Fidelity fidelity = Fidelity.getInstance(this);
        // 获取手机实际宽度和高度
        String screenResolution = fidelity.getScreenWidth() + "X" + fidelity.getScreenHeight();
        // 打印当前手机的屏幕分辨率
        mTvScreenResolution.setText("当前屏幕分辨率："+screenResolution);

        // 适配图片，设计稿尺寸为600x400
        fidelity.setWidthHeight(mIvViImage, 600, 400);
        // 适配字体，设计稿尺寸14px
        fidelity.setTextSize(mTvViText14, 14);
        // 适配字体，设计稿尺寸20px
        fidelity.setTextSize(mTvViText20, 20);
        // 适配字体，设计稿尺寸30px
        fidelity.setTextSize(mTvViText30, 30);

        // 适配设计稿200px的尺寸
        int dimens200px = (int) (fidelity.hifi2px(200) + 0.5);
        mTvViDimens200px.setText("200px适配后的实际尺寸：" + dimens200px);
        // 适配设计稿400px的尺寸
        int dimens400px = (int) (fidelity.hifi2px(400) + 0.5);
        mTvViDimens400px.setText("400px适配后的实际尺寸：" + dimens400px);

        // 获取200dp在当前手机上的px值
        int dimens200dp = (int) (fidelity.dp2px(200) + 0.5);
        mTvViDimens200dp.setText("200dp适配后的实际尺寸：" + dimens200dp);
    }

    @Override
    protected void onDestroy() {
        // 释放资源，真实项目中请在Application的onDestroy()方法中调用
        Fidelity.destroy();
        super.onDestroy();
    }
}
