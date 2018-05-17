package zhou.com.snailbook.ui.presenter;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zhou.com.snailbook.api.BookApi;
import zhou.com.snailbook.base.RxPresenter;
import zhou.com.snailbook.bean.BookListDetail;
import zhou.com.snailbook.ui.contract.SubjectBookListDetailContract;
import zhou.com.snailbook.utils.LogUtils;

/**
 * Created by zhou on 2018/1/27.
 */

public class SubjectBookListDetailPresenter extends RxPresenter<SubjectBookListDetailContract.View> implements SubjectBookListDetailContract.Presenter<SubjectBookListDetailContract.View> {

    private BookApi bookApi;

    @Inject
    public SubjectBookListDetailPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getBookListDetail(String bookListId) {
        Subscription rxSubscription = bookApi.getBookListDetail(bookListId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookListDetail>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookListDetail:" + e.toString());
                        mView.complete();
                    }

                    @Override
                    public void onNext(BookListDetail data) {
                        mView.showBookListDetail(data);
                    }
                });
        addSubscrebe(rxSubscription);
    }

}
