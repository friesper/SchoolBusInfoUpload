package com.example.huitong.schoolbusinfoupload.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.huitong.schoolbusinfoupload.R;

/**
 * Created by yinxu on 2018/4/5.
 */

public class ViewDialogFragment extends DialogFragment {

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, "ViewDialogFragment");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.addsutdnet, null);
        builder.setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            EditText et_userName = (EditText) view.findViewById(R.id.addname);
                            EditText et_password = (EditText) view.findViewById(R.id.addphone);
                            Intent intent=new Intent();
                            intent.putExtra("name",et_userName.getText().toString());
                            intent.putExtra("phone",et_password.getText().toString());
                        Fragment targetFragment = getTargetFragment(); // fragment1 in our case
                        if (targetFragment != null) {
                            targetFragment.onActivityResult(1, 0, intent);
                        }

                    }
                })
        ;
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
