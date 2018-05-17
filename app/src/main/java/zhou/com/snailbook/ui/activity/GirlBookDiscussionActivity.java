package zhou.com.snailbook.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import butterknife.BindView;
import zhou.com.snailbook.R;
import zhou.com.snailbook.base.BaseCommuniteActivity;
import zhou.com.snailbook.component.AppComponent;
import zhou.com.snailbook.view.SelectionLayout;

/**
 * 女生区
 */
public class GirlBookDiscussionActivity extends BaseCommuniteActivity {

    @BindView(R.id.slOverall)
    SelectionLayout slOverall;

    @Override
    public int getLayoutId() {
        return R.layout.activity_girl_book_discussion;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("女生区");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void configViews() {

    }

    @Override
    public void initDatas() {
        super.initDatas();
    }

    @Override
    protected List<List<String>> getTabList() {
        return list1;
    }
}
