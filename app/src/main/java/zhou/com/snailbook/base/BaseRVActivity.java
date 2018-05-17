package zhou.com.snailbook.base;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.lang.reflect.Constructor;

import butterknife.BindView;
import zhou.com.snailbook.R;
import zhou.com.snailbook.base.BaseActivity;
import zhou.com.snailbook.utils.NetworkUtils;
import zhou.com.snailbook.view.recyclerview.EasyRecyclerView;
import zhou.com.snailbook.view.recyclerview.adapter.OnLoadMoreListener;
import zhou.com.snailbook.view.recyclerview.adapter.RecyclerArrayAdapter;
import zhou.com.snailbook.view.recyclerview.swipe.OnRefreshListener;

/**
 * Created by zhou on 2018/1/26.
 */

public abstract class BaseRVActivity<T> extends BaseActivity implements OnLoadMoreListener, OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener {

    @BindView(R.id.recyclerview)
    protected EasyRecyclerView mRecyclerView;
    protected RecyclerArrayAdapter<T> mAdapter;

    protected int start = 0;
    protected int limit = 20;


    protected void initAdapter(boolean refreshable, boolean loadmoreable) {
        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(this);
            mAdapter.setError(R.layout.common_error_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.resumeMore();
                }
            });
            if (loadmoreable) {
                mAdapter.setMore(R.layout.common_more_view, this);
                mAdapter.setNoMore(R.layout.common_nomore_view);
            }
            if (refreshable && mRecyclerView != null) {
                mRecyclerView.setRefreshListener(this);
            }
        }

        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setItemDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), 1, 0, 0);
            mRecyclerView.setAdapterWithProgress(mAdapter);
        }
    }

    protected void initAdapter(Class<? extends RecyclerArrayAdapter<T>> clazz, boolean refreshable, boolean loadmoreable) {
        mAdapter = (RecyclerArrayAdapter) createInstance(clazz);
        initAdapter(refreshable, loadmoreable);
    }

    public Object createInstance(Class<?> cls) {
        Object obj;
        try {
            Constructor c1 = cls.getDeclaredConstructor(Context.class);
            c1.setAccessible(true);
            obj = c1.newInstance(mContext);
        } catch (Exception e) {
            obj = null;
        }
        return obj;
    }

    @Override
    public void onLoadMore() {
        if (!NetworkUtils.isConnected(getApplicationContext())) {
            mAdapter.pauseMore();
            return;
        }
    }

    @Override
    public void onRefresh() {
        start = 0;
        if (!NetworkUtils.isConnected(getApplicationContext())) {
            mAdapter.pauseMore();
            return;
        }
    }

    protected void loaddingError(){
        mAdapter.clear();
        mAdapter.pauseMore();
        mRecyclerView.setRefreshing(false);
    }
}
