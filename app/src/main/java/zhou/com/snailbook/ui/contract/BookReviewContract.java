package zhou.com.snailbook.ui.contract;

import java.util.List;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.BookReviewList;

/**
 * Created by zhou on 2018/1/27.
 */

public interface BookReviewContract {

    interface View extends BaseContract.BaseView {
        void showBookReviewList(List<BookReviewList.ReviewsBean> list, boolean isRefresh);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getBookReviewList(String sort, String type, String distillate, int start, int limit);
    }

}
