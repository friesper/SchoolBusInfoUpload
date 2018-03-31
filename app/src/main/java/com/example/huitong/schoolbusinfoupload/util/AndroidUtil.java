package com.example.huitong.schoolbusinfoupload.util;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.huitong.schoolbusinfoupload.activity.LoginActivity;
import com.example.huitong.schoolbusinfoupload.activity.MainActivity;
import com.example.huitong.schoolbusinfoupload.enity.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.huitong.schoolbusinfoupload.activity.LoginActivity.JSON;

/**
 * Created by yinxu on 2018/3/28.
 */

public class AndroidUtil {
    public static final  String   SPFILENAME ="USERINFO";
    public static boolean saveSession(SharedPreferences sharedPreferences, Map<String,String> map){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Iterator iterator=map.keySet().iterator();
        String  name;
        while (iterator.hasNext()){
            name=(String)iterator.next();
            editor.putString(name,map.get(name));
            Log.d("AndroidUtil","session"+map.get(name));
        }
        return  editor.commit();
    }
    public static  boolean saveUserTyoe(SharedPreferences sharedPreferences, String userType){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if (userType!=null) {
            editor.putString("userType", userType);
        }
        return  editor.commit();

    }
    public static boolean savedUsernameAndPassword(SharedPreferences sharedPreferences, User user){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if (user!=null) {
            editor.putString("username", user.getUserName());
            editor.putString("password",user.getPassWord());
        }
        return  editor.commit();


    }
    public static User getUserNameAndPassword(SharedPreferences sharedPreferences){
        User user=new User();
        user.setPassWord(sharedPreferences.getString("password",""));
        user.setUserName(sharedPreferences.getString("username",""));
        return user;

    }
    public static  String userType(SharedPreferences sharedPreferences){
        String userType ;
        userType=sharedPreferences.getString("userType","");
        return userType;
    }
    public static  boolean saveUserInfo(SharedPreferences sharedPreferences, String userInfo){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if (userInfo!=null) {
            editor.putString("userInfo", userInfo);
        }
        return  editor.commit();

    }
    public static  String getUserInfo(SharedPreferences sharedPreferences){
        String userInfo ;
        userInfo=sharedPreferences.getString("userInfo","");
        return userInfo;
    }
    public static  String getCookie(SharedPreferences sharedPreferences){
        String cookie;
        cookie=sharedPreferences.getString("set-cookie","");
        Log.d("AndroidUtil","cookie         "+cookie);
        return cookie;
    }

    public  static boolean login(final Context context, final Handler handler, final Activity activity, final ProgressDialog progressDialog){
        final SharedPreferences sharedPreferences=context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        final String url="http://192.168.1.2/mobile/login";
        final HashMap map=new HashMap();
        final OkHttpClient httpClient= new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)    .readTimeout(5,TimeUnit.SECONDS).build();
        User user=getUserNameAndPassword(sharedPreferences);
        Log.d("AndroidUtil","username       "+user.getPassWord()+"userpassword"+user.getUserName());
        RequestBody requestBody=RequestBody.create(JSON, com.alibaba.fastjson.JSON.toJSONString(user));
        final Request request=new Request.Builder().post(requestBody).url(url).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = httpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
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
                            }
                        }else {
                            AndroidUtil.saveSession(sharedPreferences,map);
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                            progressDialog.dismiss();
                            }
                        });

                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
                                alertDialog.setMessage("账户信息过期，请重新登陆");
                                alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent();
                                        intent.setClass(context,LoginActivity.class);
                                        context.startActivity(intent);
                                        activity.finish();
                                    }
                                });
                                alertDialog.setCancelable(false);
                                alertDialog.create().show();

                            }
                        });
                    }

                } catch (IOException e) {
                    Log.d("LoginActivity",e.getMessage());
                }
            }
        }).start();
        return false;
    }



}
