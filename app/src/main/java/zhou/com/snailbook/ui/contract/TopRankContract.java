package zhou.com.snailbook.ui.contract;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.RankingList;

/**
 * Created by zhou on 2018/1/26.
 */

public interface TopRankContract {
    interface View extends BaseContract.BaseView{
        void showRankList(RankingList rankingList);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        void getRankList();
    }
}
