package com.jc.flora.apps.component.video.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jc.flora.R;
import com.jc.flora.apps.component.vi.fidelity.Fidelity;
import com.jc.flora.apps.component.video.model.MP4;

import java.util.List;

/**
 * Created by Shijincheng on 2019/3/28.
 */

public class VideoAdapter extends BaseQuickAdapter<MP4, BaseViewHolder> {

    /**  */
    private RenderAttacher mRenderAttacher;

    public VideoAdapter(List<MP4> data) {
        super(R.layout.item_video_list, data);
    }

    public void setRenderAttacher(RenderAttacher renderAttacher) {
        mRenderAttacher = renderAttacher;
    }

    // 重写这个方法，实现对视频容器的动态屏幕适配
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateViewHolder(parent, viewType);

        FrameLayout layoutVideoContainer = helper.getView(R.id.layout_video_container);
        Fidelity fidelity = Fidelity.getInstance(parent.getContext());
        double ivWidth = fidelity.getScreenWidth() - 2 * fidelity.dp2px(10);
        double ivHeight = ivWidth * 9/16;
        ViewGroup.LayoutParams params = layoutVideoContainer.getLayoutParams();
        params.width = (int)(ivWidth + 0.5f);
        params.height = (int)(ivHeight + 0.5f);
        layoutVideoContainer.setLayoutParams(params);

        return helper;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MP4 item) {
        TextView tvTitle = helper.getView(R.id.tv_video_name);
        ImageView ivAlbum = helper.getView(R.id.iv_album);
        View layoutAlbum = helper.getView(R.id.layout_album_cover);
        FrameLayout container = helper.getView(R.id.layout_video_container);

        tvTitle.setText(item.name);
        item.loadAlbum(ivAlbum);

        int position = helper.getAdapterPosition();
        if(mRenderAttacher != null){
            boolean isCurrentPlay = mRenderAttacher.addVideoRender(container, position);
            layoutAlbum.setVisibility(isCurrentPlay ? View.GONE : View.VISIBLE);
        }
    }

    public interface RenderAttacher{
        boolean addVideoRender(FrameLayout container, int position);
    }

}