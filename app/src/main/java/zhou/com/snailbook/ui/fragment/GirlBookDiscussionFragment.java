package zhou.com.snailbook.ui.fragment;

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
import zhou.com.snailbook.ui.contract.GirlBookDiscussionContract;
import zhou.com.snailbook.ui.presenter.GirlBookDiscussionPresenter;

/**
 * Created by zhou on 2018/1/26.
 */

public class GirlBookDiscussionFragment extends BaseRVFragment<GirlBookDiscussionPresenter, DiscussionList.PostsBean> implements GirlBookDiscussionContract.View {

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
        EventBus.getDefault().register(this);
    }

    @Override
    public void configViews() {
        initAdapter(BookDiscussionAdapter.class, true, true);
        onRefresh();
    }

    @Override
    public void showGirlBookDisscussionList(List<DiscussionList.PostsBean> list, boolean isRefresh) {
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
        mPresenter.getGirlBookDisscussionList(sort, distillate, 0, limit);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getGirlBookDisscussionList(sort, distillate, start, limit);
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
