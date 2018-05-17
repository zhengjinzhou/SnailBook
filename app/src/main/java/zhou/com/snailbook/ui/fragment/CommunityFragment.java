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
import zhou.com.snailbook.ui.activity.BookDiscussionActivity;
import zhou.com.snailbook.ui.activity.BookHelpActivity;
import zhou.com.snailbook.ui.activity.BookReviewActivity;
import zhou.com.snailbook.ui.activity.GirlBookDiscussionActivity;
import zhou.com.snailbook.ui.adapter.FindAdapter;
import zhou.com.snailbook.view.SupportDividerItemDecoration;

/**
 * Created by zhou on 2018/1/25.
 * <p>
 * 社区
 */

public class CommunityFragment extends BaseFragment implements OnRvItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
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
        mList.add(new FindBean("综合讨论区", R.drawable.discuss_section));
        mList.add(new FindBean("书评区", R.drawable.comment_section));
        mList.add(new FindBean("书荒互助区", R.drawable.helper_section));
        mList.add(new FindBean("女生区", R.drawable.girl_section));
        mList.add(new FindBean("原创区", R.drawable.yuanchuang));
    }

    @Override
    public void configViews() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SupportDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL, true));
        mAdapter = new FindAdapter(mContext, mList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void onItemClick(View view, int position, Object data) {
        switch (position) {
            case 0:
                BookDiscussionActivity.startActivity(activity, true);
                break;
            case 1:
                startToActivity(BookReviewActivity.class);
                break;
            case 2:
                startToActivity(BookHelpActivity.class);
                break;
            case 3:
                startToActivity(GirlBookDiscussionActivity.class);
                break;
            case 4:
                BookDiscussionActivity.startActivity(activity, false);
                break;
            default:
                break;

        }
    }
}
