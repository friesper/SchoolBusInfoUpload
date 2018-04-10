package com.example.huitong.schoolbusinfoupload.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.example.huitong.schoolbusinfoupload.R;
import com.example.huitong.schoolbusinfoupload.adapter.BusInfoAdapter;
import com.example.huitong.schoolbusinfoupload.adapter.StudentInfoAdapter;
import com.example.huitong.schoolbusinfoupload.enity.Student;
import com.example.huitong.schoolbusinfoupload.enity.StudentStatus;
import com.example.huitong.schoolbusinfoupload.util.AndroidUtil;
import com.example.huitong.schoolbusinfoupload.util.DatabaseUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.huitong.schoolbusinfoupload.activity.LoginActivity.JSON;

/**
 * Created by yinxu on 2018/3/30.
 */

public class NurseTaskActivity extends BaseActivity implements View.OnClickListener {
    static String tag="NurseTaskActivity";
    ProgressDialog progressDialog;
    Handler handler=new Handler();
    LinearLayoutManager layoutManager;
    RecyclerView studentInfoView;
    StudentInfoAdapter studentInfoAdapter;
    ArrayList<Student> studens;
    SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nurse);
        layoutManager = new LinearLayoutManager(this );
        studens=getStudentInfoFromDb();
        studentInfoAdapter =new StudentInfoAdapter(this,studens);
        studentInfoView=findViewById(R.id.nurse_student_list);
        studentInfoView.setLayoutManager(layoutManager);
        studentInfoView.setAdapter(studentInfoAdapter);
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Tip");
        progressDialog.setMessage("数据初始化");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        sharedPreferences=getSharedPreferences(AndroidUtil.SPFILENAME,MODE_PRIVATE);
        Button button=findViewById(R.id.student_submit);
        button.setOnClickListener(this);



    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.student_submit:
                progressDialog.show();
                uploadInfo();
                break;

            default:
                break;
        }

    }
    ArrayList<Student> getStudentInfoFromDb(){
        ArrayList<Student> arrayLis=new ArrayList<>();
        Student student;
        DatabaseUtil databaseUtil=new DatabaseUtil(this,null,1);
        SQLiteDatabase sqLiteDatabase=databaseUtil.getWritableDatabase();
        Log.d(tag,sqLiteDatabase.getPath());
        Cursor cursor = sqLiteDatabase.query("student", new String[] { "name",
                "phone" },null, null, null, null, null);
        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex("name"));
            String phone= cursor.getString(cursor.getColumnIndex("phone"));
            student=new Student();
            student.setName(name);
            student.setPhone(phone);
            arrayLis.add(student);
        }
        sqLiteDatabase.close();
        return arrayLis;
    }
    void  uploadInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                JSONArray jsonArray=new JSONArray();
                Integer busId=sharedPreferences.getInt("busId",0);
                String busNumber=sharedPreferences.getString("busNumber","");
                Integer nurseId=sharedPreferences.getInt("id",0);
                String nurseName=sharedPreferences.getString("name","");
                String DriverName=sharedPreferences.getString("driverName","");
                Integer drivreId=sharedPreferences.getInt("driverId",0);
                long time = System.currentTimeMillis();
                final Calendar mCalendar = Calendar.getInstance();
                mCalendar.setTimeInMillis(time);

                int hour = mCalendar.get(Calendar.HOUR);
                Date date=mCalendar.getTime();
                int apm = mCalendar.get(Calendar.AM_PM);

                StudentStatus studentStatus;
                for (int i=0;i<studens.size();i++){
                    studentStatus=new StudentStatus();
                    studentStatus.setStudentName(studens.get(i).getName());
                    studentStatus.setStudentPhone(studens.get(i).getPhone());
                    studentStatus.setBusId(busId);
                    studentStatus.setBusNumber(busNumber);
                    studentStatus.setDriverId(drivreId);
                    studentStatus.setDriverName(DriverName);
                    studentStatus.setNurseId(nurseId);
                    studentStatus.setNurseName(nurseName);
                    studentStatus.setTakeTime(date);
                    studentStatus.setTimeQuantum(apm);
                    if (studens.get(i).getStatus()){
                        studentStatus.setStatus(1);
                    }
                    else {
                        studentStatus.setStatus(0);
                    }
                    jsonArray.add(studentStatus);

                }

                final String url=AndroidUtil.host+"/admin/info/status/info/upload";
                RequestBody requestBody = RequestBody.create(JSON, jsonArray.toJSONString());

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
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"提交失败",Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String str = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                AlertDialog.Builder alertDialog=new AlertDialog.Builder(NurseTaskActivity.this);
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
        }).start();

    }
}
