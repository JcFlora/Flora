package com.jc.flora.apps.ui.dialog.multi_dialog;

import android.content.DialogInterface;

/**
 * Created by shijincheng on 2020/9/29.
 */
public interface MultipleShow {
    void show();

    void dismiss();

    void setOnDisMissListener(DialogInterface.OnDismissListener onDismissListener);
}