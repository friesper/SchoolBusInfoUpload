package com.example.huitong.schoolbusinfoupload.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huitong.schoolbusinfoupload.R;
import com.example.huitong.schoolbusinfoupload.enity.Student;
import com.example.huitong.schoolbusinfoupload.enity.StudentStatus;

import java.util.ArrayList;

/**
 * Created by yinxu on 2018/4/6.
 */

public class DriverInfoAdapter extends RecyclerView.Adapter<DriverInfoAdapter.InfoHolder> {
    private Context context;
    private ArrayList<StudentStatus> arrayList;

    public DriverInfoAdapter(Context context, ArrayList<StudentStatus> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }


    @NonNull
    @Override
    public InfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.driver_info_list_item,parent, false);
        InfoHolder infoHolder=new InfoHolder(view);
        return infoHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InfoHolder holder, int position) {
        if (arrayList.get(position).getStatus()==1){
            holder.status.setText("乘车");
        }else {
            holder.status.setText("未乘车");
        }
        if (arrayList.get(position).getTimeQuantum()==1){
            holder.time.setText("上午");

        }
        else {
            holder.time.setText("下午");
        }
            holder.name.setText(arrayList.get(position).getStudentName());
            holder.phone.setText(arrayList.get(position).getStudentPhone());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class InfoHolder extends RecyclerView.ViewHolder {
        private TextView name,phone,status,time;
        public InfoHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.driver_showname);
            phone=itemView.findViewById(R.id.driver_showphone);
            status=itemView.findViewById(R.id.driver_showstatus);
            time=itemView.findViewById(R.id.driver_showtiem);

        }
    }
}
