package com.example.huitong.schoolbusinfoupload.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.huitong.schoolbusinfoupload.R;
import com.example.huitong.schoolbusinfoupload.fragment.DriverFragment;
import com.example.huitong.schoolbusinfoupload.fragment.DriverInfoFragment;
import com.example.huitong.schoolbusinfoupload.fragment.InfoFragment;
import com.example.huitong.schoolbusinfoupload.fragment.NurseStudentList;
import com.example.huitong.schoolbusinfoupload.util.AndroidUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yinxu on 2018/3/28.
 */

public class MainActivity extends BaseActivity {
    private BottomNavigationView navigation;
    static String  tag="MainActivity";
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment[] fragments;
    private int lastShowFragment = 0;
    String userType,userInfo;
    ProgressDialog progressDialog;
    private long exitTime = 0;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userType=getIntent().getStringExtra("userType");
        userInfo=getIntent().getStringExtra("userInfo");
        navigation = (BottomNavigationView)findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Tip");
        progressDialog.setMessage("数据初始化");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        initFragments();
        initUserInfo();



    }

    private void initUserInfo() {
        userType=getIntent().getStringExtra("userType");
        if (userType.equals("driver")){
            Log.d("MainActivity",userInfo);
            JSONObject jsonObject = JSONObject.parseObject(userInfo);
           String  data = (String) jsonObject.get("data");
            data=data.substring(data.indexOf('{'));
            data=data.replace('=',':');
            jsonObject=JSONObject.parseObject(data);
            Integer id= (Integer) jsonObject.get("id");
             String name=jsonObject.get("name").toString();
            String phone=(String)jsonObject.get("phone").toString();
            Integer busId=(Integer)jsonObject.get("busId");
            String busNumber=jsonObject.get("busNumber").toString();
             SharedPreferences sharedPreferences=getSharedPreferences(AndroidUtil.SPFILENAME,MODE_PRIVATE);
            AndroidUtil.saveInfo(sharedPreferences,"id",id);
            AndroidUtil.saveInfo(sharedPreferences,"busId",busId);
            AndroidUtil.saveInfo(sharedPreferences,"busNumber",busNumber);
            AndroidUtil.saveInfo(sharedPreferences,"name",name);
            AndroidUtil.saveInfo(sharedPreferences,"phone",phone);
            progressDialog.dismiss();

        }
        else if (userType.equals("nurse")){
            Log.d("MainActivity",userInfo);
            JSONObject jsonObject = JSONObject.parseObject(userInfo);
            String  data =  jsonObject.get("data").toString();
            data=data.substring(data.indexOf('{'));
            data=data.replace('=',':');
            jsonObject=JSONObject.parseObject(data);
            Integer id= (Integer) jsonObject.get("id");
            final String name=jsonObject.get("name").toString();
            String phone=(String)jsonObject.get("phone").toString();
            final SharedPreferences sharedPreferences=getSharedPreferences(AndroidUtil.SPFILENAME,MODE_PRIVATE);
            AndroidUtil.saveInfo(sharedPreferences,"id",id);
            AndroidUtil.saveInfo(sharedPreferences,"name",name);
            AndroidUtil.saveInfo(sharedPreferences,"phone",phone);
            String url=AndroidUtil.host+"/admin/nurse/mobile/relationInfo/"+id;
            Log.d("MainActivity","id"+Integer.toString(id));
            final Request request = new Request.Builder()
                    .url(url)
                    .build();
            Call call =   LoginActivity.mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("MainActivity","on Failure");
                            Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String str = response.body().string();
                    JSONObject jsonObject1=JSONObject.parseObject(str);
                    String data=jsonObject1.getString("data");
                    if (data!=null&&!data.equals("")) {
                        Log.d(tag,"data++"+data);
                        JSONArray jsonArray=JSONArray.parseArray(data);
                        if (jsonArray.size()>0){
                        String content1= jsonArray.get(0).toString();
                        try {
                            if (!content1.equals("")) {
                                JSONObject jsonObject2 = JSONObject.parseObject(content1);
                                Integer busId = (Integer) jsonObject2.get("busId");
                                String number = (String) jsonObject2.get("busNumber");
                                String name = (String) jsonObject2.get("name");
                                Integer driverId = (Integer) jsonObject2.get("id");
                                AndroidUtil.saveInfo(sharedPreferences, "driverId", driverId);
                                AndroidUtil.saveInfo(sharedPreferences, "busId", busId);
                                AndroidUtil.saveInfo(sharedPreferences, "driverName", name);
                                AndroidUtil.saveInfo(sharedPreferences, "busNumber", number);
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,"请联系管理员完善个人信息",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"请管理员在后台绑定车辆信息",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    });
                }

            });
        }

    }

    public void switchFragment(int lastIndex, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastIndex]);
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.fragment_container, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    private void initFragments() {
        fragment1 = new DriverFragment();
        if (userType.equals("driver")){
            fragment2 = new DriverInfoFragment();
        }else {
            fragment2 = new NurseStudentList();
        }fragment3 = new InfoFragment();
        fragments = new Fragment[]{fragment1, fragment2, fragment3};
        lastShowFragment = 0;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment1)
                .show(fragment1)
                .commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (lastShowFragment != 0) {
                        setTitle("主页");
                        switchFragment(lastShowFragment, 0);
                        lastShowFragment = 0;
                    }
                    return true;
                case R.id.navigation_dashboard:
                    if (lastShowFragment != 1) {
                        if (userType.equals("driver")) {
                            setTitle("当日学生接送信息");
                        }else {
                            setTitle("接送学生名单");
                        }
                        switchFragment(lastShowFragment, 1);
                        lastShowFragment = 1;
                    }
                    return true;
                case R.id.navigation_notifications:
                    if (lastShowFragment != 2) {
                       setTitle("个人信息");
                        switchFragment(lastShowFragment, 2);
                        lastShowFragment = 2;
                    }
                    return true;
            }
            return false;
        }

    };
    @Override
    public void onBackPressed() {
        exit();
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            BaseActivity.sendFinishActivityBroadcast(this);
            finish();
        }
    }

}
