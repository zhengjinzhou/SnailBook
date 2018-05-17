package zhou.com.snailbook.ui.contract;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.BookListTags;

/**
 * Created by zhou on 2018/1/26.
 */

public interface SubjectBookListContract {

    interface View extends BaseContract.BaseView {
        void showBookListTags(BookListTags data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookListTags();
    }
}

