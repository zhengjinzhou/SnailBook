package zhou.com.snailbook.ui.contract;

import java.util.List;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.BookHelpList;

/**
 * Created by zhou on 2018/1/27.
 */

public interface BookHelpContract {

    interface View extends BaseContract.BaseView {
        void showBookHelpList(List<BookHelpList.HelpsBean> list, boolean isRefresh);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getBookHelpList(String sort, String distillate, int start, int limit);
    }

}
