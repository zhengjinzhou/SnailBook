package zhou.com.snailbook.ui.contract;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.BooksByCats;

/**
 * Created by zhou on 2018/1/26.
 */

public interface SubRankContract {

    interface View extends BaseContract.BaseView {
        void showRankList(BooksByCats data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getRankList(String id);
    }
}