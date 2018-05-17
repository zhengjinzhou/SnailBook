package zhou.com.snailbook.ui.contract;

import zhou.com.snailbook.base.BaseContract;

/**
 * Created by zhou on 2018/1/25.
 */

public interface MainContract {

    interface View extends BaseContract.BaseView{
        //void loginSuccess();//登录成功，这个方法不去实现

        void syncBookShelfCompleted();//同步书架
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        //void login(String uid, String token, String platform);

        void syncBookShelf();
    }
}
