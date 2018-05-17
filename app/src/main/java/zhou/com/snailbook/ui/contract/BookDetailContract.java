package zhou.com.snailbook.ui.contract;

import java.util.List;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.BookDetail;
import zhou.com.snailbook.bean.HotReview;
import zhou.com.snailbook.bean.RecommendBookList;

/**
 * Created by zhou on 2018/1/26.
 */

public interface BookDetailContract {

    interface View extends BaseContract.BaseView{
        void showBookDetail(BookDetail data);

        void showHotReview(List<HotReview.Reviews> list);

        void showRecommendBookList(List<RecommendBookList.RecommendBook> list);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookDetail(String bookId);

        void getHotReview(String book);

        void getRecommendBookList(String bookId, String limit);
    }
}
