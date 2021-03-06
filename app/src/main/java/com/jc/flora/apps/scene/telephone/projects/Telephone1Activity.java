package com.jc.flora.apps.scene.telephone.projects;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.scene.telephone.delegate.TelephoneDelegate;

/**
 * Created by Samurai on 2017/7/2.
 */
public class Telephone1Activity extends AppCompatActivity{

    private View mBtnTelephone;
    private TextView mTvTelephone;

    private TelephoneDelegate mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("拨打电话");
        setContentView(R.layout.activity_telephone);
        findViews();
        initDelegate();
    }

    private void findViews(){
        mBtnTelephone = findViewById(R.id.btn_telephone);
        mTvTelephone = (TextView) findViewById(R.id.tv_telephone);
    }

    private void initDelegate(){
        mDelegate = new TelephoneDelegate(this, mBtnTelephone, mTvTelephone);
    }

    public void changeTelephoneInfo(View v) {
        final String[] ITEMS = {"15896458428", "025-87654321", "10086", "025-87654321-9999", ""};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择电话");
        builder.setItems(ITEMS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mDelegate.setTelephoneInfo(ITEMS[which]);
            }
        });
        builder.setCancelable(true);
        builder.show();
    }

}