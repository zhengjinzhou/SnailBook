package zhou.com.snailbook.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import zhou.com.snailbook.R;
import zhou.com.snailbook.base.BaseCommuniteActivity;
import zhou.com.snailbook.component.AppComponent;

/**
 * 书荒求助区
 */

public class BookHelpActivity extends BaseCommuniteActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_help;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("书荒互助区");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        super.initDatas();
    }

    @Override
    public void configViews() {

    }

    @Override
    protected List<List<String>> getTabList() {
        return list1;
    }
}
