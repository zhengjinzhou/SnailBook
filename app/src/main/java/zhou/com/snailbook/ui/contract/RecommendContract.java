package zhou.com.snailbook.ui.contract;

import java.util.List;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.BookMixAToc;
import zhou.com.snailbook.bean.Recommend;

/**
 * Created by zhou on 2018/1/26.
 */

public interface RecommendContract {
    interface View extends BaseContract.BaseView {
        void showRecommendList(List<Recommend.RecommendBooks> list);

        void showBookToc(String bookId, List<BookMixAToc.mixToc.Chapters> list);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {

        void getRecommendList();
    }
}
