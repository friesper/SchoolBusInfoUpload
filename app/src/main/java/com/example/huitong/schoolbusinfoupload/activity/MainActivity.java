package com.example.huitong.schoolbusinfoupload.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.internal.NavigationMenu;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.huitong.schoolbusinfoupload.R;
import com.example.huitong.schoolbusinfoupload.fragment.DriverFragment;
import com.example.huitong.schoolbusinfoupload.util.AndroidUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yinxu on 2018/3/28.
 */

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigation;
    static String  tag="MainActivity";
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment[] fragments;
    private int lastShowFragment = 0;
    String userType,userInfo;
    ProgressDialog progressDialog;
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
            String name= jsonObject.get("name").toString();
            progressDialog.dismiss();

        }
        else if (userType.equals("nurse")){
            JSONObject jsonObject = JSONObject.parseObject(userInfo);
            String  data =  jsonObject.get("data").toString();
            data=data.substring(data.indexOf('{'));
            data=data.replace('=',':');
            jsonObject=JSONObject.parseObject(data);
            Integer id= (Integer) jsonObject.get("id");
            final String name=jsonObject.get("name").toString();
            String phone=(String)jsonObject.get("phone");
            final SharedPreferences sharedPreferences=getSharedPreferences(AndroidUtil.SPFILENAME,MODE_PRIVATE);
            AndroidUtil.saveInfo(sharedPreferences,"nurseId",id);
            AndroidUtil.saveInfo(sharedPreferences,"nurseName",name);
            AndroidUtil.saveInfo(sharedPreferences,"nursePhone",phone);
            String url="http://192.168.1.2/admin/nurse/mobile/relationInfo/"+id;
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
                    if (!data.equals("")) {
                        Log.d(tag,"data++"+data);
                        JSONArray jsonArray=JSONArray.parseArray(data);
                        String content1= jsonArray.get(0).toString();
                        if (!content1.equals("")){
                            JSONObject jsonObject2=JSONObject.parseObject(content1);
                            Integer busId=(Integer)jsonObject2.get("id");
                            String number=(String)jsonObject2.get("number");
                            AndroidUtil.saveInfo(sharedPreferences,"busId",busId);
                            AndroidUtil.saveInfo(sharedPreferences,"busNumber",number);
                        }
                        String content2=  jsonArray.get(1).toString();

                        if (!content2.equals("")){
                            JSONObject jsonObject2=JSONObject.parseObject(content1);
                            Integer busId=(Integer)jsonObject2.get("id");
                            String name=(String)jsonObject2.get("name");
                            AndroidUtil.saveInfo(sharedPreferences,"driverId",busId);
                            AndroidUtil.saveInfo(sharedPreferences,"driverName",name);
                        }
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
        fragment2 = new Fragment();
        fragment3 = new Fragment();
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
                        switchFragment(lastShowFragment, 0);
                        lastShowFragment = 0;
                    }
                    return true;
                case R.id.navigation_dashboard:
                    if (lastShowFragment != 1) {
                        switchFragment(lastShowFragment, 1);
                        lastShowFragment = 1;
                    }
                    return true;
                case R.id.navigation_notifications:
                    if (lastShowFragment != 2) {
                        switchFragment(lastShowFragment, 2);
                        lastShowFragment = 2;
                    }
                    return true;
            }
            return false;
        }

    };




}
