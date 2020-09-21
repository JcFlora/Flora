package com.jc.flora.apps.scene.pray.projects;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.pray.delegate.PrayBottomSheetDialogDelegate;
import com.jc.flora.apps.scene.pray.delegate.PrayDialogDelegate;
import com.jc.flora.apps.ui.dialog.delegate.BottomSheetDialogDelegate;
import com.jc.flora.apps.ui.dialog.delegate.CustomDialogDelegate2;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.ui.dialog.projects.BottomSheetDialogActivity;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shijincheng on 2020/9/17.
 */
public class Pray2Activity extends AppCompatActivity {

    private PrayDialogDelegate mPrayDialogDelegate;
    private PrayBottomSheetDialogDelegate mPrayBottomSheetDialogDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("单页面+弹窗置灰");
        setContentView(R.layout.activity_pray2);
        mPrayDialogDelegate = new PrayDialogDelegate(this);
        showBottomSheetDialog();
    }

    private void showBottomSheetDialog(){
        TextView btnRole = (TextView) findViewById(R.id.btn_role);
        mPrayBottomSheetDialogDelegate = new PrayBottomSheetDialogDelegate(this);
        mPrayBottomSheetDialogDelegate.init(btnRole);
        mPrayBottomSheetDialogDelegate.setOnRoleCheckedListener(new PrayBottomSheetDialogDelegate.OnRoleCheckedListener() {
            @Override
            public void onRoleChecked(String role) {
                String content = "1".equals(role) ? "学生" : "老师";
                ToastDelegate.show(Pray2Activity.this, "你选择了" + content + "角色");
            }
        });
    }

    public void openGrayFilter(View v){
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);
        mPrayDialogDelegate.getContentView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);
        mPrayBottomSheetDialogDelegate.getContentView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);
    }

    public void closeGrayFilter(View v){
        getWindow().getDecorView().setLayerType(View.LAYER_TYPE_NONE, null);
        mPrayDialogDelegate.getContentView().setLayerType(View.LAYER_TYPE_NONE, null);
        mPrayBottomSheetDialogDelegate.getContentView().setLayerType(View.LAYER_TYPE_NONE, null);
    }

    public void showCartoonDialog(View v){
        mPrayDialogDelegate.show();
    }

}