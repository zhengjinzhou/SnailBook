package zhou.com.snailbook.ui.presenter;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zhou.com.snailbook.api.BookApi;
import zhou.com.snailbook.base.RxPresenter;
import zhou.com.snailbook.bean.BookHelp;
import zhou.com.snailbook.bean.CommentList;
import zhou.com.snailbook.ui.contract.BookHelpDetailContract;
import zhou.com.snailbook.utils.LogUtils;

/**
 * Created by zhou on 2018/1/28.
 */

public class BookHelpDetailPresenter extends RxPresenter<BookHelpDetailContract.View> implements BookHelpDetailContract.Presenter {

    private BookApi bookApi;

    @Inject
    public BookHelpDetailPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getBookHelpDetail(String id) {
        Subscription rxSubscription = bookApi.getBookHelpDetail(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookHelp>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookHelpDetail:" + e.toString());
                    }

                    @Override
                    public void onNext(BookHelp bookHelp) {
                        mView.showBookHelpDetail(bookHelp);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getBestComments(String disscussionId) {
        Subscription rxSubscription = bookApi.getBestComments(disscussionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBestComments:" + e.toString());
                    }

                    @Override
                    public void onNext(CommentList list) {
                        mView.showBestComments(list);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getBookHelpComments(String disscussionId, int start, int limit) {
        Subscription rxSubscription = bookApi.getBookReviewComments(disscussionId, start + "", limit + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookHelpComments:" + e.toString());
                    }

                    @Override
                    public void onNext(CommentList list) {
                        mView.showBookHelpComments(list);
                    }
                });
        addSubscrebe(rxSubscription);
    }

}
