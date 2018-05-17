package zhou.com.snailbook.base;

/**
 * Created by zhou on 2018/1/25.
 * base基础类
 */

public interface BaseContract {
    interface BasePresenter<T>{
        void attachView(T view);

        void detachView();
    }

    interface BaseView{
        void showError();

        void complete();
    }
}
