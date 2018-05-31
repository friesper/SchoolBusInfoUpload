package com.example.huitong.schoolbusinfoupload.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.huitong.schoolbusinfoupload.R;
import com.example.huitong.schoolbusinfoupload.adapter.showStudentNameAdapter;
import com.example.huitong.schoolbusinfoupload.enity.Student;
import com.example.huitong.schoolbusinfoupload.util.DatabaseUtil;

import java.util.ArrayList;

/**
 * Created by yinxu on 2018/4/4.
 */

public class NurseStudentList extends FragmentActivity implements View.OnClickListener ,ViewDialogFragment.NoticeDialogListener {
    public static String SPFILENAME="userInfo";
    static String tag="DashboardFragment";
    protected static final int REUEST_CODDE = 0;
    showStudentNameAdapter showStudentNameAdapters;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    ArrayList<Student> arrayList;
    String username;
    LinearLayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list);
        sharedPreferences=getSharedPreferences(SPFILENAME,MODE_PRIVATE);
        recyclerView=findViewById(R.id.studentLists);
        username=sharedPreferences.getString("userName","");
        if (sharedPreferences!=null) {
            Log.d(tag, "username" + username);
        }
        arrayList=getStudentInfoFromDb();
        floatingActionButton=findViewById(R.id.additem);
        layoutManager = new LinearLayoutManager(this);
        showStudentNameAdapters=new showStudentNameAdapter(this,arrayList,username);
        recyclerView.setAdapter(showStudentNameAdapters);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        floatingActionButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.additem:
                ViewDialogFragment viewDialogFragment = new ViewDialogFragment();
                viewDialogFragment.show(getSupportFragmentManager(),"loginDialog");
                break;

            default:
                break;
        }
    }

    ArrayList<Student> getStudentInfoFromDb(){
        ArrayList<Student> arrayLis=new ArrayList<>();
        Student student;
        DatabaseUtil databaseUtil=new DatabaseUtil(this,username ,null,1);
        SQLiteDatabase sqLiteDatabase=databaseUtil.getWritableDatabase();
        Log.d(tag,sqLiteDatabase.getPath());
        Cursor cursor = sqLiteDatabase.query("student", new String[] { "id","name",
                "phone","address","distance" },null, null, null, null, null);
        while (cursor.moveToNext()){
            Integer id=cursor.getInt(cursor.getColumnIndex("id"));
            String name=cursor.getString(cursor.getColumnIndex("name"));
            String phone= cursor.getString(cursor.getColumnIndex("phone"));
            String address= cursor.getString(cursor.getColumnIndex("address"));
            String distance= cursor.getString(cursor.getColumnIndex("distance"));
            student=new Student();
            student.setId(id);
            student.setName(name);
            student.setPhone(phone);
            student.setAddress(address);
            student.setDistance(distance);
            Log.d(tag,student.toString());
            arrayLis.add(student);
        }
        sqLiteDatabase.close();
        return arrayLis;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==0) {
            String name=data.getStringExtra("name");
            String phone=data.getStringExtra("phone");
            String address=data.getStringExtra("address");
            String distance=data.getStringExtra("distance");
            String sql="insert into   student (name,phone,address,distance) values('"+name+"','"+phone+"','"+address+"','"+distance+"')";
            DatabaseUtil databaseUtil=new DatabaseUtil(this,username,null,1);
            SQLiteDatabase sqLiteDatabase=databaseUtil.getWritableDatabase();
            sqLiteDatabase.execSQL(sql);
            sqLiteDatabase.close();
            arrayList=getStudentInfoFromDb();
            showStudentNameAdapters.setArrayList(arrayList);
            recyclerView.refreshDrawableState();
            Toast.makeText(this, "添加成功", Toast.LENGTH_LONG).show();
        }
        }

    @Override
    public void onDialogPositiveClick(Intent data) {
        String name=data.getStringExtra("name");
        String phone=data.getStringExtra("phone");
        String address=data.getStringExtra("address");
        String distance=data.getStringExtra("distance");
        String sql="insert into   student (name,phone,address,distance) values('"+name+"','"+phone+"','"+address+"','"+distance+"')";
        DatabaseUtil databaseUtil=new DatabaseUtil(this,username,null,1);
        SQLiteDatabase sqLiteDatabase=databaseUtil.getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
        arrayList=getStudentInfoFromDb();
        showStudentNameAdapters.setArrayList(arrayList);
        recyclerView.refreshDrawableState();
        Toast.makeText(this, "添加成功", Toast.LENGTH_LONG).show();
        Log.d(tag,address+distance);
    }

    @Override
    public void onDialogNegativeClick(Intent intent) {

    }
}


