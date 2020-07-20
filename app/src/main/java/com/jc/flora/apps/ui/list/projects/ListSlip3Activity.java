package com.jc.flora.apps.ui.list.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.ui.list.adapter.SlipAdapter3;
import com.jc.flora.apps.ui.list.model.Slip3;
import com.jc.flora.apps.ui.list.widget.SlipsView;

import java.util.ArrayList;

/**
 * Created by shijincheng on 2017/9/4.
 */
public class ListSlip3Activity extends AppCompatActivity {

    private SlipsView mSlips0, mSlips1, mSlips2, mSlips3;
    private ArrayList<Slip3> mSlipList0, mSlipList1, mSlipList2, mSlipList3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加多种模板");
        setContentView(R.layout.activity_list_slip3);
        findViews();
        initData();
        initViews();
    }

    private void findViews() {
        mSlips0 = (SlipsView)findViewById(R.id.slips0);
        mSlips1 = (SlipsView)findViewById(R.id.slips1);
        mSlips2 = (SlipsView)findViewById(R.id.slips2);
        mSlips3 = (SlipsView)findViewById(R.id.slips3);
    }

    private void initData() {
        mSlipList0 = new ArrayList<>(2);
        mSlipList1 = new ArrayList<>(3);
        mSlipList2 = new ArrayList<>(3);
        mSlipList3 = new ArrayList<>(2);
        addSlipBottomSubtitle(mSlipList0, "小倩", "心有猛虎，细嗅蔷薇", R.mipmap.ic_login);
        addSlipBottomSubtitle(mSlipList0, "姥姥", "会当凌绝顶，一览众山小", R.mipmap.ic_login);
        addSlip(mSlipList1, "健康统计", R.mipmap.ic_render_jk);
        addSlip(mSlipList1, "我的购物", R.mipmap.ic_render_shop);
        addSlip(mSlipList1, "精品推荐", R.mipmap.ic_render_jp);
        addSlipEndSubtitle(mSlipList2, "心情日记", "0", R.mipmap.ic_render_rj);
        addSlipEndSubtitle(mSlipList2, "我的账单", "收入0.0元", R.mipmap.ic_render_zd);
        addSlipSwitch(mSlipList2, "活动提醒", R.mipmap.ic_render_tx);
        addSlipEndSubtitle(mSlipList3, "星座运势", "巨蟹座:不佳", R.mipmap.ic_render_xz);
        addSlipEndSubtitle(mSlipList3, "今日天气", "南京:中雨", R.mipmap.ic_render_tq);
    }

    private void initViews() {
        mSlips0.setAdapter(new SlipAdapter3(this, mSlipList0));
        mSlips1.setAdapter(new SlipAdapter3(this, mSlipList1));
        mSlips2.setAdapter(new SlipAdapter3(this, mSlipList2));
        mSlips3.setAdapter(new SlipAdapter3(this, mSlipList3));
        setOnItemClick(mSlips0, mSlipList0);
        setOnItemClick(mSlips1, mSlipList1);
        setOnItemClick(mSlips2, mSlipList2);
        setOnItemClick(mSlips3, mSlipList3);
    }

    private void addSlip(ArrayList<Slip3> list, String title, int iconResId) {
        Slip3 slip = new Slip3();
        slip.iconResId = iconResId;
        slip.title = title;
        list.add(slip);
    }

    private void addSlipEndSubtitle(ArrayList<Slip3> list, String title, String subTitle, int iconResId) {
        Slip3 slip = new Slip3();
        slip.slipType = Slip3.SlipType.END_SUBTITLE;
        slip.iconResId = iconResId;
        slip.title = title;
        slip.subTitle = subTitle;
        list.add(slip);
    }

    private void addSlipBottomSubtitle(ArrayList<Slip3> list, String title, String subTitle, int iconResId) {
        Slip3 slip = new Slip3();
        slip.slipType = Slip3.SlipType.BOTTOM_SUBTITLE;
        slip.iconResId = iconResId;
        slip.title = title;
        slip.subTitle = subTitle;
        list.add(slip);
    }

    private void addSlipSwitch(ArrayList<Slip3> list, String title, int iconResId){
        Slip3 slip = new Slip3();
        slip.slipType = Slip3.SlipType.SWITCH;
        slip.iconResId = iconResId;
        slip.title = title;
        list.add(slip);
    }

    private void setOnItemClick(SlipsView slipsView, final ArrayList<Slip3> list) {
        slipsView.setOnItemClickListener(new SlipsView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Slip3 slip = list.get(position);
                if(slip.slipType != Slip3.SlipType.SWITCH){
                    ToastDelegate.show(ListSlip3Activity.this, "你点击了" + list.get(position).title);
                }
            }
        });
    }

}