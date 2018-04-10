package com.example.huitong.schoolbusinfoupload.adapter;

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

    public showStudentNameAdapter(Context context, ArrayList<Student> arrayList){
    this.context=context;
    this.arrayList=arrayList;
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
        public showHolder(View itemView) {
             super(itemView);
             showname=itemView.findViewById(R.id.showname);
             showphone=itemView.findViewById(R.id.showphone);
             delete=itemView.findViewById(R.id.deletesutdnetinfo);
            databaseUtil=new DatabaseUtil(context,null,1);
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
                    sqLiteDatabase.close();
                    notifyDataSetChanged();
                    Toast.makeText(context,"删除成功",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;


            }


        }
    }


}
