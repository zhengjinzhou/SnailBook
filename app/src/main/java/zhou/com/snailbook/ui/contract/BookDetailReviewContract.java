package zhou.com.snailbook.ui.contract;

import java.util.List;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.HotReview;

/**
 * Created by zhou on 2018/1/26.
 */

public interface BookDetailReviewContract {

    interface View extends BaseContract.BaseView {
        void showBookDetailReviewList(List<HotReview.Reviews> list, boolean isRefresh);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookDetailReviewList(String bookId, String sort, int start, int limit);
    }
}