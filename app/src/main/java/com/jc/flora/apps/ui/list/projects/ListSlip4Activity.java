package com.jc.flora.apps.ui.list.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.ui.list.adapter.RadioAdapter;
import com.jc.flora.apps.ui.list.model.Slip3;
import com.jc.flora.apps.ui.list.widget.SlipsView;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/9/4.
 */
public class ListSlip4Activity extends AppCompatActivity {

    private SlipsView mSlips;
    private ArrayList<Slip3> mSlipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加单选模式");
        setContentView(R.layout.activity_list_slip4);
        findViews();
        initData();
        initViews();
    }

    private void findViews() {
        mSlips = (SlipsView)findViewById(R.id.slips);
    }

    private void initData() {
        mSlipList = new ArrayList<>(4);
        addSlipRadio(mSlipList, "支付宝", "使用支付宝支付", R.mipmap.ic_payment_ali);
        addSlipRadio(mSlipList, "花呗支付", "使用花呗支付", R.mipmap.ic_payment_huabei);
        addSlipRadio(mSlipList, "QQ支付", "使用QQ支付", R.mipmap.ic_payment_qq);
        addSlipRadio(mSlipList, "微信支付", "使用微信支付", R.mipmap.ic_payment_wexin);
    }

    private void initViews() {
        mSlips.setAdapter(new RadioAdapter(this, mSlipList));
    }

    private void addSlipRadio(ArrayList<Slip3> list, String title, String subTitle, int iconResId) {
        Slip3 slip = new Slip3();
        slip.iconResId = iconResId;
        slip.title = title;
        slip.subTitle = subTitle;
        list.add(slip);
    }

}