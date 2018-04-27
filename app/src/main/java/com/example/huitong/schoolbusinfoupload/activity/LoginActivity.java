package com.example.huitong.schoolbusinfoupload.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.huitong.schoolbusinfoupload.R;
import com.example.huitong.schoolbusinfoupload.enity.Driver;
import com.example.huitong.schoolbusinfoupload.enity.User;
import com.example.huitong.schoolbusinfoupload.util.AndroidUtil;
import com.example.huitong.schoolbusinfoupload.util.JumpTextWatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.huitong.schoolbusinfoupload.util.AndroidUtil.SPFILENAME;

/**
 * Created by yinxu on 2018/3/27.
 */

public class LoginActivity extends BaseActivity {
    public static String SPFILENAME="userInfo";
    ProgressDialog progressDialog;
    public  static  OkHttpClient mOkHttpClient;
    Handler handler=new Handler();
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    public  static String tags="loginActivity";
    @Override
    public void  onCreate(final Bundle savedInstanceState) {
        final Map<String ,String> map=new HashMap<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Log.d("loginACtivity","sadas");
        final EditText  username=(EditText)findViewById(R.id.username);
        final EditText password=(EditText)findViewById(R.id.password);
        Button login=(Button)findViewById(R.id.login);
        final String url=AndroidUtil.host+"/mobile/login";
        final User user=new User();
        user.setUserName(username.getText().toString());
        user.setPassWord(password.getText().toString());
        Log.d("LoginActivity",com.alibaba.fastjson.JSON.toJSONString(user));
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Tip");
        progressDialog.setMessage("正在登陆");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        SharedPreferences sharedPreferences=getSharedPreferences(SPFILENAME,MODE_PRIVATE);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Login(username, password, user, url, httpClient, map);*/
                progressDialog.show();
                loginss(username, password, url, user);
            }
        });
        String username_=sharedPreferences.getString("userName","");
        String password_=sharedPreferences.getString("userPassword","");
        Log.d(tags,"username+"+username_);
        Log.d(tags,"password+"+password_);
        if (!username_.equals("")&&!password_.equals("")){
            username.setText(username_);
            password.setText(password_);
            loginss(username, password, url, user);
        }


    }

    private void loginss(final EditText username, final EditText password, String url, final User user) {
        if (TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(password.getText())) {
            Toast.makeText(LoginActivity.this,"账号和密码不能为空",Toast.LENGTH_LONG).show();
        } else {
            progressDialog.show();
            user.setUserName(username.getText().toString());
            user.setPassWord(password.getText().toString());
            Log.d(tags,com.alibaba.fastjson.JSON.toJSONString(user));
            RequestBody requestBody=RequestBody.create(JSON, com.alibaba.fastjson.JSON.toJSONString(user));
         mOkHttpClient=new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                        cookieStore.put(HttpUrl.parse(AndroidUtil.host+"/mobile/login"), cookies);
                        for(Cookie cookie:cookies){
                            Log.d(tags,"cookie Name:"+cookie.name());
                            Log.d(tags,"cookie Path:"+cookie.path());
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(HttpUrl.parse(AndroidUtil.host+"/mobile/login"));
                        if(cookies==null){
                            System.out.println("没加载到cookie");
                        }
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"网络异常",Toast.LENGTH_LONG).show();
                        }
                    });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 404) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"账号或密码错误",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    });
                } else if (response.code() == 200) {
                    final String str = response.body().string();
                    Log.d(tags, str);
                    Log.d(tags,"username"+user.getUserName()+"password"+user.getPassWord());
                    AndroidUtil.saveInfo(getSharedPreferences(SPFILENAME,MODE_PRIVATE),"userName",user.getUserName());
                    AndroidUtil.saveInfo(getSharedPreferences(SPFILENAME,MODE_PRIVATE),"userPassword",user.getPassWord());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent();
                            if (str.contains("driver")) {
                                intent.putExtra("userType", "driver");
                            } else {
                                intent.putExtra("userType", "nurse");
                            }
                            intent.putExtra("userInfo", str);
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });


    }
    }



}
