package com.example.huitong.schoolbusinfoupload.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.huitong.schoolbusinfoupload.R;
import com.example.huitong.schoolbusinfoupload.enity.Student;

import java.util.ArrayList;

/**
 * Created by yinxu on 2018/4/3.
 */

public class StudentInfoAdapter extends RecyclerView.Adapter<StudentInfoAdapter.StuInfoHolder> {
    private ArrayList<Student> student;
    private int[]  status;
    private Context context;




    public StudentInfoAdapter(Context context, ArrayList<Student> studentArrayList){
        this.context=context;
        this.student=studentArrayList;

    }


    @NonNull
    @Override
    public StuInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.studentinfo_item,parent, false);
        StuInfoHolder stuInfoHolder=new StuInfoHolder(view);
        return stuInfoHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StuInfoHolder holder, int position) {
        Log.d("debug",Integer.toString(position)+"size"+student.size());

        holder.sudent_name.setText(student.get(position).getName());
        holder.student_phone.setText(student.get(position).getPhone());
        holder.studnet_status.setChecked(student.get(position).getStatus());
        holder.student_address.setText(student.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return student.size() ;
    }

    public class StuInfoHolder extends RecyclerView.ViewHolder {
        private TextView sudent_name;
        private TextView student_phone,student_address;
        private CheckBox studnet_status;

        public StuInfoHolder(View itemView) {
            super(itemView);
            student_address=itemView.findViewById(R.id.student_item_address);
            student_phone=itemView.findViewById(R.id.student_item_phone);
            sudent_name=itemView.findViewById(R.id.student_item_name);
            studnet_status=itemView.findViewById(R.id.student_item_status);
            studnet_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    student.get(getLayoutPosition()).setStatus(b);
                }
            });
        }

    }
}
