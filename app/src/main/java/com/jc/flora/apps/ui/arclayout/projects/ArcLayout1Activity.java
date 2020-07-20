package com.jc.flora.apps.ui.arclayout.projects;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.ogaclejapan.arclayout.Arc;
import com.ogaclejapan.arclayout.ArcLayout;

/**
 * Created by shijincheng on 2017/2/8.
 */
public class ArcLayout1Activity extends AppCompatActivity {

    /** 弧形布局位置 */
    private static final String[] LAYOUT_ITEMS = {"上中", "右上", "右中", "右下", "下中", "左下", "左中", "左上", "居中"};
    /** 弧形布局位置 */
    private static final Arc[] ARC_LAYOUT_ORIGIN = {Arc.TOP, Arc.TOP_RIGHT,
            Arc.RIGHT, Arc.BOTTOM_RIGHT, Arc.BOTTOM, Arc.BOTTOM_LEFT,
            Arc.LEFT, Arc.TOP_LEFT, Arc.CENTER};

    /** 弧形布局 */
    private ArcLayout mArcLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("基础弧形布局");
        setContentView(R.layout.activity_arclayout1);
        Button btnChangeLayout = (Button) findViewById(R.id.btn_change_layout);
        btnChangeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLayoutDialog();
            }
        });
        mArcLayout = (ArcLayout) findViewById(R.id.arc_layout);
        for (int i = 0, count = mArcLayout.getChildCount(); i < count; i++) {
            final int j = i;
            mArcLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastDelegate.show(ArcLayout1Activity.this, "你点击了第" + (j + 1) + "个标签");
                }
            });
        }
    }

    /** 显示修改布局对话框 */
    private void showChangeLayoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("修改弧形布局位置");
        builder.setIcon(R.mipmap.ic_arc);
        builder.setItems(LAYOUT_ITEMS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mArcLayout.setArc(ARC_LAYOUT_ORIGIN[which]);
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

}
