package zhou.com.snailbook.ui.fragment;

import android.os.Bundle;

import zhou.com.snailbook.R;
import zhou.com.snailbook.base.BaseRVFragment;
import zhou.com.snailbook.bean.BooksByCats;
import zhou.com.snailbook.component.AppComponent;
import zhou.com.snailbook.component.DaggerFindComponent;
import zhou.com.snailbook.ui.activity.BookDetailActivity;
import zhou.com.snailbook.ui.adapter.SubCategoryAdapter;
import zhou.com.snailbook.ui.contract.SubRankContract;
import zhou.com.snailbook.ui.presenter.SubRankPresenter;

/**
 * Created by zhou on 2018/1/26.
 *
 * 二级排行榜  周榜 月榜  总榜
 */

public class SubRankFragment extends BaseRVFragment<SubRankPresenter, BooksByCats.BooksBean> implements SubRankContract.View {

    public final static String BUNDLE_ID = "_id";
    private String id;

    public static SubRankFragment newInstance(String id) {
        SubRankFragment fragment = new SubRankFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID, id);
        fragment.setArguments(bundle);
        return fragment;
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
    public int getLayoutResId() {
        return R.layout.common_easy_recyclerview;
    }

    @Override
    public void initDatas() {
        id = getArguments().getString(BUNDLE_ID);
    }

    @Override
    public void configViews() {
        initAdapter(SubCategoryAdapter.class, true, false);

        onRefresh();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFindComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getRankList(id);
    }

    @Override
    public void onItemClick(int position) {
        BookDetailActivity.startActivity(activity, mAdapter.getItem(position)._id);
    }

}
