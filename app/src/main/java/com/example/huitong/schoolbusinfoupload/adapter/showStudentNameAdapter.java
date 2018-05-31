package com.example.huitong.schoolbusinfoupload.adapter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huitong.schoolbusinfoupload.R;
import com.example.huitong.schoolbusinfoupload.enity.Student;
import com.example.huitong.schoolbusinfoupload.util.AndroidUtil;
import com.example.huitong.schoolbusinfoupload.util.DatabaseUtil;

import java.util.ArrayList;

/**
 * Created by yinxu on 2018/4/4.
 */

public class showStudentNameAdapter extends RecyclerView.Adapter<showStudentNameAdapter.showHolder> {
    private ArrayList<Student> arrayList;
    private Context context;
    private String name;
    public showStudentNameAdapter(Context context, ArrayList<Student> arrayList,String name){
    this.context=context;
    this.arrayList=arrayList;
    this.name=name;
    }
    public void  setArrayList(ArrayList<Student> arrayList){
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }
    public showHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboardstudnet,parent, false);
        showHolder holder=new showHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull showHolder holder, int position) {
        holder.showname.setText(arrayList.get(position).getName());
        holder.showphone.setText(arrayList.get(position).getPhone());
        final String address=arrayList.get(position).getAddress();
        final String distance=arrayList.get(position).getDistance();
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("详细信息");
                builder.setMessage("住址:"+address+"\n"+"距离:"+distance);
                builder.setPositiveButton("确定", null);
                builder.create().show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class  showHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView showname,showphone;
        private Button edit,delete;
        DatabaseUtil databaseUtil;
        SQLiteDatabase sqLiteDatabase;
        LinearLayout linearLayout;
        public showHolder(View itemView) {
             super(itemView);
             showname=itemView.findViewById(R.id.showname);
             showphone=itemView.findViewById(R.id.showphone);
             delete=itemView.findViewById(R.id.deletesutdnetinfo);
            databaseUtil=new DatabaseUtil(context,name,null,1);
            linearLayout=itemView.findViewById(R.id.show_student_infos);
            sqLiteDatabase=databaseUtil.getWritableDatabase();
             delete.setOnClickListener(this);


         }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.deletesutdnetinfo:
                    Log.d("adapter",arrayList.get(getLayoutPosition()).getId().toString());
                    sqLiteDatabase.delete("student","id=?",new String[]{arrayList.get(getLayoutPosition()).getId().toString()});
                    arrayList.remove(getLayoutPosition());
                    notifyDataSetChanged();
                    Toast.makeText(context,"删除成功",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;


            }


        }
    }


}
