package com.example.huitong.schoolbusinfoupload.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
                    intent.setClass(getActivity(),DriverTaskActivity.class);
                    startActivity(intent);
                }
                else {
                }
        }
        
    }

    private void Login() {
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(LoginActivity.SPFILENAME, Context.MODE_PRIVATE);
        String cookie=AndroidUtil.getCookie(sharedPreferences);
        Log.d(driverFragmentTag,cookie);
        final OkHttpClient httpClient= new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)    .readTimeout(5,TimeUnit.SECONDS).build();
        final Request request=new Request.Builder().headers(new Headers.Builder().add("Cookie",cookie).build()).get().url(url).build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = httpClient.newCall(request).execute();

                    if (response.isSuccessful()){
                        String content=response.body().string();

                        if (content.contains("Login")){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                Toast.makeText(getActivity(),"登陆失效，请重新登陆",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent();
                                intent.setClass(getActivity(),LoginActivity.class);
                                startActivity(intent);
                                }
                            });
                        }
                        else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent=new Intent();
                                    intent.setClass(getActivity(),DriverTaskActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                        Log.d(driverFragmentTag,content);
                        Log.d(driverFragmentTag,String.valueOf(response.code()));
                    }else {
                        Log.d(driverFragmentTag,"failed");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(driverFragmentTag,e.getMessage());
                }
            }
        }).start();

    }
}
