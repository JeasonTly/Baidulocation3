package com.aorise.study;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.aorise.study.base.LogT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/9/12.
 */

public class FileUtils {
    public static void savePhoto(final Context context, final Bitmap bmp, final SaveResultCallback saveResultCallback) {
        final File sdDir = getSDPath();
        if (sdDir == null) {
            Toast.makeText(context,"设备自带的存储不可用", Toast.LENGTH_LONG).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                File appDir = new File(sdDir, "out_photo");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置以当前时间格式为图片名称
                String fileName = df.format(new Date()) + ".png";
                File file = new File(appDir, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                    saveResultCallback.onSavedSuccess();
                } catch (FileNotFoundException e) {
                    saveResultCallback.onSavedFailed();
                    e.printStackTrace();
                } catch (IOException e) {
                    saveResultCallback.onSavedFailed();
                    e.printStackTrace();
                }

                //保存图片后发送广播通知更新数据库
                Uri uri = Uri.fromFile(file);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            }
        }).start();
    }
    public static void SaveFile(final Context context ,final String time){
        final File sdDir = getSDPath();
        if (sdDir == null) {
            Toast.makeText(context,"设备自带的存储不可用", Toast.LENGTH_LONG).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                File appDir = new File(sdDir, "logfile");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }

                String temp  = "";
                File file = new File(appDir, "log.txt");;
                FileInputStream fis = null;
                InputStreamReader isr = null;
                BufferedReader br = null;
                FileOutputStream fos  = null;
                PrintWriter pw = null;
                try {
                    //构造函数中的第二个参数true表示以追加形式写文件
                    FileWriter fw = new FileWriter(file,true);
                    fw.write(time);
                    fw.close();
//                    //文件路径(包括文件名称)
//                    //将文件读入输入流
//                    fis = new FileInputStream(file);
//                    isr = new InputStreamReader(fis);
//                    br = new BufferedReader(isr);
//                    StringBuffer buffer = new StringBuffer();
//
//                    //文件原有内容
//                    for(int i=0;(temp =br.readLine())!=null;i++){
//                        buffer.append(temp);
//                        // 行与行之间的分隔符 相当于“\n”
//                        buffer = buffer.append(System.getProperty("line.separator"));
//                    }
//                    buffer.append(time);
//
//                    fos = new FileOutputStream(file);
//                    pw = new PrintWriter(fos);
//                    pw.write(buffer.toString().toCharArray());
//                    pw.flush();
//                    if (pw != null) {
//                        pw.close();
//                    }
//                    if (fos != null) {
//                        fos.close();
//                    }
//                    if (br != null) {
//                        br.close();
//                    }
//                    if (isr != null) {
//                        isr.close();
//                    }
//                    if (fis != null) {
//                        fis.close();
//                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
//
//                //保存图片后发送广播通知更新数据库
//                Uri uri = Uri.fromFile(file);
//                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            }
        }).start();
    }

    public static File getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir;
    }

    public interface SaveResultCallback {
        void onSavedSuccess();

        void onSavedFailed();
    }
}
