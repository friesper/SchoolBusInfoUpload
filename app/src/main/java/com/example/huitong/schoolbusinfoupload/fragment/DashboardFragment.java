package com.example.huitong.schoolbusinfoupload.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.huitong.schoolbusinfoupload.R;
import com.example.huitong.schoolbusinfoupload.adapter.showStudentNameAdapter;
import com.example.huitong.schoolbusinfoupload.enity.Student;
import com.example.huitong.schoolbusinfoupload.util.DatabaseUtil;

import java.util.ArrayList;

/**
 * Created by yinxu on 2018/4/4.
 */

public class DashboardFragment extends Fragment implements View.OnClickListener  {
    static String tag="DashboardFragment";
    protected static final int REUEST_CODDE = 0;
    showStudentNameAdapter showStudentNameAdapters;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    ArrayList<Student> arrayList;
    LinearLayoutManager layoutManager;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dash_board_fragment,container,false);
        recyclerView=view.findViewById(R.id.studentLists);
        arrayList=getStudentInfoFromDb();
        floatingActionButton=view.findViewById(R.id.additem);
        layoutManager = new LinearLayoutManager(getContext());
        showStudentNameAdapters=new showStudentNameAdapter(getContext(),arrayList);
        recyclerView.setAdapter(showStudentNameAdapters);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        floatingActionButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.additem:
                ViewDialogFragment viewDialogFragment = new ViewDialogFragment();
                viewDialogFragment.setTargetFragment(this,1);
                viewDialogFragment.show(getFragmentManager());
                break;

            default:
                break;
        }
    }
    ArrayList<Student> getStudentInfoFromDb(){
        ArrayList<Student> arrayLis=new ArrayList<>();
        Student student;
        DatabaseUtil databaseUtil=new DatabaseUtil(getContext(),null,1);
        SQLiteDatabase sqLiteDatabase=databaseUtil.getWritableDatabase();
        Log.d(tag,sqLiteDatabase.getPath());
        Cursor cursor = sqLiteDatabase.query("student", new String[] { "id","name",
                "phone" },null, null, null, null, null);
        while (cursor.moveToNext()){
            Integer id=cursor.getInt(cursor.getColumnIndex("id"));
            String name=cursor.getString(cursor.getColumnIndex("name"));
            String phone= cursor.getString(cursor.getColumnIndex("phone"));
            student=new Student();
            student.setId(id);
            student.setName(name);
            student.setPhone(phone);
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
            String sql="insert into   student (name,phone) values('"+name+"','"+phone+"')";
            DatabaseUtil databaseUtil=new DatabaseUtil(getContext(),null,1);
            SQLiteDatabase sqLiteDatabase=databaseUtil.getWritableDatabase();
            sqLiteDatabase.execSQL(sql);
            sqLiteDatabase.close();
            arrayList=getStudentInfoFromDb();
            showStudentNameAdapters.setArrayList(arrayList);
            recyclerView.refreshDrawableState();
            Toast.makeText(getContext(), "添加成功", Toast.LENGTH_LONG).show();
        }
        }
    }


