package com.example.huitong.schoolbusinfoupload.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.huitong.schoolbusinfoupload.R;
import com.example.huitong.schoolbusinfoupload.activity.LoginActivity;
import com.example.huitong.schoolbusinfoupload.adapter.DriverInfoAdapter;
import com.example.huitong.schoolbusinfoupload.enity.StudentStatus;
import com.example.huitong.schoolbusinfoupload.util.AndroidUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class nurseInfoFragment extends Fragment implements View.OnClickListener {

    String userType;
    String tag="nurseInfoFragment";
    RecyclerView recyclerView;
    ArrayList<StudentStatus> studentStatuses;
    LinearLayoutManager layoutManager;
    DriverInfoAdapter driverInfoAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.nurse_info_fragment,container,false);
        userType=getActivity().getIntent().getStringExtra("userType");
        recyclerView=view.findViewById(R.id.nurse_student_info_list);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        driverInfoAdapter=new DriverInfoAdapter(getContext(),getInfo(new Date()));
        recyclerView.setAdapter(driverInfoAdapter);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }



    public ArrayList<StudentStatus> getInfo(Date dates){
        final ArrayList<StudentStatus> arrayList = new ArrayList<>();
        String userInfo=getActivity().getIntent().getStringExtra("userInfo");
        JSONObject jsonObject = JSONObject.parseObject(userInfo);
        Log.d("InfoFragment",userInfo);
        String  data =  jsonObject.get("data").toString();
        data=data.substring(data.indexOf('{'));
        data=data.replace('=',':');
        jsonObject=JSONObject.parseObject(data);
        Log.d(tag,data);
        Iterator iterator=jsonObject.keySet().iterator();
        if (jsonObject.get("busId")!=null){
            String id=jsonObject.getString("busId").toString();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String date=simpleDateFormat.format(dates);
            final String url= AndroidUtil.host+"/admin/info/mobile/student/find/"+id+"/"+date;
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
                                    driverInfoAdapter.notifyDataSetChanged();

                                }
                            });
                           /* StudentStatus[] studentStatuses= (StudentStatus[]) jsonArray.toArray();
                            for (int i=0;i<studentStatuses.length;i++){
                                arrayList.add(studentStatuses[i]);
                                Log.d(tag,studentStatuses[i].toString());
                            }*/

                        }
                    });
                }
            }).start();
        }
        else {
            Toast.makeText(getActivity(),"请绑定车辆信息",Toast.LENGTH_LONG).show();
        }
        return  arrayList;
    }


    @Override
    public void onClick(View view) {

    }
}
