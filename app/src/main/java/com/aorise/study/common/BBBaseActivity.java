package com.aorise.study.common;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import aorise.com.food_safety_app.R;
import aorise.com.food_safety_app.common.LogT;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 作者：李世林 Administrator on 2018/6/25 09:36
 * 邮箱：1871907207@qq.com
 */

public abstract class BBBaseActivity extends BaseActivity {
    private static final String TAG = BBBaseActivity.class.getSimpleName();
    private ActivityActionBarClick onActionBarItemClick;
    private View view;
    public String getStringAppend(List<Integer> list) {// TODO: 2017/9/21 将int数组用逗号拼接成字符串
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (Integer s : list) {
                sb.append(s + ",");  //循环遍历数组中元素，添加到 StringBuilder 对象中
            }
        }
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1); //调用 字符串的deleteCharAt() 方法,删除最后一个多余的逗号
        System.out.println(sb.toString());
        return sb.toString();

    }
    //rxjava线程转换
    public Observable.Transformer schedulersTransformer() {
        return new Observable.Transformer() {
            @Override
            public Object call(Object observable) {
                return ((Observable) observable).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public String getStringAppendString(List<String> list) {// TODO: 2017/9/21 将int数组用逗号拼接成字符串
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (String s : list) {
                sb.append(s + ",");  //循环遍历数组中元素，添加到 StringBuilder 对象中
            }
        }
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1); //调用 字符串的deleteCharAt() 方法,删除最后一个多余的逗号
        System.out.println(sb.toString());
        return sb.toString();

    }
//    将yyyy-mm-dd格式的日期变为时间戳  用于比较两个日期谁大谁小
    public static Long getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        }catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return Long.parseLong(re_time);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
        Log.d(TAG, "onCreate: " + getClass().getSimpleName());
    }
    public void initActionBar(View view, ActivityActionBarClick onclick){
        this.view = view;
        this.onActionBarItemClick = onclick;
    }
    public ImageView getActionbarBackView(){
        if(view == null){
            ToastUtils.show("请先执行initActionBar(actionbarView,ActivityActionBarClick）");
            this.finish();
        }
        ImageView mImageView = view.findViewById(R.id.title_back);
        mImageView.setVisibility(View.VISIBLE);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onActionBarItemClick != null) {
                    onActionBarItemClick.onBackClick();
                }else{
                    LogT.d("请设置actionbar 返回键的点击事件");
                }
            }
        });
        return mImageView;
    }
    public TextView getActionbarTitleView(){
        if(view == null){
            ToastUtils.show("请先执行initActionBar(actionbarView,ActivityActionBarClick）");
            this.finish();
        }
        TextView mTextView = view.findViewById(R.id.title_name);
        return mTextView;
    }
    public ImageView getActionbarMenuView(){
        if(view == null){
            ToastUtils.show("请先执行initActionBar(actionbarView,ActivityActionBarClick）");
            this.finish();
        }
        ImageView mImageView = view.findViewById(R.id.title_menu);
        View menuView = view.findViewById(R.id.title_menu_linear);
        mImageView.setVisibility(View.VISIBLE);
        menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onActionBarItemClick !=null){
                    onActionBarItemClick.onMenuClick();
                }else {
                    LogT.d("请设置actionbar 菜单键的点击事件");
                }
            }
        });
        return mImageView;
    }
    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    public static void fullScreen(Activity activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                    Window window = activity.getWindow();
                    View decorView = window.getDecorView();
                    //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                    int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                    decorView.setSystemUiVisibility(option);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.TRANSPARENT);
                    //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
                } else {
                    Window window = activity.getWindow();
                    WindowManager.LayoutParams attributes = window.getAttributes();
                    int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                    int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                    attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                    window.setAttributes(attributes);
                }
            }
    }

    //--------------------沉浸式状态栏------------------
    public static void setColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            View statusView = createStatusView(activity, color);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }

    }

    /**
     * 生成一个和状态栏大小相同的矩形条 * * @param activity 需要设置的activity * @param color 状态栏颜色值 * @return 状态栏矩形条
     */
    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }

    /**
     * 使状态栏透明 * <p> * 适用于图片作为背景的界面,此时需要图片填充到状态栏 * * @param activity 需要设置的activity
     */
    public static void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    //----------------------------隐藏软键盘-----------------------------------
    //当有edittext时 点击空白区域即可关闭软键盘， 集成即可  不用调用方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    public void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void setWindowStatusBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));

                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return this.getString(R.string.version_name) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.can_not_find_version_name);
        }
    }

    public static long getTime() {
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        String str = String.valueOf(time);
        return time;

    }

    /**
     获取当前时间* @return
     */
    public  static String gettime () {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss   参数： yyyy年MM月dd日 HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public  static String getriqi () {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");// HH:mm:ss   参数： yyyy年MM月dd日 HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
    public static boolean hasNotchInScreen(Context context) {
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("test", "hasNotchInScreen ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("test", "hasNotchInScreen NoSuchMethodException");
        } catch (Exception e) {
            Log.e("test", "hasNotchInScreen Exception");
        } finally {
            return ret;
        }
    }


    public static String getNewContent(String htmltext){ //html字符串中的图片进行适应屏幕宽度进行自适应缩放  加载到webview中
        /*try {
            Document doc= Jsoup.parse(htmltext);
            Elements elements=doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width","100%").attr("height","auto");
            }
            将每张图片都拉伸到屏幕宽度
            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }*/

        try {//如果图片小于屏幕那么按照原始尺寸显示  如果大于屏幕则缩放到屏幕大小
            return "<html> \n" +
                    "<head> \n" +
                    "<style type=\"text/css\"> \n" +
                    "body {font-size:13px;color:#666666}\n" +
                    "img{max-width:100% !important;}\n" +
                    "</style> \n" +
                    "</head> \n" +
                    "<body width=100% style=word-wrap:break-word;>" +
                    htmltext +
                    "</body>" +
                    "</html>";
        } catch (Exception e) {
            return htmltext;
        }
    }

    // TODO: 2018/10/16 将date类型转换为时间
    public static String getStringDate(Date date) {
            /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");*/
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(date);
            return dateString;
         }



}
