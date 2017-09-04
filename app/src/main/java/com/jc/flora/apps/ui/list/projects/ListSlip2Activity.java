package com.jc.flora.apps.ui.list.projects;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.ui.list.adapter.SlipAdapter1;
import com.jc.flora.apps.ui.list.model.Slip1;
import com.jc.flora.apps.ui.list.widget.SlipsView;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/9/3.
 */
public class ListSlip2Activity extends AppCompatActivity {

    private SlipsView mSlips1, mSlips2, mSlips3;
    private ArrayList<Slip1> mSlipList1, mSlipList2, mSlipList3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("封装成SlipsView");
        setContentView(R.layout.activity_list_slip2);
        findViews();
        initData();
        initViews();
    }

    private void findViews() {
        mSlips1 = (SlipsView)findViewById(R.id.slips1);
        mSlips2 = (SlipsView)findViewById(R.id.slips2);
        mSlips3 = (SlipsView)findViewById(R.id.slips3);
    }

    private void initData() {
        mSlipList1 = new ArrayList<>(3);
        mSlipList2 = new ArrayList<>(3);
        mSlipList3 = new ArrayList<>(2);
        addSlip(mSlipList1, "健康统计", R.mipmap.ic_render_jk);
        addSlip(mSlipList1, "我的购物", R.mipmap.ic_render_shop);
        addSlip(mSlipList1, "精品推荐", R.mipmap.ic_render_jp);
        addSlip(mSlipList2, "心情日记", R.mipmap.ic_render_rj);
        addSlip(mSlipList2, "我的账单", R.mipmap.ic_render_zd);
        addSlip(mSlipList2, "活动提醒", R.mipmap.ic_render_tx);
        addSlip(mSlipList3, "星座运势", R.mipmap.ic_render_xz);
        addSlip(mSlipList3, "今日天气", R.mipmap.ic_render_tq);
    }

    private void initViews() {
        mSlips1.setAdapter(new SlipAdapter1(this, mSlipList1));
        mSlips2.setAdapter(new SlipAdapter1(this, mSlipList2));
        mSlips3.setAdapter(new SlipAdapter1(this, mSlipList3));
        setOnItemClick(mSlips1, mSlipList1);
        setOnItemClick(mSlips2, mSlipList2);
        setOnItemClick(mSlips3, mSlipList3);
    }

    private void addSlip(ArrayList<Slip1> list, String title, int iconResId) {
        Slip1 slip = new Slip1();
        slip.iconResId = iconResId;
        slip.title = title;
        list.add(slip);
    }

    private void setOnItemClick(SlipsView slipsView, final ArrayList<Slip1> list) {
        slipsView.setOnItemClickListener(new SlipsView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ToastDelegate.show(ListSlip2Activity.this, "你点击了" + list.get(position).title);
            }
        });
    }

}