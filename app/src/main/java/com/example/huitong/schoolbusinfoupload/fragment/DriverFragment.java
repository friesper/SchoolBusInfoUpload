package com.example.huitong.schoolbusinfoupload.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.huitong.schoolbusinfoupload.R;
import com.example.huitong.schoolbusinfoupload.activity.DriverTaskActivity;
import com.example.huitong.schoolbusinfoupload.activity.LoginActivity;
import com.example.huitong.schoolbusinfoupload.activity.NurseTaskActivity;
import com.example.huitong.schoolbusinfoupload.util.AndroidUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yinxu on 2018/3/28.
 */

public class DriverFragment extends Fragment implements View.OnClickListener {


    public static String  driverFragmentTag="DriverFragment";
    final String url="http://192.168.1.2/admin/school/schoolList";
    String userType;
    Handler handler=new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.layout_driver,container,false);
        ImageButton button=(ImageButton)view.findViewById(R.id.startTask);
         userType=getActivity().getIntent().getStringExtra("userType");
        Intent intent=new Intent();
        intent.setClass(getActivity().getApplicationContext(), DriverTaskActivity.class);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.startTask:
                Intent intent=new Intent();
                if (userType!=null&&userType.equals("driver")){
                    Log.d(driverFragmentTag,"userType" +userType);
                    intent.setClass(getActivity(),DriverTaskActivity.class);
                    startActivity(intent);
                }
                else {
                    if (userType!=null&&userType.equals("nurse")){
                        Log.d(driverFragmentTag,"userType" +userType);
                        intent.setClass(getActivity(), NurseTaskActivity.class);
                        startActivity(intent);
                    }
                }
                break;
                default:
                    break;
        }
        
    }

}
