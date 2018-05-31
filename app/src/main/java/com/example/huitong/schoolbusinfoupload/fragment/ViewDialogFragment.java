package com.example.huitong.schoolbusinfoupload.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.huitong.schoolbusinfoupload.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinxu on 2018/4/5.
 */

public class ViewDialogFragment extends DialogFragment {
    private  NoticeDialogListener noticeDialogListener;
    Intent intent=new Intent();
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(Intent  intent);
        public void onDialogNegativeClick(Intent intent);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.addsutdnet, null);

        //为适配器设置下拉列表下拉时的菜单样式。

        //为spinner绑定我们定义好的数据适配器
        builder.setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final List dataList = new ArrayList<String>();
                        dataList.add("2-5公里");
                        dataList.add("5公里以上");
                        EditText name = (EditText) view.findViewById(R.id.addname);
                        EditText phone = (EditText) view.findViewById(R.id.addphone);
                        EditText address = (EditText) view.findViewById(R.id.addphone);
                        Spinner distance=view.findViewById(R.id.distance);
                        distance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                intent.putExtra("distance",adapterView.getSelectedItem().toString());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        intent.putExtra("distance",distance.getSelectedItem().toString());
                        intent.putExtra("name",name.getText().toString());
                        intent.putExtra("phone",phone.getText().toString());
                        intent.putExtra("address",address.getText().toString());
                        noticeDialogListener.onDialogPositiveClick(intent);


                    }
                })
        ;
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            noticeDialogListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public void onDestroy() {
        if (noticeDialogListener!=null) {
            noticeDialogListener.onDialogNegativeClick(intent);
        }
        super.onDestroy();
    }
}
