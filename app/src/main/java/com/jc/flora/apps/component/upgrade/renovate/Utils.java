package com.jc.flora.apps.component.upgrade.renovate;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.io.File;

import androidx.core.content.FileProvider;

public class Utils {

    public static void installApk(Context context, String filePathName) {
        Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file = new File(filePathName);
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setDataAndType(FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file), type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
        context.startActivity(intent);
        if(context instanceof Activity){
            ((Activity)context).finish();
        }else if(context instanceof Service){
            ((Service)context).stopSelf();
        }
    }

}