package com.aorise.study.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import aorise.com.food_safety_app.network.BuildConfig;


/**
 * Created by Admin on 2018/6/26.
 */

public class Utils {

    private AlertDialog dlg;

    /**
     * 存放实体类以及任意类型
     *
     * @param context 上下文对象
     * @param key
     * @param obj
     */
    public static void putBean(Context context, String key, Object obj) {
        if (obj instanceof Serializable) {// obj必须实现Serializable接口，否则会出问题
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String string64 = new String(Base64.encode(baos.toByteArray(),
                        0));
                SharedPreferences.Editor editor = context.getSharedPreferences("Model", Context.MODE_PRIVATE).edit();
                editor.putString(key, string64).apply();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException(
                    "the obj must implement Serializble");
        }

    }

    public static Object getBean(Context context, String key) {
        Object obj = null;
        try {
            SharedPreferences sp = context.getSharedPreferences("Model", Context.MODE_PRIVATE);
            String base64 = sp.getString(key, "");
            if (base64.equals("")) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

//    public static void showDialog(final Context mContext) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        View v = inflater.inflate(R.layout.dialog, null);
//        TextView btn_sure = v.findViewById(R.id.dialog_btn_sure);
//        TextView btn_cancel = v.findViewById(R.id.dialog_btn_cancle);
//        //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
//        final Dialog dialog = builder.create();
//        dialog.show();
//        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
//        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
//        btn_sure.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                mContext.startActivity(new Intent(mContext, LoginActivity.class));
//            }
//        });
//
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                dialog.dismiss();
//            }
//        });
//    }

    /**
     * * 时间戳转换成日期格式字符串
     * * @param seconds 精确到秒的字符串
     * * @param formatStr
     * * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date   字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 38      * 取得当前时间戳（精确到秒）
     * 39      * @return
     * 40
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    public static void showDeskRedPoint(Context context, int count) {
        SharedPreferences desktop_star = context.getSharedPreferences("Star", Context.MODE_PRIVATE);
        // TODO: 2018/9/19  设置APP桌面图标 适配小米华为三星
        String launcherClassName = "nursery.com.aorise.asnursery.ui.activity.SplashActivity";//启动的Activity完整名称
//                    小米
        if (Build.BRAND.equals("Xiaomi")) {
//            try {
//                Field field = notification.getClass().getDeclaredField("extraNotification");
//                Object extraNotification = field.get(notification);
//                Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
//                method.invoke(extraNotification, 1);
//                desktop_star.edit().putInt("count", 1).apply();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        } else if (Build.BRAND.equals("Huawei")) {
            Bundle localBundle = new Bundle();//需要存储的数据
            localBundle.putString("package", context.getPackageName());//包名
            localBundle.putString("class", launcherClassName);
            localBundle.putInt("badgenumber", count);//未读信息条数
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, localBundle);
        } else if (Build.BRAND.equals("samsung")) {
            Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
            intent.putExtra("badge_count", count);
            intent.putExtra("badge_count_package_name", context.getPackageName());
            intent.putExtra("badge_count_class_name", launcherClassName);
            context.sendBroadcast(intent);
        }
    }

    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

//    public static void showMultiDialog(Context context) {
//        final String[] items = {"AAA", "BBB", "CCC", "DDD", "DDD", "DDD", "DDD", "DDD", "DDD", "DDD", "DDD", "DDD", "DDD", "DDD", "DDD", "DDD", "DDD"};
//        AlertDialog alertDialog = new AlertDialog.Builder(context)
//                .setIcon(R.drawable.addinform)
//                .setTitle("多选列表")
//                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                        // TODO Auto-generated method stub
//                        Log.i("TEST", which + ";" + isChecked);
//                    }
//                })
//                .setPositiveButton("确定", null)
//                .create();
//        alertDialog.show();
//    }

    //讲2018-1-8 21：30:21 转成2018-1-8
    public static String formatTime(String time) {
        try {
            String[] split = time.split(" ");
            return split[0];
        } catch (Exception e) {
            return "";
        }
    }


    public static String autoWaringState(int state, int lockStatus) {
        String stateStr = "";
        switch (state) {
            case BuildConfig.STATUS_UNEXPIRE:
                stateStr = "快到期";
                break;
            case BuildConfig.STATUS_EXPIRE:
                stateStr = "已过期";
                break;
        }
        switch (lockStatus) {
            case BuildConfig.STATUS_LOCK:
                stateStr = stateStr + " 锁定";
                break;
            case BuildConfig.STATUS_LOCK_NORMAL:
                break;
        }
        return stateStr;
    }

    public static String autoWaringColor(int state) {
        String color = "#7287f0";
        switch (state) {
            case BuildConfig.STATUS_UNEXPIRE:
                color = "#f77f98";
                break;
            case BuildConfig.STATUS_EXPIRE:
                color = "#7287f0";
                break;
        }
        return color;
    }

    /**
     * 验证非空
     *
     * @param list
     */
    public static boolean isEmpty(List<TextView> list) {
        boolean flag = false;
        if (list != null) {
            for (TextView textView : list) {
                String str = textView.getText().toString();
                if (str.isEmpty() || str.equals("")) {
                    flag = true;
                }
            }
        }
        return flag;
    }
}
