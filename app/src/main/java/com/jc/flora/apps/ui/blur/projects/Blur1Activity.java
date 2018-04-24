package com.jc.flora.apps.ui.blur.projects;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jc.flora.R;

/**
 * Created by shijincheng on 2017/1/20.
 */
public class Blur1Activity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("高斯模糊");
        setContentView(R.layout.activity_blur1);
        final Button btn = (Button) findViewById(R.id.button);
        final Button btn2 = (Button) findViewById(R.id.button2);
        final Button btn3 = (Button) findViewById(R.id.button3);

        final ImageView iv = (ImageView) findViewById(R.id.imageView);
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btn.setDrawingCacheEnabled(true);
                btn.setBackgroundDrawable(BlurUtil.BoxBlurFilter(btn.getDrawingCache()));
                btn.setText("");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btn.destroyDrawingCache();
                btn.setBackgroundDrawable(null);
                btn.setText("我是一个粉刷匠，");
            }
        });
        final MyGallery myGallery = (MyGallery) findViewById(R.id.gallery);
        MyGalleryAdapter adapter = new MyGalleryAdapter(this);
        myGallery.setAdapter(adapter);
        myGallery.setVisibility(View.INVISIBLE);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myGallery.setVisibility(View.VISIBLE);
            }
        },150);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    //	public static Bitmap convertViewToBitmap(View view){
//	  //view.measure(MeasureSpec.makeMeasureSpec(0,0), MeasureSpec.makeMeasureSpec(0,0));
//	  //view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//	  view.buildDrawingCache();
//	  Bitmap bitmap = view.getDrawingCache();
//	  return bitmap;
//	}
//
//    public static Bitmap convertViewToBitmap(View view, int bitmapWidth, int bitmapHeight){
//        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
//        view.draw(new Canvas(bitmap));
//        return bitmap;
//    }

}
