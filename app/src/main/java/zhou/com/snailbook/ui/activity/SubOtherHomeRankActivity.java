package zhou.com.snailbook.ui.activity;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

import zhou.com.snailbook.R;
import zhou.com.snailbook.base.BaseRVActivity;
import zhou.com.snailbook.bean.BooksByCats;
import zhou.com.snailbook.component.AppComponent;
import zhou.com.snailbook.component.DaggerFindComponent;
import zhou.com.snailbook.ui.adapter.SubCategoryAdapter;
import zhou.com.snailbook.ui.contract.SubRankContract;
import zhou.com.snailbook.ui.presenter.SubRankPresenter;

public class SubOtherHomeRankActivity extends BaseRVActivity<BooksByCats.BooksBean> implements SubRankContract.View {

    public final static String BUNDLE_ID = "_id";
    public static final String INTENT_TITLE = "title";
    private String id;
    private String title;

    public static void startActivity(Context context, String id, String title) {
        context.startActivity(new Intent(context, SubOtherHomeRankActivity.class)
                .putExtra(INTENT_TITLE, title).putExtra(BUNDLE_ID, id));
    }

    @Inject
    SubRankPresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sub_other_home_rank;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFindComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        title = getIntent().getStringExtra(INTENT_TITLE).split(" ")[0];
        id = getIntent().getStringExtra(BUNDLE_ID);

        mCommonToolbar.setTitle(title);
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        initAdapter(SubCategoryAdapter.class, true, false);
        mPresenter.attachView(this);
        onRefresh();
    }

    @Override
    public void showError() {
        loaddingError();
    }

    @Override
    public void complete() {
        mRecyclerView.setRefreshing(false);
    }

    @Override
    public void showRankList(BooksByCats data) {
        mAdapter.clear();
        mAdapter.addAll(data.books);
    }

    @Override
    public void onItemClick(int position) {
        //BookDetailActivity.startActivity(this, mAdapter.getItem(position)._id);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getRankList(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }
}
