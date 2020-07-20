package com.jc.flora.apps.ui.dialog.delegate;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.Arrays;

/**
 * implementation 'com.contrarywind:Android-PickerView:4.1.3'
 * Created by Samurai on 2018/5/17.
 */
public class PickerDelegate {

    private static final String[] ITEMS = {"泰国", "新加坡", "印度尼西亚"};

    private AppCompatActivity mActivity;
    private TextView mBtn;
    private OptionsPickerView<String> mPicker;
    private int mSelectedIndex = -1;

    private OnPickerItemSelectedListener mItemSelectedListener;

    public PickerDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    public void init(TextView button){
        mBtn = button;
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker();
            }
        });
    }

    public void setOnItemSelectedListener(OnPickerItemSelectedListener l) {
        mItemSelectedListener = l;
    }

    public void setSelectedIndex(int selectedIndex) {
        setSelectedIndex(selectedIndex, false);
    }

    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    private void setSelectedIndex(int selectedIndex, boolean byUser) {
        if(selectedIndex < 0){
            return;
        }
        mSelectedIndex = selectedIndex;
        mBtn.setText(ITEMS[selectedIndex]);
        if(mItemSelectedListener != null){
            mItemSelectedListener.onItemSelected(selectedIndex, byUser);
        }
    }

    private void showPicker(){
        if(mPicker == null){
            mPicker = new OptionsPickerBuilder(mActivity, mOnOptionsSelectListener).build();
            mPicker.setPicker(Arrays.asList(ITEMS));
        }
        mPicker.setSelectOptions(mSelectedIndex > 0 ? mSelectedIndex : 0);
        mPicker.show();
    }

    private OnOptionsSelectListener mOnOptionsSelectListener = new OnOptionsSelectListener() {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            setSelectedIndex(options1, true);
        }
    };

    public interface OnPickerItemSelectedListener{
        void onItemSelected(int which, boolean byUser);
    }

}