package zhou.com.snailbook.ui.presenter;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import zhou.com.snailbook.api.BookApi;
import zhou.com.snailbook.base.RxPresenter;
import zhou.com.snailbook.bean.HotReview;
import zhou.com.snailbook.ui.contract.BookDetailReviewContract;
import zhou.com.snailbook.utils.LogUtils;
import zhou.com.snailbook.utils.RxUtil;
import zhou.com.snailbook.utils.StringUtils;

/**
 * Created by zhou on 2018/1/26.
 */

public class BookDetailReviewPresenter extends RxPresenter<BookDetailReviewContract.View> implements BookDetailReviewContract.Presenter<BookDetailReviewContract.View> {

    private BookApi bookApi;

    @Inject
    public BookDetailReviewPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    public void getBookDetailReviewList(String bookId, String sort, final int start, int limit) {
        String key = StringUtils.creatAcacheKey("book-detail-review-list", bookId, sort, start, limit);
        Observable<HotReview> fromNetWork = bookApi.getBookDetailReviewList(bookId, sort, start + "", limit + "")
                .compose(RxUtil.<HotReview>rxCacheListHelper(key));

        //依次检查disk、network
        Subscription rxSubscription = Observable.concat(RxUtil.rxCreateDiskObservable(key, HotReview.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotReview>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookDetailReviewList:" + e.toString());
                        mView.showError();
                    }

                    @Override
                    public void onNext(HotReview list) {
                        boolean isRefresh = start == 0 ? true : false;
                        mView.showBookDetailReviewList(list.reviews, isRefresh);
                    }
                });
        addSubscrebe(rxSubscription);
    }

}
