package zhou.com.snailbook.ui.activity;

import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import zhou.com.snailbook.R;
import zhou.com.snailbook.base.BaseActivity;
import zhou.com.snailbook.bean.RankingList;
import zhou.com.snailbook.common.OnRvItemClickListener;
import zhou.com.snailbook.component.AppComponent;
import zhou.com.snailbook.component.DaggerFindComponent;
import zhou.com.snailbook.ui.adapter.TopRankAdapter;
import zhou.com.snailbook.ui.contract.TopRankContract;
import zhou.com.snailbook.ui.presenter.TopRankPresenter;
import zhou.com.snailbook.utils.ToastUtils;

/**
 * 排行榜
 *
 */
public class TopRankActivity extends BaseActivity implements TopRankContract.View {

    @BindView(R.id.elvFeMale)
    ExpandableListView elvFeMale;
    @BindView(R.id.elvMale)
    ExpandableListView elvMale;

    private List<RankingList.MaleBean> maleGroups = new ArrayList<>();
    private List<List<RankingList.MaleBean>> maleChilds = new ArrayList<>();
    private TopRankAdapter maleAdapter;

    private List<RankingList.MaleBean> femaleGroups = new ArrayList<>();
    private List<List<RankingList.MaleBean>> femaleChilds = new ArrayList<>();
    private TopRankAdapter femaleAdapter;

    @Inject TopRankPresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_top_rank;
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
        mCommonToolbar.setTitle("排行榜");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        maleAdapter = new TopRankAdapter(this, maleGroups, maleChilds);
        femaleAdapter = new TopRankAdapter(this, femaleGroups, femaleChilds);
        maleAdapter.setItemClickListener(new ClickListener());
        femaleAdapter.setItemClickListener(new ClickListener());
    }

    @Override
    public void configViews() {
        showDialog();
        elvMale.setAdapter(maleAdapter);
        elvFeMale.setAdapter(femaleAdapter);

       mPresenter.attachView(this);
       mPresenter.getRankList();
    }

    @Override
    public void showError() {
        ToastUtils.showToast("连接超时，请检查网络");
    }

    @Override
    public void complete() {
        dismissDialog();
    }

    @Override
    public void showRankList(RankingList rankingList) {
        maleGroups.clear();
        femaleGroups.clear();
        updateMale(rankingList);
        updateFemale(rankingList);
    }

    private void updateMale(RankingList rankingList) {
        List<RankingList.MaleBean> list = rankingList.male;
        List<RankingList.MaleBean> collapse = new ArrayList<>();
        for (RankingList.MaleBean bean : list) {
            if (bean.collapse) { // 折叠
                collapse.add(bean);
            } else {
                maleGroups.add(bean);
                maleChilds.add(new ArrayList<RankingList.MaleBean>());
            }
        }
        if (collapse.size() > 0) {
            maleGroups.add(new RankingList.MaleBean("别人家的排行榜"));
            maleChilds.add(collapse);
        }
        maleAdapter.notifyDataSetChanged();
    }

    private void updateFemale(RankingList rankingList) {
        List<RankingList.MaleBean> list = rankingList.female;
        List<RankingList.MaleBean> collapse = new ArrayList<>();
        for (RankingList.MaleBean bean : list) {
            if (bean.collapse) { // 折叠
                collapse.add(bean);
            } else {
                femaleGroups.add(bean);
                femaleChilds.add(new ArrayList<RankingList.MaleBean>());
            }
        }
        if (collapse.size() > 0) {
            femaleGroups.add(new RankingList.MaleBean("别人家的排行榜"));
            femaleChilds.add(collapse);
        }
        femaleAdapter.notifyDataSetChanged();
    }

    class ClickListener implements OnRvItemClickListener<RankingList.MaleBean> {

        @Override
        public void onItemClick(View view, int position, RankingList.MaleBean data) {
            if (data.monthRank == null) {
               SubOtherHomeRankActivity.startActivity(mContext, data._id, data.title);
            } else {
               SubRankActivity.startActivity(mContext, data._id, data.monthRank, data.totalRank, data.title);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
