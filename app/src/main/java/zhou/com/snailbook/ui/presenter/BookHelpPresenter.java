package zhou.com.snailbook.ui.presenter;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import zhou.com.snailbook.api.BookApi;
import zhou.com.snailbook.base.RxPresenter;
import zhou.com.snailbook.bean.BookHelpList;
import zhou.com.snailbook.ui.contract.BookHelpContract;
import zhou.com.snailbook.utils.LogUtils;
import zhou.com.snailbook.utils.RxUtil;
import zhou.com.snailbook.utils.StringUtils;

/**
 * Created by zhou on 2018/1/27.
 */

public class BookHelpPresenter extends RxPresenter<BookHelpContract.View> implements BookHelpContract.Presenter {

    private BookApi bookApi;

    @Inject
    public BookHelpPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getBookHelpList(String sort, String distillate, final int start, int limit) {
        String key = StringUtils.creatAcacheKey("book-help-list", "all", sort, start + "", limit + "", distillate);
        Observable<BookHelpList> fromNetWork = bookApi.getBookHelpList("all", sort, start + "", limit + "", distillate)
                .compose(RxUtil.<BookHelpList>rxCacheListHelper(key));

        //依次检查disk、network
        Subscription rxSubscription = Observable.concat(RxUtil.rxCreateDiskObservable(key, BookHelpList.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookHelpList>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookHelpList:" + e.toString());
                        mView.showError();
                    }

                    @Override
                    public void onNext(BookHelpList list) {
                        boolean isRefresh = start == 0 ? true : false;
                        mView.showBookHelpList(list.helps, isRefresh);
                    }
                });
        addSubscrebe(rxSubscription);
    }

}
