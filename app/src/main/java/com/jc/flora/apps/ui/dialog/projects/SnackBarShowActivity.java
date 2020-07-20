package com.jc.flora.apps.ui.dialog.projects;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.jc.flora.R;

/**
 * Created by shijincheng on 2017/3/1.
 */
public class SnackBarShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("SnackBar");
        setContentView(R.layout.activity_snack_bar);
    }

    public void showSnackBar(View v) {
        Snackbar sb = Snackbar.make(v, "加入到购物车成功", Snackbar.LENGTH_LONG);
        sb.setAction("去购物车", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        sb.getView().setBackgroundResource(android.R.color.holo_blue_light);
        sb.show();
    }

}