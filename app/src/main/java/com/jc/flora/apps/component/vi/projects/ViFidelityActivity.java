package com.jc.flora.apps.component.vi.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.component.vi.fidelity.Fidelity;
import com.jc.flora.apps.component.vi.fidelity.ViSettingConstants;

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
    private TextView mTvViDimens200;
    private TextView mTvViDimens400;
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
        mTvViDimens200 = (TextView) findViewById(R.id.tv_vi_dimens_200);
        mTvViDimens400 = (TextView) findViewById(R.id.tv_vi_dimens_400);
        mTvViDimens200dp = (TextView) findViewById(R.id.tv_vi_dimens_200dp);
    }

    private void initViews(){

        // 获取设计稿标准宽度和高度的常量
        String hifiResolution = ViSettingConstants.HIFI_WIDTH+ "X" +ViSettingConstants.HIFI_HEIGHT;
        // 打印设计稿的标准分辨率
        mTvHifiResolution.setText("设计稿标准分辨率："+hifiResolution);

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
        int dimens200 = (int) (fidelity.getViDimen(200) + 0.5);
        mTvViDimens200.setText("200px适配后的实际尺寸：" + dimens200);
        // 适配设计稿400px的尺寸
        int dimens400 = (int) (fidelity.getViDimen(400) + 0.5);
        mTvViDimens400.setText("400px适配后的实际尺寸：" + dimens400);

        // 获取200dp在当前手机上的px值
        int dimens200dp = (int) (fidelity.dp2px(200) + 0.5);
        mTvViDimens200dp.setText("200dp适配后的实际尺寸：" + dimens200dp);
    }

}
