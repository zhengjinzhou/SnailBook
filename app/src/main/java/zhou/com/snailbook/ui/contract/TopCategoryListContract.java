package zhou.com.snailbook.ui.contract;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.CategoryList;

/**
 * Created by zhou on 2018/1/26.
 */

public interface TopCategoryListContract {

    interface View extends BaseContract.BaseView {
        void showCategoryList(CategoryList data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getCategoryList();
    }

}