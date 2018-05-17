package zhou.com.snailbook.ui.activity;

import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import zhou.com.snailbook.R;
import zhou.com.snailbook.base.BaseRVFragment;
import zhou.com.snailbook.base.Constant;
import zhou.com.snailbook.bean.BooksByCats;
import zhou.com.snailbook.bean.support.SubEvent;
import zhou.com.snailbook.component.AppComponent;
import zhou.com.snailbook.component.DaggerFindComponent;
import zhou.com.snailbook.ui.adapter.SubCategoryAdapter;
import zhou.com.snailbook.ui.contract.SubCategoryFragmentContract;
import zhou.com.snailbook.ui.presenter.SubCategoryFragmentPresenter;

/**
 * 分类详情
 * 子界面 显示
 *
 * created by zhou
 */
public class SubCategoryFragment extends BaseRVFragment<SubCategoryFragmentPresenter, BooksByCats.BooksBean> implements SubCategoryFragmentContract.View {

    public final static String BUNDLE_MAJOR = "major";
    public final static String BUNDLE_MINOR = "minor";
    public final static String BUNDLE_GENDER = "gender";
    public final static String BUNDLE_TYPE = "type";

    public static SubCategoryFragment newInstance(String major, String minor, String gender,
                                                  @Constant.CateType String type) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_MAJOR, major);
        bundle.putString(BUNDLE_GENDER, gender);
        bundle.putString(BUNDLE_MINOR, minor);
        bundle.putString(BUNDLE_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String major = "";
    private String minor = "";
    private String gender = "";
    private String type = "";

    @Override
    public int getLayoutResId() {
        return R.layout.common_easy_recyclerview;
    }

    @Override
    public void initDatas() {
        EventBus.getDefault().register(this);
        major = getArguments().getString(BUNDLE_MAJOR);
        gender = getArguments().getString(BUNDLE_GENDER);
        minor = getArguments().getString(BUNDLE_MINOR);
        type = getArguments().getString(BUNDLE_TYPE);
    }

    @Override
    public void configViews() {
        initAdapter(SubCategoryAdapter.class, true, true);
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
    public void showCategoryList(BooksByCats data, boolean isRefresh) {
        if (isRefresh) {
            start = 0;
            mAdapter.clear();
        }
        mAdapter.addAll(data.books);
        start += data.books.size();
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
    public void initCategoryList(SubEvent event) {
        minor = event.minor;
        String type = event.type;
        if (this.type.equals(type)) {
            onRefresh();
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onItemClick(int position) {
        BooksByCats.BooksBean data = mAdapter.getItem(position);
        BookDetailActivity.startActivity(activity, data._id);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getCategoryList(gender, major, minor, this.type, 0, limit);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getCategoryList(gender, major, minor, this.type, start, limit);
    }
}
