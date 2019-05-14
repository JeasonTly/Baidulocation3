package com.aorise.study.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：李世林 Administrator on 2018/6/25 09:41
 * 邮箱：1871907207@qq.com
 */

public class CrashHandlerUtil implements Thread.UncaughtExceptionHandler {

    private static final String PATH;
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".trace";
    private static CrashHandlerUtil mCrashHandlerUtil;
    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;
    private Context mContext;
    private String mDirectory;
    private WeakReference<CrashListener> mCrashListener;
    private File mCrashLogFile;

    private CrashHandlerUtil() {
    }

    public static CrashHandlerUtil getInstance() {
        if(mCrashHandlerUtil == null) {
            Class var0 = CrashHandlerUtil.class;
            synchronized(CrashHandlerUtil.class) {
                if(mCrashHandlerUtil == null) {
                    mCrashHandlerUtil = new CrashHandlerUtil();
                }
            }
        }

        return mCrashHandlerUtil;
    }

    public void init(Context context, CrashHandlerUtil.CrashListener crashListener, String directory) {
        this.mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.mContext = context.getApplicationContext();
        this.mCrashListener = new WeakReference(crashListener);
        this.mDirectory = directory;
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        this.dumpExceptionToSDCard(ex);
        this.uploadExceptionToServer();
        ex.printStackTrace();
        if(!this.handleException(ex) && this.mUncaughtExceptionHandler != null) {
            this.mUncaughtExceptionHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException var4) {
                var4.printStackTrace();
            }

            Process.killProcess(Process.myPid());
            System.exit(0);
        }

    }

    private boolean handleException(Throwable ex) {
        if(ex == null) {
            return false;
        } else {
            //MobclickAgent.reportError(this.mContext, ex);
            (new Thread() {
                public void run() {
                    Looper.prepare();
                    if(CrashHandlerUtil.this.mCrashListener.get() != null) {
                        ((CrashHandlerUtil.CrashListener)CrashHandlerUtil.this.mCrashListener.get()).crashAction();
                    }

                    Looper.loop();
                }
            }).start();
            return true;
        }
    }

    private void uploadExceptionToServer() {
        if(this.mCrashListener.get() != null && this.mCrashLogFile != null) {
            ((CrashHandlerUtil.CrashListener)this.mCrashListener.get()).uploadExceptionToServer(this.mCrashLogFile);
        }

    }

    private void dumpExceptionToSDCard(Throwable ex) {
        if(!Environment.getExternalStorageState().equals("mounted")) {
            //AoriseLog.w("sdcard unmounted,skip dump exception");
        } else {
            File dir = new File(PATH + this.mDirectory);
            if(!dir.exists()) {
                dir.mkdirs();
            }

            String time = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date(System.currentTimeMillis()));
            this.mCrashLogFile = new File(PATH + this.mDirectory + "crash" + time + ".trace");

            try {
                PrintWriter e = new PrintWriter(new BufferedWriter(new FileWriter(this.mCrashLogFile)));
                e.println(time);
                this.dumpPhoneInfo(e);
                e.println();
                ex.printStackTrace(e);
                e.close();
            } catch (IOException var5) {
                var5.printStackTrace();
            }

        }
    }

    private void dumpPhoneInfo(PrintWriter printWriter) {
        try {
            PackageManager e = this.mContext.getPackageManager();
            PackageInfo packageInfo = e.getPackageInfo(this.mContext.getPackageName(), 1);
            printWriter.print("App Version:");
            printWriter.print(packageInfo.versionName);
            printWriter.print('_');
            printWriter.println(packageInfo.versionCode);
            printWriter.print("OS Version:");
            printWriter.print(Build.VERSION.RELEASE);
            printWriter.print('_');
            printWriter.println(Build.VERSION.SDK_INT);
            printWriter.print("Vendor:");
            printWriter.println(Build.MANUFACTURER);
            printWriter.print("Model:");
            printWriter.println(Build.MODEL);
            printWriter.print("CPU ABI:");
            printWriter.println(Build.CPU_ABI);
        } catch (PackageManager.NameNotFoundException var4) {
            var4.printStackTrace();
        }

    }

    static {
        PATH = Environment.getExternalStorageDirectory().getPath() + File.separator;
    }

    public interface CrashListener {
        void uploadExceptionToServer(File var1);

        void crashAction();
    }

}
