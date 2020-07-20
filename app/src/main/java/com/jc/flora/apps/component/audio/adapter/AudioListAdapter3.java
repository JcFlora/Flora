package com.jc.flora.apps.component.audio.adapter;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jc.flora.R;
import com.jc.flora.apps.component.audio.model.MP3;
import com.jc.flora.apps.ui.progress.widget.RoundProgressBar;

import java.util.List;

/**
 * Created by Shijincheng on 2019/3/21.
 */

public class AudioListAdapter3 extends BaseQuickAdapter<MP3, BaseViewHolder> {

    private int mCurrentPlayIndex = -1;
    private int mCurrentMax = -1;
    private int mCurrentProgress = -1;

    public AudioListAdapter3(List<MP3> data) {
        super(R.layout.item_audio_list2, data);
    }

    public void setCurrentPlayIndexAndMax(int currentPlayIndex, int currentMax) {
        mCurrentPlayIndex = currentPlayIndex;
        mCurrentMax = currentMax;
        mCurrentProgress = 0;
        notifyDataSetChanged();
    }

    public void setCurrentProgress(int currentPlayIndex, int currentProgress) {
        mCurrentProgress = currentProgress;
        notifyItemChanged(currentPlayIndex,"audio_item");
    }

    @Override
    protected void convert(BaseViewHolder helper, MP3 item) {
        ImageView iv = helper.getView(R.id.iv_cover);
        TextView tvTitle = helper.getView(R.id.tv_name);
        ImageView ivPlay = helper.getView(R.id.iv_play);
        RoundProgressBar pbPlay = helper.getView(R.id.pb_play);

        item.loadAlbum(iv);
        tvTitle.setText(item.name);

        int position = helper.getAdapterPosition();
        if(position == mCurrentPlayIndex){
            ivPlay.setImageResource(R.drawable.audio_item_playing);
            pbPlay.setVisibility(View.VISIBLE);
            if(mCurrentMax > 0){
                pbPlay.setMax(mCurrentMax);
            }
            if(mCurrentProgress >= 0){
                pbPlay.setProgress(mCurrentProgress);
            }
        }else{
            ivPlay.setImageResource(R.drawable.audio_item_ready);
            pbPlay.setVisibility(View.GONE);
            pbPlay.setMax(9_999_999);
            pbPlay.setProgress(0);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        if(payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads);
        }else{
            RoundProgressBar pbPlay = holder.getView(R.id.pb_play);
            if(mCurrentProgress >= 0){
                pbPlay.setProgress(mCurrentProgress);
            }
        }
    }
}