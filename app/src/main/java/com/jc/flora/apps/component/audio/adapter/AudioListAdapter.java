package com.jc.flora.apps.component.audio.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jc.flora.R;
import com.jc.flora.apps.component.audio.model.MP3;

import java.util.List;

/**
 * Created by Shijincheng on 2019/3/21.
 */

public class AudioListAdapter extends BaseQuickAdapter<MP3, BaseViewHolder> {

    public AudioListAdapter(List<MP3> data) {
        super(R.layout.item_audio_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MP3 item) {
        ImageView iv = helper.getView(R.id.iv_cover);
        TextView tvTitle = helper.getView(R.id.tv_name);

        iv.setImageResource(item.coverImgResId);
        tvTitle.setText(item.name);
    }

}