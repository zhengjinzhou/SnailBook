package zhou.com.snailbook.ui.presenter;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zhou.com.snailbook.api.BookApi;
import zhou.com.snailbook.base.RxPresenter;
import zhou.com.snailbook.bean.BookMixAToc;
import zhou.com.snailbook.bean.Recommend;
import zhou.com.snailbook.manager.SettingManager;
import zhou.com.snailbook.ui.contract.RecommendContract;
import zhou.com.snailbook.utils.ACache;
import zhou.com.snailbook.utils.LogUtils;
import zhou.com.snailbook.utils.RxUtil;
import zhou.com.snailbook.utils.StringUtils;

/**
 * Created by zhou on 2018/1/26.
 */

public class RecommendPresenter extends RxPresenter<RecommendContract.View>
        implements RecommendContract.Presenter<RecommendContract.View> {

    private Context mContext;
    private BookApi bookApi;

    @Inject
    public RecommendPresenter(Context context, BookApi bookApi){
        this.mContext = context;
        this.bookApi = bookApi;
    }

    @Override
    public void getRecommendList() {
        String key = StringUtils.creatAcacheKey("recommend-list", SettingManager.getInstance().getUserChooseSex());
        Observable<Recommend> fromNetWork = bookApi.getRecommend(SettingManager.getInstance().getUserChooseSex())
                .compose(RxUtil.<Recommend>rxCacheListHelper(key));
        //依次检查disk、network
        Subscription rxSubscription = Observable.concat(RxUtil.rxCreateDiskObservable(key, Recommend.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Recommend>() {
                    @Override
                    public void onNext(Recommend recommend) {
                        if (recommend != null) {
                            List<Recommend.RecommendBooks> list = recommend.books;
                            if (list != null && !list.isEmpty() && mView != null) {
                                mView.showRecommendList(list);
                            }
                        }
                    }

                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getRecommendList", e.toString());
                        mView.showError();
                    }
                });
        addSubscrebe(rxSubscription);
    }

    public void getTocList(final String bookId) {
        bookApi.getBookMixAToc(bookId, "chapters").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookMixAToc>() {
                    @Override
                    public void onNext(BookMixAToc data) {
                        ACache.get(mContext).put(bookId + "bookToc", data);
                        List<BookMixAToc.mixToc.Chapters> list = data.mixToc.chapters;
                        if (list != null && !list.isEmpty() && mView != null) {
                            mView.showBookToc(bookId, list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError: " + e);
                        mView.showError();
                    }
                });
    }
}
