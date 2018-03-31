package com.example.huitong.schoolbusinfoupload.adapter;

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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by yinxu on 2018/3/29.
 */

public class BusInfoAdapter extends RecyclerView.Adapter<BusInfoAdapter.BusInfoHolder>{
    private Context context;
    private String[] titles;
    private String[] contents;

    public BusInfoAdapter(Context context){
        this.context=context;
        this.titles=context.getResources().getStringArray(R.array.TaskItem);
        this.contents=context.getResources().getStringArray(R.array.driverTaskDefault);
    }

    @Override
    public BusInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.businfo_item,parent, false);
        BusInfoHolder infoHolder=new BusInfoHolder(view);
        return infoHolder;
    }
    public ArrayList  getContents(){
           String[] c= contents.clone();
           ArrayList<String> arrayList=new ArrayList<>();
           for (int i=0;i<c.length;i++){
               arrayList.add(c[i]);
           }
        return arrayList;
    }
    @Override
    public void onBindViewHolder(BusInfoHolder holder, int position) {
        holder.textView.setText(titles[position]);
        holder.editText.setText(contents[position]);

    }


    @Override
    public int getItemCount() {
        return titles==null?0:titles.length;
    }

    class BusInfoHolder extends RecyclerView.ViewHolder implements TextWatcher {
        private TextView textView;
        private EditText editText;

        public BusInfoHolder(View view){
            super(view);
            textView=(TextView)view.findViewById(R.id.task_item_name);
            editText=(EditText)view.findViewById(R.id.task_item_input);
            editText.addTextChangedListener(this);

        }

        public final <T> T findViewById(int resId){
            return (T) itemView.findViewById(resId);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            contents[getLayoutPosition()]=editable.toString();
        }

    }
}
