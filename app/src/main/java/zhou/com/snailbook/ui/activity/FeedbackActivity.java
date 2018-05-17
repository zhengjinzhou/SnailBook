package zhou.com.snailbook.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zhou.com.snailbook.R;
import zhou.com.snailbook.base.BaseActivity;
import zhou.com.snailbook.component.AppComponent;

public class FeedbackActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("意见反馈");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {

    }

}
