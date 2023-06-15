package com.test.drivingcar.ui.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.test.drivingcar.R;

public class CarTypeDialog extends Dialog {
    private Context context;
    private View mDialogView;

    public CarTypeDialog(@NonNull Context context) {
        super(context);
        setCustomDialog(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.8f);
        getWindow().setAttributes(lp);
    }

    private void setCustomDialog(Context context) {
        this.context = context;
        mDialogView = View.inflate(context, R.layout.dialog_car_type, null);
        mDialogView.findViewById(R.id.ll_car).setOnClickListener(v -> {
            dialogInter.callback(1);
            CarTypeDialog.this.dismiss();
        });
        mDialogView.findViewById(R.id.ll_trunk).setOnClickListener(v -> {
            dialogInter.callback(2);
            CarTypeDialog.this.dismiss();
        });
        mDialogView.findViewById(R.id.ll_bus).setOnClickListener(v -> {
            dialogInter.callback(3);
            CarTypeDialog.this.dismiss();
        });
        mDialogView.findViewById(R.id.ll_moto).setOnClickListener(v -> {
            dialogInter.callback(4);
            CarTypeDialog.this.dismiss();
        });

        setContentView(mDialogView);
    }

    DialogInter dialogInter;

    public void setDialogInter(DialogInter dialogInter) {
        this.dialogInter = dialogInter;
    }

    public interface DialogInter {
        void callback(int type);
    }
}
