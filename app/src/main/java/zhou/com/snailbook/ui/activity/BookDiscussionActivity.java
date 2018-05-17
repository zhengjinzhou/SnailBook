package zhou.com.snailbook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import butterknife.BindView;
import zhou.com.snailbook.R;
import zhou.com.snailbook.base.BaseCommuniteActivity;
import zhou.com.snailbook.component.AppComponent;
import zhou.com.snailbook.ui.fragment.BookDiscussionFragment;
import zhou.com.snailbook.view.SelectionLayout;

/**
 * 综合讨论区
 *
 * 原创区
 *
 */
public class BookDiscussionActivity extends BaseCommuniteActivity {

    private static final String INTENT_DIS = "isDis";
    private boolean mIsDiscussion;

    @BindView(R.id.slOverall)
    SelectionLayout slOverall;

    public static void startActivity(Context context, boolean isDiscussion) {
        context.startActivity(new Intent(context, BookDiscussionActivity.class)
                .putExtra(INTENT_DIS, isDiscussion));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_discussion;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        mIsDiscussion = getIntent().getBooleanExtra(INTENT_DIS, false);
        if (mIsDiscussion) {
            mCommonToolbar.setTitle("综合讨论区");
        } else {
            mCommonToolbar.setTitle("原创区");
        }
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void configViews() {
        BookDiscussionFragment fragment = BookDiscussionFragment.newInstance(mIsDiscussion ? "ramble" : "original");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentCO, fragment).commit();
    }

    @Override
    protected List<List<String>> getTabList() {
        return list1;
    }

    @Override
    public void initDatas() {
        super.initDatas();
    }
}
