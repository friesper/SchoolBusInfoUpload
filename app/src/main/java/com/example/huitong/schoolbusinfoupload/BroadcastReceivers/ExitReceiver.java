package com.example.huitong.schoolbusinfoupload.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.huitong.schoolbusinfoupload.activity.BaseActivity;

/**
 * Created by yinxu on 2018/4/6.
 */

public class ExitReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
                if("com.example.huitong.exit".equals(intent.getAction())){
                }
    }
}
