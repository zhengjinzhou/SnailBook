package zhou.com.snailbook.ui.contract;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.base.CategoryListLv2;

/**
 * Created by zhou on 2018/1/27.
 */

public interface SubCategoryActivityContract {
    interface View extends BaseContract.BaseView {
        void showCategoryList(CategoryListLv2 data);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getCategoryListLv2();
    }

}
