package com.aorise.study.common;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.hjq.toast.ToastUtils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import aorise.com.food_safety_app.R;


/**
 * 作者：李世林 Administrator on 2018/6/25 09:36
 * 邮箱：1871907207@qq.com
 */

public abstract class BaseActivity extends AppCompatActivity implements CrashHandlerUtil.CrashListener {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private Toolbar mToolBar = null;
    private ProgressDialog mLoadingDialog = null;
    private Toast mToast = null;

    public BaseActivity() {
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityManager.getInstance().addActivity(this);
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
        this.initView();
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
//        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
//            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
//            //这样半透明+白=灰, 状态栏的文字能看得清
//            StatusBarUtil.setStatusBarColor(this,0x55000000);
//        }
        this.initData();
        this.initEvent();
        closeAndroidPDialog();
    }

    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.destroyToast();
        ActivityManager.getInstance().finishActivity(this);
        HandlerUtil.HANDLER.removeCallbacksAndMessages((Object) null);
        //AoriseLog.i(TAG, this.getLocalClassName() + " - onDestroy");
    }

    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        //this.initToolbar();
    }

    public void showToast(String text) {
        ToastUtils.show(text);
        /*if(this.mToast == null) {
            this.mToast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        } else {
            this.mToast.setText(text);
        }

        this.mToast.show();*/
    }

    public void showToast(@StringRes int resId) {
        this.showToast(this.getString(resId));
    }

    private void destroyToast() {
        if (this.mToast != null) {
            this.mToast.cancel();
            this.mToast = null;
        }

    }


    public void uploadExceptionToServer(File file) {
    }

    public void crashAction() {
        this.showToast(R.string.aorise_label_crash_msg);
    }

    public void openActivity(Class<?> pClass) {
        this.openActivity(pClass, (Bundle) null);
    }

    public void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        this.startActivity(intent);
    }

    public void openActivityClearTop(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        this.startActivity(intent);
    }

    public void openActivityForResult(Class<?> pClass, Bundle pBundle, int requestCode) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        this.startActivityForResult(intent, requestCode);
    }
}
