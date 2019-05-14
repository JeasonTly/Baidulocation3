package com.aorise.study.common;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 作者：李世林 Administrator on 2018/7/3 11:42
 * 邮箱：1871907207@qq.com
 */
public class CommonUtil {
    // TODO: 2018/7/3 这是一个公共方法类   用于存放类似图片加载器之类的工具

    /**
     * @param context     上下文
     * @param view        imageview控件
     * @param uri         图片地址
     * @param placeholder 加载时图片
     * @param error       加载错误图片
     * @param <T>
     */
    public static <T> void loadImage(Context context, ImageView view, T uri, @DrawableRes int placeholder, @DrawableRes int error) {
        GlideManager.getInstance().load(context, view, uri, placeholder, error);
    }


    // TODO: 2018/8/2 按键弹出软键盘
    public static void upWindow(EditText edt) {
        edt.requestFocus();
        InputMethodManager imm = (InputMethodManager) edt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
    }

//    /*/**
//     * 通过 List<MultipartBody.Part> 传入多个part实现多文件上传
//     * @param parts 每个part代表一个
//     * @return 状态信息
//     */
//    @Multipart
//    @POST("UploadServer")
//    Call<BaseResponse<String>> uploadFilesWithParts(@Part() List<MultipartBody.Part> parts);
//
//
//    /**
//     * 通过 MultipartBody和@body作为参数来实现多文件上传
//     * @param multipartBody MultipartBody包含多个Part
//     * @return 状态信息
//     */
//    @POST("UploadServer")
//    Call<BaseResponse<String>> uploadFileWithRequestBody(@Body MultipartBody multipartBody);*/


    public static MultipartBody filesToMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("file", file.getName(), requestBody);
        }
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }


    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("multipartFiles", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    public static RequestBody getRequestBody(String string) {
        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), string);
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    /**
     * 获取当前时间戳+4位随机数组合字符串
     */
    public static String getCurrentTimeRandom() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStr = dateFormat.format(now);
        return timeStr + getFourRandom();
    }

    public static String getFourRandom() {
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if (randLength < 4) {
            for (int i = 1; i <= 4 - randLength; i++)
                fourRandom = "0" + fourRandom;
        }
        return fourRandom;
    }
    /**
     * 将文本格式的字符串转换为日期Date对象,方便日期排序
     * @param dateString 传入的时间日期格式
     * @return Date数据类型，可以通过data1.before(data2)和data1.after(data2)进行比较
     */
    public static String stringToSecondDate(String dateString) {
        String date = "";
        try {
            ParsePosition position = new ParsePosition(0);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateValue = simpleDateFormat.parse(dateString, position);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时:mm分:ss秒");
             date = dateFormat.format(dateValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }
    public static String stringToDate(String dateString) {
        String date = "";
        try {
            ParsePosition position = new ParsePosition(0);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateValue = simpleDateFormat.parse(dateString, position);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            date = dateFormat.format(dateValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }
    public static String string2Date(String dateString) {
        String date = "";
        try {
            ParsePosition position = new ParsePosition(0);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateValue = simpleDateFormat.parse(dateString, position);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            date = dateFormat.format(dateValue);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }

    public static String string2MD5(String inStr) {
        LogT.d("string2MD5: -------------------------");
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }
    public static String convertMD5(String inStr) {
        LogT.d("convertMD5: -------------------------");
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }

}
