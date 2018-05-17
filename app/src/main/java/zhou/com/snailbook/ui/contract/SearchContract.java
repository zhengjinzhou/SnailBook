package zhou.com.snailbook.ui.contract;

import java.util.List;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.SearchDetail;

/**
 * Created by zhou on 2018/1/27.
 */

public interface SearchContract {

    interface View extends BaseContract.BaseView {
        void showHotWordList(List<String> list);

        void showAutoCompleteList(List<String> list);

        void showSearchResultList(List<SearchDetail.SearchBooks> list);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getHotWordList();

        void getAutoCompleteList(String query);

        void getSearchResultList(String query);
    }

}
