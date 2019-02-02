package com.aorise.study.QRCode;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.aorise.study.R;
import com.aorise.study.base.LogT;
import com.aorise.study.databinding.ActivityQrcodeBinding;

/**
 * Created by Tuliyuan.
 * Date: 2019/2/1.
 */
public class QRCreateActivity extends AppCompatActivity {


    ActivityQrcodeBinding mDatabinding;
    private String[] permission = {Manifest.permission.CAMERA};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabinding = DataBindingUtil.setContentView(this, R.layout.activity_qrcode);
        mDatabinding.qrcodeView.setImageBitmap(QRCodeUtil.createQRCodeBitmap("http://www.baidu.com/",160));
        // 申请文件读写权限（部分朋友遇到相册选图需要读写权限的情况，这里一并写一下）
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(QRCreateActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2039);
            return;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(QRCreateActivity.this, permission, 2038);
            return;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2038:
                // 摄像头权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LogT.d("获取camera权限成功");
                }
                break;
            case 2039:
                // 文件读写权限申请
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获得授权
                    LogT.d("获取storage权限成功");
                  //  startQrCode();
                } else {
                    // 被禁止授权
                    Toast.makeText(QRCreateActivity.this, "请至权限中心打开本应用的文件读写权限", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
