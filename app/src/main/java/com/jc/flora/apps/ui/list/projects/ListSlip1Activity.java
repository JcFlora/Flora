package com.jc.flora.apps.ui.list.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.jc.flora.R;
import com.jc.flora.apps.ui.list.adapter.SlipAdapter1;
import com.jc.flora.apps.ui.list.model.Slip1;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/9/3.
 */
public class ListSlip1Activity extends AppCompatActivity {

    private LinearLayout mLayoutSlips1, mLayoutSlips2, mLayoutSlips3;
    private ArrayList<Slip1> mSlipList1, mSlipList2, mSlipList3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("列表组布局视图");
        setContentView(R.layout.activity_list_slip1);
        findViews();
        initData();
        initViews();
    }

    private void findViews() {
        mLayoutSlips1 = (LinearLayout)findViewById(R.id.layout_slips1);
        mLayoutSlips2 = (LinearLayout)findViewById(R.id.layout_slips2);
        mLayoutSlips3 = (LinearLayout)findViewById(R.id.layout_slips3);
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
        setAdapter(mLayoutSlips1, mSlipList1);
        setAdapter(mLayoutSlips2, mSlipList2);
        setAdapter(mLayoutSlips3, mSlipList3);
    }

    private void addSlip(ArrayList<Slip1> list, String title, int iconResId) {
        Slip1 slip = new Slip1();
        slip.iconResId = iconResId;
        slip.title = title;
        list.add(slip);
    }

    private void setAdapter(LinearLayout layout, ArrayList<Slip1> list){
        SlipAdapter1 adapter = new SlipAdapter1(this, list);
        for (int i = 0, count = adapter.getCount(); i < count; i++) {
            layout.addView(adapter.getView(i, null, layout));
        }
    }

}