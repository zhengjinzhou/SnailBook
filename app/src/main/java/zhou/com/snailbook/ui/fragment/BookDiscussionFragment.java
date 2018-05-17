package zhou.com.snailbook.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import zhou.com.snailbook.R;
import zhou.com.snailbook.base.BaseRVFragment;
import zhou.com.snailbook.base.Constant;
import zhou.com.snailbook.bean.DiscussionList;
import zhou.com.snailbook.bean.support.SelectionEvent;
import zhou.com.snailbook.component.AppComponent;
import zhou.com.snailbook.component.DaggerCommunityComponent;
import zhou.com.snailbook.ui.activity.BookDiscussionDetailActivity;
import zhou.com.snailbook.ui.adapter.BookDiscussionAdapter;
import zhou.com.snailbook.ui.contract.BookDiscussionContract;
import zhou.com.snailbook.ui.presenter.BookDiscussionPresenter;

/**
 * Created by zhou on 2018/1/26.
 * 综合讨论区 Fragment
 */

public class BookDiscussionFragment extends BaseRVFragment<BookDiscussionPresenter, DiscussionList.PostsBean> implements BookDiscussionContract.View {

    private static final String BUNDLE_BLOCK = "block";

    public static BookDiscussionFragment newInstance(String block) {
        BookDiscussionFragment fragment = new BookDiscussionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_BLOCK, block);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String block = "ramble";
    private String sort = Constant.SortType.DEFAULT;
    private String distillate = Constant.Distillate.ALL;

    @Override
    public int getLayoutResId() {
        return R.layout.common_easy_recyclerview;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCommunityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initDatas() {
        block = getArguments().getString(BUNDLE_BLOCK);
        EventBus.getDefault().register(this);
    }

    @Override
    public void configViews() {
        initAdapter(BookDiscussionAdapter.class, true, true);
        onRefresh();
    }

    @Override
    public void showBookDisscussionList(List<DiscussionList.PostsBean> list, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
            start = 0;
        }
        mAdapter.addAll(list);
        start = start + list.size();
    }

    @Override
    public void showError() {
        loaddingError();
    }

    @Override
    public void complete() {
        mRecyclerView.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initCategoryList(SelectionEvent event) {
        mRecyclerView.setRefreshing(true);
        sort = event.sort;
        distillate = event.distillate;
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getBookDisscussionList(block, sort, distillate, 0, limit);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getBookDisscussionList(block, sort, distillate, start, limit);
    }

    @Override
    public void onItemClick(int position) {
        DiscussionList.PostsBean data = mAdapter.getItem(position);
        BookDiscussionDetailActivity.startActivity(activity, data._id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
