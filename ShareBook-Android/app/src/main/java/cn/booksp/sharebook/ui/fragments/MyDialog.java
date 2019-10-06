package cn.booksp.sharebook.ui.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.dommy.qrcode.util.Constant;
import com.google.zxing.activity.CaptureActivity;

import cn.booksp.sharebook.R;

public class MyDialog extends DialogFragment {

    private Button scanBtn;
    private Button inputBtn;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final FragmentActivity activity = getActivity();
        final Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.mydialog, null);
        scanBtn = dialog.findViewById(R.id.scanBtn);
        inputBtn = dialog.findViewById(R.id.inputBtn);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 申请相机权限
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // 申请权限
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
                    return;
                }
                // 申请文件读写权限（部分朋友遇到相册选图需要读写权限的情况，这里一并写一下）
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // 申请权限
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.REQ_PERM_EXTERNAL_STORAGE);
                    return;
                }
                // 二维码扫码
                Intent intent = new Intent(context, CaptureActivity.class);
                activity.startActivityForResult(intent, Constant.REQ_QR_CODE);
                dismiss();
            }
        });
        inputBtn.setOnClickListener( v -> {
            IsbnDialog isbnDialog = new IsbnDialog();
            isbnDialog.show(activity.getSupportFragmentManager(),"hello");
            dismiss();
        });
        builder.setView(dialog);
        return builder.create();
    }
}
