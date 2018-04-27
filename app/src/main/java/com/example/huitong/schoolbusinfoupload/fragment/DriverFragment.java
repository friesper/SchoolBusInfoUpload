package com.example.huitong.schoolbusinfoupload.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
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

import static android.content.Context.MODE_PRIVATE;
import static com.example.huitong.schoolbusinfoupload.util.AndroidUtil.SPFILENAME;

/**
 * Created by yinxu on 2018/3/28.
 */

public class DriverFragment extends Fragment implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    public static String  driverFragmentTag="DriverFragment";
    final String url=AndroidUtil.host+"/admin/school/schoolList";
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
    @TargetApi(23)
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        sharedPreferences=getActivity().getSharedPreferences(SPFILENAME,MODE_PRIVATE);

    }
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        sharedPreferences=getActivity().getSharedPreferences(SPFILENAME,MODE_PRIVATE);
    }

        @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.startTask:
                Intent intent=new Intent();
                if (userType!=null&&userType.equals("driver")){
                    Log.d(driverFragmentTag,"userType" +userType);
                    Integer busId=sharedPreferences.getInt("busId",0);
                    if (busId==0){
                        Toast.makeText(getActivity(),"未绑定车辆信息,请联系管理员绑定信息",Toast.LENGTH_LONG).show();

                    }
                    else {
                    intent.setClass(getActivity(),DriverTaskActivity.class);
                    startActivity(intent);
                    }
                }
                else {
                    if (userType!=null&&userType.equals("nurse")){
                        Integer busId=sharedPreferences.getInt("busId",0);
                        String busNumber=sharedPreferences.getString("busNumber","");
                        Integer nurseId=sharedPreferences.getInt("id",0);
                        String nurseName=sharedPreferences.getString("name","");
                        String DriverName=sharedPreferences.getString("driverName","");
                        Integer drivreId=sharedPreferences.getInt("driverId",0);
                        Log.d(driverFragmentTag,"userType" +userType);
                        if (busId==0||nurseId==0||drivreId==0) {
                                Toast.makeText(getActivity(),"未绑定车辆信息,请联系管理员绑定信息",Toast.LENGTH_LONG).show();
                        }
                        else {
                        intent.setClass(getActivity(), NurseTaskActivity.class);
                        startActivity(intent);
                        }
                    }
                }
                break;
                default:
                    break;
        }
        
    }

}
