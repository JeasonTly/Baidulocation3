package com.aorise.study;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.aorise.study.base.LogT;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if(intent.getAction().equals("com.write.file")){
            LogT.d("com.write.file");

            Intent mIntent = new Intent("com.write.file");
            mIntent.setComponent(new ComponentName("com.aorise.study","com.aorise.study.MyReceiver"));
            PendingIntent sender = PendingIntent.getBroadcast(
                    context, 0, mIntent, 0);

            AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//            manager.setWindow(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),1000*60*3,sender);
            manager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+1000*60*3 ,1000*60*3,sender);
            SimpleDateFormat df = new SimpleDateFormat("yyyy:MM:dd: - HH:mm:ss");//设置以当前时间格式为图片名称
            String time = df.format(new Date()) +"\r\n";
            FileUtils.SaveFile(context,time);

        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
