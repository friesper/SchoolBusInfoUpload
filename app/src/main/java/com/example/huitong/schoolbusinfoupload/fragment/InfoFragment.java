package com.example.huitong.schoolbusinfoupload.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.huitong.schoolbusinfoupload.R;

/**
 * Created by yinxu on 2018/4/4.
 */

public class InfoFragment extends Fragment implements View.OnClickListener {
    Button loginout;
    TextView name,workUnit,phone;
    String  userInfo;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.info_fragment,container,false);

        loginout=view.findViewById(R.id.loginout);
        loginout.setOnClickListener(this);
        name=view.findViewById(R.id.name);
        workUnit=view.findViewById(R.id.work_unit);
        phone=view.findViewById(R.id.phone);
        userInfo=getActivity().getIntent().getStringExtra("userInfo");
        initViwe();
        return view;
    }
    private void initViwe() {
        JSONObject jsonObject = JSONObject.parseObject(userInfo);
        Log.d("InfoFragment",userInfo);
        String  data =  jsonObject.get("data").toString();
        data=data.substring(data.indexOf('{'));
        data=data.replace('=',':');
        jsonObject=JSONObject.parseObject(data);
        Integer id= (Integer) jsonObject.get("id");
        Log.d("InfoFragment",data);
        String name_string=jsonObject.get("name").toString();
        String  workUnit_string=jsonObject.get("workUnitName").toString();
        String phone_string=jsonObject.get("phone").toString();
        name.setText(name_string);
        phone.setText(phone_string);
        workUnit.setText(workUnit_string);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginout:
                getActivity().finish();
                break;
            default:
                    break;
        }

    }
}
