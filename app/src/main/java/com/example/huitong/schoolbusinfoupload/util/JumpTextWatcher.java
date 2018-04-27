package com.example.huitong.schoolbusinfoupload.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;


/**
 * Created by yinxu on 2018/4/23.
 */

public class JumpTextWatcher implements TextWatcher {
    private EditText mThisView = null;
    private View mNextView = null;
    public JumpTextWatcher(EditText vThis, View vNext) {
        super();
        mThisView = vThis;
        if (vNext != null) {
            mNextView = vNext;
        }
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String str = editable.toString();
        if (str.indexOf("/r") >= 0 || str.indexOf("\n") >= 0) {
            //如果发现输入回车符或换行符，替换为空字符
            mThisView.setText(str.replace("/r", "").replace("\n", ""));
            if (mNextView != null) {
                //如果跳转控件不为空，让下一个控件获得焦点，此处可以直接实现登录功能
                mNextView.requestFocus();
                if (mNextView instanceof EditText) {
                    EditText et = (EditText) mNextView;
                    //如果跳转控件为EditText，让光标自动移到文本框文字末尾
                    et.setSelection(et.getText().length());
                }
            }
        }
    }
}
