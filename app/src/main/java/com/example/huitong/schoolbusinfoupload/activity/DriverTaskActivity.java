package com.example.huitong.schoolbusinfoupload.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.huitong.schoolbusinfoupload.R;
import com.example.huitong.schoolbusinfoupload.adapter.BusInfoAdapter;
import com.example.huitong.schoolbusinfoupload.enity.BusInfo;
import com.example.huitong.schoolbusinfoupload.enity.Driver;
import com.example.huitong.schoolbusinfoupload.enity.User;
import com.example.huitong.schoolbusinfoupload.util.AndroidUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yinxu on 2018/3/29.
 */

public class DriverTaskActivity extends AppCompatActivity implements View.OnClickListener {
    public static String tag="DriverTaskActivity";
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    Handler handler=new Handler();
    LinearLayoutManager layoutManager;
    RecyclerView busInfoList;
    BusInfoAdapter busInfoAdapter;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_task);
         busInfoAdapter=new BusInfoAdapter(this);
         busInfoList=(RecyclerView)findViewById(R.id.BusInfo);
         layoutManager = new LinearLayoutManager(this );
//设置布局管理器
        busInfoList.setLayoutManager(layoutManager);
//设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        busInfoList.setAdapter(busInfoAdapter);
        //设置分隔线
        busInfoList.addItemDecoration( new DividerItemDecoration(this,layoutManager.HORIZONTAL));
//设置增加或删除条目的动画
        busInfoList.setItemAnimator( new DefaultItemAnimator());
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Tip");
        progressDialog.setMessage("数据初始化");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        Button button=findViewById(R.id.submitTask);
        sharedPreferences=getSharedPreferences("USERINFO",MODE_PRIVATE);
        button.setOnClickListener(this);

    }





    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submitTask:
                upLoadInfo();
                break;
            default:
                break;
        }

    }

    private void upLoadInfo() {
        ArrayList arrayList = busInfoAdapter.getContents();
        BusInfo busInfo = setInfo(arrayList);
        String cookie = AndroidUtil.getCookie(sharedPreferences);
        Log.d(tag, cookie);
        RequestBody requestBody = RequestBody.create(JSON, com.alibaba.fastjson.JSON.toJSONString(busInfo));
        Log.d(tag, com.alibaba.fastjson.JSON.toJSONString(busInfo));
        String url = "http://192.168.1.2/admin/info/bus/info/upload";
        final Request request = new Request.Builder().post(requestBody)
                .url(url)
                .build();
        Call call =   LoginActivity.mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "请求shibai", Toast.LENGTH_SHORT).show();

                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                Log.i("wangshu", str);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder alertDialog=new AlertDialog.Builder(DriverTaskActivity.this);
                        alertDialog.setMessage("提交成功，返回首页");
                        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        alertDialog.setCancelable(false);
                        alertDialog.create().show();
                        Log.d(tag,str);
                    }
                });
            }

        });







    }
    private BusInfo setInfo(ArrayList arrayList) {

        String userinfo=AndroidUtil.getUserInfo(sharedPreferences);
        Driver driver=  com.alibaba.fastjson.JSON.parseObject(userinfo, new TypeReference<Driver>() {});
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
             date = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BusInfo busInfo=new BusInfo();
        busInfo.setAirFilter((String) arrayList.get(1));
        busInfo.setAmountOfAntifreeze((String) arrayList.get(9));
        busInfo.setBakeFluid((String)arrayList.get(10));
        busInfo.setBatteryHealth((String)arrayList.get(2));
        busInfo.setBeltTightness((String)arrayList.get(11));
        busInfo.setBrake((String)arrayList.get(16));
        busInfo.setClutch((String)arrayList.get(15));
        busInfo.setCreateTime(date);
        busInfo.setEngineHygiene((String)arrayList.get(0));
        busInfo.setEscapeDoor((String)arrayList.get(6));
        busInfo.setFireExtinguisher((String)arrayList.get(5));
        busInfo.setGpsMonitoring((String)arrayList.get(4));
        busInfo.setGuideBoard((String)arrayList.get(14));
        busInfo.setInstrumentPanel((String)arrayList.get(18));
        busInfo.setLights((String)arrayList.get(13));
        busInfo.setMedicineBox((String)arrayList.get(3));
        busInfo.setOillOilLevel((String)arrayList.get(8));
        busInfo.setSafetyHammer((String)arrayList.get(7));
        busInfo.setSteeringWheel((String)arrayList.get(17));
        busInfo.setTirePressureScrews((String)arrayList.get(12));
        busInfo.setBusId(driver.getBusId());
        busInfo.setBusNumber(driver.getBusNumber());
        busInfo.setDriverName(driver.getName());
        Log.d(tag,busInfo.toString());
        Log.d(tag,userinfo);
        Log.d(tag,driver.toString());
        return busInfo;
    }
}
