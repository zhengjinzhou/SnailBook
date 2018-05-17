package zhou.com.snailbook.ui.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.util.LogTime;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

import butterknife.BindView;
import zhou.com.snailbook.R;
import zhou.com.snailbook.base.BaseActivity;
import zhou.com.snailbook.base.Constant;
import zhou.com.snailbook.component.AppComponent;
import zhou.com.snailbook.component.DaggerMainComponent;
import zhou.com.snailbook.manager.EventManager;
import zhou.com.snailbook.manager.SettingManager;
import zhou.com.snailbook.service.DownloadBookService;
import zhou.com.snailbook.ui.contract.MainContract;
import zhou.com.snailbook.ui.fragment.CommunityFragment;
import zhou.com.snailbook.ui.fragment.FindFragment;
import zhou.com.snailbook.ui.fragment.RecommendFragment;
import zhou.com.snailbook.ui.presenter.MainActivityPresenter;
import zhou.com.snailbook.utils.SharedPreferencesUtil;
import zhou.com.snailbook.utils.ToastUtils;
import zhou.com.snailbook.view.GenderPopupWindow;
import zhou.com.snailbook.view.LoginPopupWindow;
import zhou.com.snailbook.view.RVPIndicator;

public class MainActivity extends BaseActivity implements MainContract.View {

    @BindView(R.id.indicator)
    RVPIndicator indicator;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Inject
    MainActivityPresenter mPresenter;
    // 退出时间
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 2000;
    private List<Fragment> mTabContents;
    private List<String> mDatas;
    private FragmentPagerAdapter mAdapter;
    private GenderPopupWindow genderPopupWindow;
    private LoginPopupWindow popupWindow;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent.builder().appComponent(appComponent).build().inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("蜗牛");
        mCommonToolbar.setLogo(R.drawable.logo);
    }

    public void pullSyncBookShelf() {
        mPresenter.syncBookShelf();
    }

    @Override
    public void initDatas() {
        //requestWriteSettings();

        setLight();
        initPermission();

        startService(new Intent(this, DownloadBookService.class));

        mDatas = Arrays.asList(getResources().getStringArray(R.array.home_tabs));
        mTabContents = new ArrayList<>();
        mTabContents.add(new RecommendFragment());
        mTabContents.add(new CommunityFragment());
        mTabContents.add(new FindFragment());
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mTabContents.get(position);
            }

            @Override
            public int getCount() {
                return mTabContents.size();
            }
        };
    }

    @Override
    public void configViews() {
        indicator.setTabItemTitles(mDatas);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(3);
        indicator.setViewPager(viewPager, 0);

        mPresenter.attachView(this);

        indicator.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!SettingManager.getInstance().isUserChooseSex()
                        && (genderPopupWindow == null || !genderPopupWindow.isShowing())) {
                    showChooseSexPopupWindow();
                } else {
                    showDialog();
                    mPresenter.syncBookShelf();
                }
            }
        }, 500);
    }

    public void setCurrentItem(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void showError() {
        ToastUtils.showSingleToast("同步异常");
        dismissDialog();
    }

    @Override
    public void complete() {

    }

    @Override
    public void syncBookShelfCompleted() {
        dismissDialog();
        EventManager.refreshCollectionList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * 显示item中的图片；
     *
     * @param view
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * menu的item的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search://搜索
                startToActivity(SearchActivity.class);
                break;
            case R.id.action_login:
                if (popupWindow == null) {
                    popupWindow = new LoginPopupWindow(this);
                }
                popupWindow.showAtLocation(mCommonToolbar, Gravity.CENTER, 0, 0);
                break;

            case R.id.action_my_message:
                if (popupWindow == null) {
                    popupWindow = new LoginPopupWindow(this);
                }
                popupWindow.showAtLocation(mCommonToolbar, Gravity.CENTER, 0, 0);
                break;

            case R.id.action_book:
                showDialog();
                mPresenter.syncBookShelf();
                break;

            case R.id.action_local_book:
                startToActivity(ScanLocalBookActivity.class);
                break;

            case R.id.action_wifi:
                //startToActivity(WifiBookActivity.class);
                ToastUtils.showLongToast("正在拼命开发中~");
                break;

            case R.id.action_feed:
                startToActivity(FeedbackActivity.class);
                break;

            case R.id.action_night:

                if (SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false)) {
                    SharedPreferencesUtil.getInstance().putBoolean(Constant.ISNIGHT, false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    SharedPreferencesUtil.getInstance().putBoolean(Constant.ISNIGHT, true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                recreate();
                break;

            case R.id.action_setting:
                startToActivity(SettingActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 再按一次退出程序
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
                currentBackPressedTime = System.currentTimeMillis();
                ToastUtils.showToast(getString(R.string.exit_tips));
                return true;
            } else {
                finish();
            }
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DownloadBookService.cancel();
        stopService(new Intent(this, DownloadBookService.class));
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    public void showChooseSexPopupWindow() {
        if (genderPopupWindow == null) {
            genderPopupWindow = new GenderPopupWindow(MainActivity.this);
        }
        if (!SettingManager.getInstance().isUserChooseSex()
                && !genderPopupWindow.isShowing()) {
            genderPopupWindow.showAtLocation(mCommonToolbar, Gravity.CENTER, 0, 0);
        }
    }

    /**
     * 设置亮度问题，签名打包后删掉试试
     */
    private static final int REQUEST_CODE_ASK_WRITE_SETTINGS = 1111;

    private void setLight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, REQUEST_CODE_ASK_WRITE_SETTINGS);
            } else {
                Log.d("", "setLight: ");
            }
        }
    }

    private static final int REQUEST_CODE_WRITE_SETTINGS = 1;

    private void requestWriteSettings() {
        //Log.d("", "requestWriteSettings: "+SharedPreferencesUtil.getInstance().getBoolean("isZhou"));
        if (!SharedPreferencesUtil.getInstance().getBoolean("isZhou")) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_CODE_WRITE_SETTINGS);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_WRITE_SETTINGS) {
            if (Settings.System.canWrite(this)) {
                Log.i("", "onActivityResult write settings granted");
                SharedPreferencesUtil.getInstance().putBoolean("isZhou", true);
            } else {

            }
        }
        if (requestCode == REQUEST_CODE_ASK_WRITE_SETTINGS) {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 动态请求权限
     */
    private void initPermission() {
        requestRunTimePermission(new String[]{
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_SETTINGS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,

                }
                , new PermissionListener() {
                    @Override
                    public void onGranted() {  //所有权限授权成功
                        Log.d("", "onGranted: ");
                    }

                    @Override
                    public void onGranted(List<String> grantedPermission) { //授权失败权限集合
                        Log.d("", "onGranted: " + grantedPermission.toString());
                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) { //授权成功权限集合
                        Log.d("", "onDenied: " + deniedPermission.toString());
                    }
                });
    }
}
