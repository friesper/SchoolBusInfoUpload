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
    public static final String host="http://123.207.53.122";
    public static final  String   SPFILENAME ="userInfo";
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
    public static  boolean saveUserType(SharedPreferences sharedPreferences, String userType){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if (userType!=null) {
            editor.putString("userType", userType);
        }
        return  editor.commit();

    }
    public static boolean saveInfo(SharedPreferences sharedPreferences,String name,String content){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(name, content);
        return editor.commit();
    }
    public static boolean saveInfo(SharedPreferences sharedPreferences,String name,Integer content){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(name, content);
        return editor.commit();
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




}
