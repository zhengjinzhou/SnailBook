package zhou.com.snailbook.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zhou.com.snailbook.R;
import zhou.com.snailbook.base.BaseFragment;
import zhou.com.snailbook.bean.support.FindBean;
import zhou.com.snailbook.common.OnRvItemClickListener;
import zhou.com.snailbook.component.AppComponent;
import zhou.com.snailbook.ui.activity.SubjectBookListActivity;
import zhou.com.snailbook.ui.activity.TopCategoryListActivity;
import zhou.com.snailbook.ui.activity.TopRankActivity;
import zhou.com.snailbook.ui.adapter.FindAdapter;
import zhou.com.snailbook.view.SupportDividerItemDecoration;

/**
 * Created by zhou on 2018/1/25.
 *
 * 发现
 */

public class FindFragment extends BaseFragment implements OnRvItemClickListener {

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    private List<FindBean> mList = new ArrayList<>();
    private FindAdapter mAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_find;
    }

    @Override
    public void attachView() {

    }

    @Override
    public void initDatas() {
        mList.clear();
        mList.add(new FindBean("排行榜",R.drawable.home_find_rank));
        mList.add(new FindBean("主题书单", R.drawable.home_find_topic));
        mList.add(new FindBean("分类", R.drawable.home_find_category));
    }

    @Override
    public void configViews() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SupportDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL, true));
        mAdapter = new FindAdapter(mContext,mList,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void onItemClick(View view, int position, Object data) {
        switch (position) {
            case 0:
                startToActivity(TopRankActivity.class);
                break;
            case 1:
                startToActivity(SubjectBookListActivity.class);
                break;
            case 2:
                startToActivity(TopCategoryListActivity.class);
                break;
            default:
                break;

        }
    }
}
