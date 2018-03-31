package com.example.huitong.schoolbusinfoupload.activity;

import android.app.Activity;
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

public class LoginActivity extends AppCompatActivity {
    public static String SPFILENAME="userInfo";
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
        final String url="http://192.168.1.2/mobile/login";
        final User user=new User();
        user.setUserName(username.getText().toString());
        user.setPassWord(password.getText().toString());
        Log.d("LoginActivity",com.alibaba.fastjson.JSON.toJSONString(user));
        final OkHttpClient httpClient= new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)    .readTimeout(5,TimeUnit.SECONDS).build();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Login(username, password, user, url, httpClient, map);*/
                loginss(username, password, url, user);
            }
        });

    }

    private void loginss(EditText username, EditText password, String url, User user) {
        if (TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(password.getText())) {
            Toast.makeText(LoginActivity.this,"账号和密码不能为空",Toast.LENGTH_LONG).show();
        } else {
            user.setUserName(username.getText().toString());
            user.setPassWord(password.getText().toString());
            Log.d("LoginActivity",com.alibaba.fastjson.JSON.toJSONString(user));
            RequestBody requestBody=RequestBody.create(JSON, com.alibaba.fastjson.JSON.toJSONString(user));
         mOkHttpClient=new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                        cookieStore.put(HttpUrl.parse("http://192.168.1.2/mobile/login"), cookies);
                        for(Cookie cookie:cookies){
                            System.out.println("cookie Name:"+cookie.name());
                            System.out.println("cookie Path:"+cookie.path());
                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(HttpUrl.parse("http://192.168.1.2/mobile/login"));
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

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                Log.d(tags, str);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                         Intent intent=new Intent();
                        intent.putExtra("userType","driver");
                        intent.putExtra("userInfo",str);
                        intent.setClass(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
            }

        });

    }
    }

    private void Login(EditText username, EditText password, final User user, String url, final OkHttpClient httpClient, final Map<String, String> map) {
        if (TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(password.getText())) {
            Toast.makeText(LoginActivity.this,"账号和密码不能为空",Toast.LENGTH_LONG).show();
        } else {
            user.setUserName(username.getText().toString());
            user.setPassWord(password.getText().toString());
            Log.d("LoginActivity",com.alibaba.fastjson.JSON.toJSONString(user));
            RequestBody requestBody=RequestBody.create(JSON, com.alibaba.fastjson.JSON.toJSONString(user));
            final Request request=new Request.Builder().post(requestBody).url(url).build();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        Response response = httpClient.newCall(request).execute();
                        SharedPreferences sharedPreferences=getSharedPreferences(SPFILENAME,MODE_PRIVATE);

                        if (response.isSuccessful()) {
                            AndroidUtil.savedUsernameAndPassword(sharedPreferences,user);
                            List<String> list=response.headers("set-cookie");
                            map.put("set-cookie",list.get(0));
                            Log.d("LoginActivity",list.get(0));
                            if (AndroidUtil.saveSession(sharedPreferences,map)){
                                String content= response.body().string();
                               if (content.contains("Driver")){
                                   JSONObject jsonObject = JSONObject.parseObject(content);
                                   String  message = (String) jsonObject.get("message");
                                   message=message.substring(message.indexOf('{'));
                                   message=message.replace('=',':');
                                   AndroidUtil.saveUserInfo(sharedPreferences,message);
                                   Log.d("LoginActivity","userInfo"+AndroidUtil.getUserInfo(sharedPreferences));
                                    final Intent intent=new Intent();
                                    intent.putExtra("userType","driver");
                                    intent.putExtra("userInfo",message);
                                    intent.setClass(LoginActivity.this,MainActivity.class);
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                               }
                            }else {
                                AndroidUtil.saveSession(sharedPreferences,map);
                            }
                        } else {
                            Log.d("LoginActivity", "login failed");
                        }

                    } catch (IOException e) {
                        Log.d("LoginActivity",e.getMessage());
                    }
                }
            }).start();
        }
    }


}
