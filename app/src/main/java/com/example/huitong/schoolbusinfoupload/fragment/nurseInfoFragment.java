package com.example.huitong.schoolbusinfoupload.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.huitong.schoolbusinfoupload.R;
import com.example.huitong.schoolbusinfoupload.activity.LoginActivity;
import com.example.huitong.schoolbusinfoupload.activity.MainActivity;
import com.example.huitong.schoolbusinfoupload.adapter.DriverInfoAdapter;
import com.example.huitong.schoolbusinfoupload.enity.StudentStatus;
import com.example.huitong.schoolbusinfoupload.util.AndroidUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.huitong.schoolbusinfoupload.fragment.NurseStudentList.SPFILENAME;

public class nurseInfoFragment extends Fragment implements View.OnClickListener {

    String userType;
    String tag="nurseInfoFragment";
    RecyclerView recyclerView;
    Button total_date,yesterDayDate;ImageButton studnetList;
    ArrayList<StudentStatus> studentStatuses;
    LinearLayoutManager layoutManager;
    DriverInfoAdapter driverInfoAdapter;
    ArrayList<StudentStatus>  infolist=new ArrayList<StudentStatus>();
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.nurse_info_fragment,container,false);
        sharedPreferences=getActivity().getSharedPreferences(SPFILENAME,MODE_PRIVATE);
        userType=getActivity().getIntent().getStringExtra("userType");
        recyclerView=view.findViewById(R.id.nurse_student_info_list);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        driverInfoAdapter=new DriverInfoAdapter(getContext(),infolist);
        recyclerView.setAdapter(driverInfoAdapter);
        recyclerView.setLayoutManager(layoutManager);
        total_date=view.findViewById(R.id.total_date);
        yesterDayDate=view.findViewById(R.id.yesterday_date);
        yesterDayDate.setOnClickListener(this);
        total_date.setOnClickListener(this);
        total_date.setClickable(false);
        studnetList=view.findViewById(R.id.student_list);
        studnetList.setOnClickListener(this);
        getInfo(new Date());
        driverInfoAdapter.notifyDataSetChanged();
        return view;
    }
    public void getInfo(Date dates){
        final ArrayList<StudentStatus> arrayList = new ArrayList<>();
        Integer busId=sharedPreferences.getInt("busId",0);
        if (busId!=null){
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String date=simpleDateFormat.format(dates);
            final String url= AndroidUtil.host+"/admin/info/mobile/student/find/"+busId+"/"+date;
            Log.d(tag,"url"+url);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d(tag,url);
                    final Request request = new Request.Builder().get()
                            .url(url)
                            .build();
                    Call call =   LoginActivity.mOkHttpClient.newCall(request);
                    call .enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(),"onFailure",Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String str=response.body().string();
                            Log.d(tag,str);
                            JSONObject jsonObject= JSON.parseObject(str);
                            String data=jsonObject.get("data").toString();
                            Log.d(tag,data);
                            List<StudentStatus> list=JSON.parseArray(data,StudentStatus.class);
                            for (int i=0;i<list.size();i++){
                                Log.d(tag,list.get(i).toString());
                                arrayList.add(list.get(i));

                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    infolist.addAll(arrayList);
                                    if (arrayList.size()==0){
                                        Toast.makeText(getActivity(),"数据为空",Toast.LENGTH_LONG).show();
                                    }
                                    driverInfoAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                }
            }).start();
        }
        else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(),"请绑定车辆信息",Toast.LENGTH_LONG).show();
                }
            });
        }
        /*return  arrayList;*/
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.total_date:
                infolist.clear();
                getInfo(new Date());
                driverInfoAdapter.notifyDataSetChanged();
                total_date.setClickable(false);
                yesterDayDate.setClickable(true);
                break;
            case R.id.yesterday_date:
                infolist.clear();
                Calendar   cal   =   Calendar.getInstance();
                cal.add(Calendar.DATE,   -1);
                getInfo(cal.getTime());
                driverInfoAdapter.notifyDataSetChanged();
                yesterDayDate.setClickable(false);
                total_date.setClickable(true);
                break;
            case R.id.student_list:
                Intent intent=new Intent(getActivity(),NurseStudentList.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }
}
