package com.example.wen.yuedu.dialog;

/**
 * Created by wen on 2018/3/11.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.wen.yuedu.R;

public class InputDialog extends AlertDialog implements OnClickListener{
    private EditText etPassword;  //编辑框
    private Button btnConfrim, btnCancel;  //确定取消按钮
    private OnEditInputFinishedListener mListener; //接口

    public interface OnEditInputFinishedListener{
        void editInputFinished(String password);
    }

    public InputDialog(Context context, OnEditInputFinishedListener mListener) {
        super(context);
        this.mListener = mListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        //控件
        etPassword = (EditText)findViewById(R.id.et_password);
        btnConfrim = (Button)findViewById(R.id.btn_confirm);
        btnCancel = (Button)findViewById(R.id.btn_cancel);

        btnConfrim.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_confirm) {
            //确定
            if (mListener != null) {
                String password = etPassword.getText().toString();
                mListener.editInputFinished(password);
            }
            dismiss();
        }else {
            //取消
            dismiss();
        }
    }

}