package zhou.com.snailbook.ui.contract;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.BookListDetail;

/**
 * Created by zhou on 2018/1/27.
 */

public interface SubjectBookListDetailContract {

    interface View extends BaseContract.BaseView {
        void showBookListDetail(BookListDetail data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookListDetail(String bookListId);
    }
}

