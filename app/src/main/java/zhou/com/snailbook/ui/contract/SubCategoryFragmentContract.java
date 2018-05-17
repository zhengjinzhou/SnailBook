package zhou.com.snailbook.ui.contract;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.BooksByCats;

/**
 * Created by zhou on 2018/1/27.
 */

public interface SubCategoryFragmentContract {

    interface View extends BaseContract.BaseView {
        void showCategoryList(BooksByCats data, boolean isRefresh);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getCategoryList(String gender, String major, String minor, String type, int start, int limit);
    }

}
