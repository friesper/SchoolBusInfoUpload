package com.example.huitong.schoolbusinfoupload.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.huitong.schoolbusinfoupload.R;
import com.example.huitong.schoolbusinfoupload.activity.LoginActivity;
import com.example.huitong.schoolbusinfoupload.adapter.BusInfoDisplayAdapter;
import com.example.huitong.schoolbusinfoupload.enity.BusInfo;
import com.example.huitong.schoolbusinfoupload.util.AndroidUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class BusInfoFragment extends Fragment implements View.OnClickListener {


    String userType;
    String tag="DriverInfoFragment";
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    BusInfoDisplayAdapter busInfoDisplayAdapter;
    Button totalDay,yesterDay;
    final Map<String,String> map=new HashMap<String,String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.bus_info_fragment,container,false);
        userType=getActivity().getIntent().getStringExtra("userType");
        recyclerView=view.findViewById(R.id.bus_info_list);
        layoutManager = new LinearLayoutManager(getContext());
        busInfoDisplayAdapter=new BusInfoDisplayAdapter(getContext(),map);
        getInfo(new Date());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(busInfoDisplayAdapter);
        recyclerView.setLayoutManager(layoutManager);
        totalDay=view.findViewById(R.id.total_date);
        yesterDay=view.findViewById(R.id.yesterday_date);
        totalDay.setOnClickListener(this);yesterDay.setOnClickListener(this);
        return view;
    }



    public void getInfo(Date date){
        String userInfo=getActivity().getIntent().getStringExtra("userInfo");
        JSONObject jsonObject = JSONObject.parseObject(userInfo);
        Log.d("InfoFragment",userInfo);
        String  data =  jsonObject.get("data").toString();
        data=data.substring(data.indexOf('{'));
        data=data.replace('=',':');
        jsonObject=JSONObject.parseObject(data);
        Log.d(tag,data);
        if (jsonObject.get("busId")!=null){
            String id=jsonObject.getString("busId").toString();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            final String dates=simpleDateFormat.format(date);
            final String url= AndroidUtil.host+"/admin/info/bus/info/find/"+id+"/"+dates;
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
                            List<BusInfo> list=  JSON.parseArray(data,BusInfo.class);
                            if (list.size()>0){
                                Log.d(tag,Integer.toString(list.size()));
                                BusInfo busInfo=list.get(0);
                                map.put("电瓶卫生",busInfo.getBatteryHealth());
                                map.put("应急门",busInfo.getEscapeDoor());
                                map.put("轮胎气压及螺丝",busInfo.getTirePressureScrews());
                                map.put("发动机卫生",busInfo.getEngineHygiene());
                                map.put("空滤卫生",busInfo.getAirFilter());
                                map.put("药品箱",busInfo.getBatteryHealth());
                                map.put("gps和监控",busInfo.getGpsMonitoring());
                                map.put("灭火器",busInfo.getFireExtinguisher());
                                map.put("安全锤",busInfo.getSafetyHammer());
                                map.put("机油量",busInfo.getOillOilLevel());
                                map.put("防冻液量",busInfo.getAmountOfAntifreeze());
                                map.put("制动液",busInfo.getBakeFluid());
                                map.put("皮带松紧度",busInfo.getBeltTightness());
                                map.put("灯光",busInfo.getLights());
                                map.put("路牌",busInfo.getGuideBoard());
                                map.put("离合",busInfo.getClutch());
                                map.put("制动器",busInfo.getBrake());
                                map.put("方向盘",busInfo.getSteeringWheel());
                                map.put("仪表盘",busInfo.getInstrumentPanel());

                            }
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(tag,"runinUI");
                                    busInfoDisplayAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                }
            }).start();
        }
        else {
            Toast.makeText(getActivity(),"请绑定车辆信息",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.total_date:
                map.clear();
                getInfo(new Date());
                busInfoDisplayAdapter.notifyDataSetChanged();
                totalDay.setClickable(false);
                yesterDay.setClickable(true);
                break;
            case R.id.yesterday_date:
                map.clear();
                Calendar cal   =   Calendar.getInstance();
                cal.add(Calendar.DATE,   -1);
                getInfo(cal.getTime());
                busInfoDisplayAdapter.notifyDataSetChanged();
                yesterDay.setClickable(false);
                totalDay.setClickable(true);
                break;
            default:
                break;

        }
    }
}
