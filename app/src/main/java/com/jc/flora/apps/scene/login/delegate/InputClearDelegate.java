package com.jc.flora.apps.scene.login.delegate;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Samurai on 2017/8/24.
 */
public class InputClearDelegate {

    public static void init(final EditText et, final View btnClear) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    btnClear.setVisibility(View.GONE);
                } else {
                    btnClear.setVisibility(View.VISIBLE);
                }
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
            }
        });
    }

}
