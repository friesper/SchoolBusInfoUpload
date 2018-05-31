package com.example.huitong.schoolbusinfoupload.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.huitong.schoolbusinfoupload.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by yinxu on 2018/3/29.
 */

public class BusInfoDisplayAdapter extends RecyclerView.Adapter<BusInfoDisplayAdapter.BusInfoHolder>{
    private Context context;
    private String[] titles;
    private Map<String ,String> contents;

    public BusInfoDisplayAdapter(Context context,Map<String,String> contents ){
        this.context=context;
        this.titles=context.getResources().getStringArray(R.array.TaskItem);
        this.contents=contents;
    }

    @Override
    public BusInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.businfo_display_item,parent, false);
        BusInfoHolder infoHolder=new BusInfoHolder(view);
        return infoHolder;
    }
    @Override
    public void onBindViewHolder(BusInfoHolder holder, int position) {
        holder.textView.setText(titles[position]);
        holder.editText.setText(contents.get(titles[position]));
    }


    @Override
    public int getItemCount() {
        return titles==null?0:titles.length;
    }

    class BusInfoHolder extends RecyclerView.ViewHolder  {
        private TextView textView;
        private TextView editText;

        public BusInfoHolder(View view){
            super(view);
            textView=(TextView)view.findViewById(R.id.task_item_name);
            editText=(TextView)view.findViewById(R.id.task_item_input);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }

    }
}
