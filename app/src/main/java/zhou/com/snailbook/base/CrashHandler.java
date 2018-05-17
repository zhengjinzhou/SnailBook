package zhou.com.snailbook.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import zhou.com.snailbook.service.DownloadBookService;
import zhou.com.snailbook.utils.FileUtils;
import zhou.com.snailbook.utils.LogUtils;
import zhou.com.snailbook.utils.ToastUtils;

/**
 * Created by zhou on 2018/1/25.
 *
 * 全局异常捕获，当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    //系统默认的UncaughtExceptionHandler处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //crashHandler实例
    private static CrashHandler INSTANCE;
    //程序的Context对象
    private Context mContext;
    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<>();
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     *
     * @return
     */
    public static CrashHandler getInstance(){
        if (INSTANCE == null){
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    public void init(Context context){
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null){
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(t, ex);
        }else {
            DownloadBookService.cancel(); // 取消任务
            LogUtils.i("取消下载任务");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    ToastUtils.showSingleToast("哎呀，程序发生异常啦...");
                    Looper.loop();
                }
            }).start();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                LogUtils.e("CrashHandler.InterruptedException--->" + e.toString());
            }
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     * @param ex
     * @return 如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex){
        if (ex == null){
            return false;
        }
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件
        saveCrashInfo2File(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param context
     */
    public void collectDeviceInfo(Context context){
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (info != null){
                String versionName = info.versionName == null ? "null" : info.versionName;
                String versionCode = info.versionCode + "";
                infos.put("versionName",versionName);
                infos.put("versionCode",versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.e("CrashHandleran.NameNotFoundException---> error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields){
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                LogUtils.e("CrashHandler.NameNotFoundException---> an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     * @param ex
     * @return 返回文件名称
     */
    private String saveCrashInfo2File(Throwable ex){
        StringBuffer sb = new StringBuffer();
        sb.append("---------------------sta--------------------------");
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        sb.append("--------------------end---------------------------");
        LogUtils.e(sb.toString());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileName = format.format(new Date()) + ".log";
        File file = new File(FileUtils.createRootPath(mContext) + "/log/" + fileName);
        FileUtils.createFile(file);
        FileUtils.writeFile(file.getAbsolutePath(), sb.toString());
        // uploadCrashMessage(sb.toString());
        return null;
    }
}
