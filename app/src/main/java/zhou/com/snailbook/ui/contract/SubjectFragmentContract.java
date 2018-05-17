package zhou.com.snailbook.ui.contract;

import java.util.List;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.BookLists;

/**
 * Created by zhou on 2018/1/26.
 */

public interface SubjectFragmentContract {

    interface View extends BaseContract.BaseView {
        void showBookList(List<BookLists.BookListsBean> bookLists, boolean isRefresh);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookLists(String duration, String sort, int start, int limit, String tag, String gender);
    }
}

