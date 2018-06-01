package com.example.huitong.schoolbusinfoupload.util;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.huitong.schoolbusinfoupload.enity.User;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by yinxu on 2018/3/28.
 */

public class AndroidUtil {
    public static final String host="http://119.185.1.182:88";
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
