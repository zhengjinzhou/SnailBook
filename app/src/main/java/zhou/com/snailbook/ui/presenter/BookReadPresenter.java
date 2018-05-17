package zhou.com.snailbook.ui.presenter;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import zhou.com.snailbook.api.BookApi;
import zhou.com.snailbook.base.RxPresenter;
import zhou.com.snailbook.bean.BookMixAToc;
import zhou.com.snailbook.bean.ChapterRead;
import zhou.com.snailbook.ui.contract.BookReadContract;
import zhou.com.snailbook.utils.LogUtils;
import zhou.com.snailbook.utils.RxUtil;
import zhou.com.snailbook.utils.StringUtils;

/**
 * Created by zhou on 2018/1/27.
 */

public class BookReadPresenter extends RxPresenter<BookReadContract.View>
        implements BookReadContract.Presenter<BookReadContract.View> {

    private Context mContext;
    private BookApi bookApi;

    @Inject
    public BookReadPresenter(Context mContext, BookApi bookApi) {
        this.mContext = mContext;
        this.bookApi = bookApi;
    }

    @Override
    public void getBookMixAToc(final String bookId, String viewChapters) {
        String key = StringUtils.creatAcacheKey("book-toc", bookId, viewChapters);
        Observable<BookMixAToc.mixToc> fromNetWork = bookApi.getBookMixAToc(bookId, viewChapters)
                .map(new Func1<BookMixAToc, BookMixAToc.mixToc>() {
                    @Override
                    public BookMixAToc.mixToc call(BookMixAToc data) {
                        return data.mixToc;
                    }
                })
                .compose(RxUtil.<BookMixAToc.mixToc>rxCacheListHelper(key));

        //依次检查disk、network
        Subscription rxSubscription = Observable
                .concat(RxUtil.rxCreateDiskObservable(key, BookMixAToc.mixToc.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookMixAToc.mixToc>() {
                    @Override
                    public void onNext(BookMixAToc.mixToc data) {
                        List<BookMixAToc.mixToc.Chapters> list = data.chapters;
                        if (list != null && !list.isEmpty() && mView != null) {
                            mView.showBookToc(list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError: " + e);
                        mView.netError(0);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getChapterRead(String url, final int chapter) {
        Subscription rxSubscription = bookApi.getChapterRead(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ChapterRead>() {
                    @Override
                    public void onNext(ChapterRead data) {
                        if (data.chapter != null && mView != null) {
                            mView.showChapterRead(data.chapter, chapter);
                        } else {
                            mView.netError(chapter);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError: " + e);
                        mView.netError(chapter);
                    }
                });
        addSubscrebe(rxSubscription);
    }

}
