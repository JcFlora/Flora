package com.jc.flora.apps.ui.blur.projects;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jc.flora.R;

public class MyGalleryAdapter extends BaseAdapter {

	private Context mContext;

	public Drawable[] mBlurDrawables;

	public View[] mItemViews;

	private static final int[] IMAGE_RES_IDS = {R.mipmap.ic_blur,R.mipmap.ic_hybrid,R.mipmap.ic_net,R.mipmap.ic_splash,R.mipmap.ic_statistics,R.mipmap.ic_upgrade};

	public void buildBlurDrawables(int i) {
		if (mBlurDrawables[i] == null) {
			mItemViews[i].buildDrawingCache();
			mBlurDrawables[i] = BlurUtil.BoxBlurFilter(mItemViews[i].getDrawingCache());
			if (i < 3)
				notifyDataSetChanged();
		}
	}

	final Handler h = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			buildBlurDrawables(msg.what);
		}
	};

	public MyGalleryAdapter(Context context) {
		mContext = context;
		mItemViews = new View[6];
		mBlurDrawables = new Drawable[6];
	}

	@Override
	public int getCount() {
		return 6;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (mItemViews[position] == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_blur_main, null);
			convertView.setTag(position);
			mItemViews[position] = convertView;
		} else {
			convertView = mItemViews[position];
		}
		((ImageView)mItemViews[position].findViewById(R.id.imageView)).setImageResource(IMAGE_RES_IDS[position]);
		if (mBlurDrawables[position] == null) {
			h.post(new Runnable() {
				@Override
				public void run() {
					h.sendEmptyMessage(position);
				}
			});
		}
		TextView tv = (TextView) convertView.findViewById(R.id.tv);
		tv.setText(position + "----" + position + "-----");
		return convertView;
	}

}
