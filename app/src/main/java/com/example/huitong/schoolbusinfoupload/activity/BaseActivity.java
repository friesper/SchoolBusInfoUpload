package com.example.huitong.schoolbusinfoupload.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yinxu on 2018/4/6.
 */

public class BaseActivity extends AppCompatActivity {
    public static final String RECEIVER_ACTION_FINISH_A = "action_a";
    public static final String RECEIVER_ACTION_FINISH_B = "action_b";
    private FinishActivityRecevier mRecevier;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecevier = new FinishActivityRecevier();
        registerFinishReciver();
    }
    private void registerFinishReciver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVER_ACTION_FINISH_A);
        intentFilter.addAction(RECEIVER_ACTION_FINISH_B);
        registerReceiver(mRecevier, intentFilter);
    }

    private class FinishActivityRecevier extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //根据需求添加自己需要关闭页面的action
            if (RECEIVER_ACTION_FINISH_A.equals(intent.getAction()) ||
                    RECEIVER_ACTION_FINISH_B.equals(intent.getAction()) ) {
                BaseActivity.this.finish();
            }
        }
    }
    @Override
    protected void onDestroy() {
        if (mRecevier != null) {
            unregisterReceiver(mRecevier);
        }
        super.onDestroy();
    }
    public static void sendFinishActivityBroadcast(Context context) {
        Intent intent = new Intent(BaseActivity.RECEIVER_ACTION_FINISH_A);
        context.sendBroadcast(intent);
        intent = new Intent(BaseActivity.RECEIVER_ACTION_FINISH_B);
        context.sendBroadcast(intent);
    }
}
