package com.jc.flora.apps.ui.dialog.projects;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.jc.flora.R;
import com.jc.flora.apps.ui.dialog.delegate.PickerDelegate;
import com.jc.flora.apps.ui.dialog.delegate.PickerDelegate2;
import com.jc.flora.apps.ui.dialog.delegate.PickerDelegate3;
import com.jc.flora.apps.ui.dialog.delegate.ToastDelegate;

/**
 * implementation 'com.contrarywind:Android-PickerView:4.1.3'
 * Created by shijincheng on 2018/5/17.
 */
public class PickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Picker");
        setContentView(R.layout.activity_dialog_picker);
        showOneColumnPicker();
        showLinkPicker();
        showNoLinkPicker();
        showDatePicker();
    }

    /**
     * 显示一列的Picker选择器
     */
    private void showOneColumnPicker(){
        TextView btn = (TextView) findViewById(R.id.btn_country);
        PickerDelegate delegate = new PickerDelegate(this);
        delegate.init(btn);
        delegate.setOnItemSelectedListener(new PickerDelegate.OnPickerItemSelectedListener() {
            @Override
            public void onItemSelected(int which, boolean byUser) {
                if(byUser){
                    ToastDelegate.show(PickerActivity.this, "你选择了第" + (which + 1) + "个");
                }
            }
        });
    }

    /**
     * 显示联动的Picker选择器
     */
    private void showLinkPicker(){
        TextView btn = (TextView) findViewById(R.id.btn_city);
        PickerDelegate2 delegate = new PickerDelegate2(this);
        delegate.init(btn);
        delegate.setOnItemSelectedListener(new PickerDelegate2.OnPickerItemSelectedListener() {
            @Override
            public void onItemSelected(int options1, int options2, boolean byUser) {
                if(byUser){
                    ToastDelegate.show(PickerActivity.this, "你选择了第" + (options1 + 1) + "个和第"+ (options2 + 1) + "个");
                }
            }
        });
    }

    /**
     * 显示非联动的Picker选择器
     */
    private void showNoLinkPicker(){
        TextView btn = (TextView) findViewById(R.id.btn_product);
        PickerDelegate3 delegate = new PickerDelegate3(this);
        delegate.init(btn);
        delegate.setOnItemSelectedListener(new PickerDelegate3.OnPickerItemSelectedListener() {
            @Override
            public void onItemSelected(int options1, int options2, int options3, boolean byUser) {
                if(byUser){
                    ToastDelegate.show(PickerActivity.this, "你选择了第" + (options1 + 1) + "个和第"+ (options2 + 1) + "个和第"+ (options3 + 1) + "个");
                }
            }
        });
    }

    /**
     * 显示日期选择的Picker选择器
     */
    private void showDatePicker(){

    }

}