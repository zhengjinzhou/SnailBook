package zhou.com.snailbook.base;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import zhou.com.snailbook.component.AppComponent;
import zhou.com.snailbook.component.DaggerAppComponent;
import zhou.com.snailbook.module.AppModule;
import zhou.com.snailbook.module.BookApiModule;
import zhou.com.snailbook.utils.AppUtils;
import zhou.com.snailbook.utils.LogUtils;
import zhou.com.snailbook.utils.SharedPreferencesUtil;

/**
 * Created by zhou on 2018/1/25.
 */

public class ReaderApplication extends Application {

    private static ReaderApplication sInstance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initCompoent();
        AppUtils.init(this);
        CrashHandler.getInstance().init(this);
        initPrefs();
        initNightMode();
    }

    public static ReaderApplication getsInstance() {
        return sInstance;
    }
    public AppComponent getAppComponent() {
        return appComponent;
    }

    private void initCompoent() {
        appComponent = DaggerAppComponent.builder()
                .bookApiModule(new BookApiModule())
                .appModule(new AppModule(this))
                .build();
    }

    /**
     * 初始化SharedPreference
     */
    protected void initPrefs() {
        SharedPreferencesUtil.init(getApplicationContext(), getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
    }

    protected void initNightMode() {
        boolean isNight = SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false);
        LogUtils.d("isNight=" + isNight);
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

}
