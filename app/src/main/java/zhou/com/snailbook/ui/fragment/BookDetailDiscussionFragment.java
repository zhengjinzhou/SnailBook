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
import zhou.com.snailbook.component.DaggerBookComponent;
import zhou.com.snailbook.ui.activity.BookDiscussionDetailActivity;
import zhou.com.snailbook.ui.adapter.BookDiscussionAdapter;
import zhou.com.snailbook.ui.contract.BookDetailDiscussionContract;
import zhou.com.snailbook.ui.presenter.BookDetailDiscussionPresenter;

/**
 * Created by zhou on 2018/1/26.
 */

public class BookDetailDiscussionFragment extends BaseRVFragment<BookDetailDiscussionPresenter, DiscussionList.PostsBean> implements BookDetailDiscussionContract.View {

    public final static String BUNDLE_ID = "bookId";

    public static BookDetailDiscussionFragment newInstance(String id) {
        BookDetailDiscussionFragment fragment = new BookDetailDiscussionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String bookId;

    private String sort = Constant.SortType.DEFAULT;



    @Override
    public void showError() {
        loaddingError();
    }

    @Override
    public void complete() {
        mRecyclerView.setRefreshing(false);
    }

    @Override
    public void showBookDetailDiscussionList(List<DiscussionList.PostsBean> list, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
            start = 0;
        }
        mAdapter.addAll(list);
        start = start + list.size();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.common_easy_recyclerview;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        bookId = getArguments().getString(BUNDLE_ID);
    }

    @Override
    public void configViews() {
        initAdapter(BookDiscussionAdapter.class, true, true);
        onRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initCategoryList(SelectionEvent event) {
        if (getUserVisibleHint()) {
            mRecyclerView.setRefreshing(true);
            sort = event.sort;
            onRefresh();
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void onItemClick(int position) {
        DiscussionList.PostsBean data = mAdapter.getItem(position);
        BookDiscussionDetailActivity.startActivity(activity, data._id);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getBookDetailDiscussionList(bookId, sort, 0, limit);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getBookDetailDiscussionList(bookId, sort, start, limit);
    }
}
