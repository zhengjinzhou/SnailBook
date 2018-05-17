package zhou.com.snailbook.ui.contract;

import java.util.List;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.DiscussionList;

/**
 * Created by zhou on 2018/1/27.
 */

public interface GirlBookDiscussionContract {

    interface View extends BaseContract.BaseView {
        void showGirlBookDisscussionList(List<DiscussionList.PostsBean> list, boolean isRefresh);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getGirlBookDisscussionList(String sort, String distillate, int start, int limit);
    }

}