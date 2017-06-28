package com.jc.flora.apps.ui.marquee.projects;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;
import com.jc.flora.apps.ui.marquee.widget.AutoSwitchTextView;

public class NoticeSwitchAdapter implements AutoSwitchTextView.SwitchAdapter {

	private String[] mData = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };

	private Context mContext;

	public NoticeSwitchAdapter(String[] data, Context context) {
		super();
		mContext = context;
	}

	@Override
	public int getCount() {
		return mData.length;
	}

	@Override
	public int getAnimDuration() {
		return 800;
	}

	@Override
	public long getSwitchDuration() {
		return 2000;
	}

	@Override
	public View getView() {
		TextView t = new TextView(mContext);
		t.setGravity(Gravity.CENTER);
		t.setTextSize(50);
		t.setMaxLines(2);
		return t;
	}
	
	@Override
	public String getShowContent(int position) {
		return mData[position];
	}

	@Override
	public void onSwitchItemClicked(AutoSwitchTextView parent, int position) {
		ToastDelegate.show(mContext, mData[position]);
	}

}
