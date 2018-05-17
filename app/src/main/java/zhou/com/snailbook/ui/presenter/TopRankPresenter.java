package zhou.com.snailbook.ui.presenter;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import zhou.com.snailbook.api.BookApi;
import zhou.com.snailbook.base.RxPresenter;
import zhou.com.snailbook.bean.RankingList;
import zhou.com.snailbook.ui.contract.TopRankContract;
import zhou.com.snailbook.utils.LogUtils;
import zhou.com.snailbook.utils.RxUtil;
import zhou.com.snailbook.utils.StringUtils;

/**
 * Created by zhou on 2018/1/26.
 */

public class TopRankPresenter extends RxPresenter<TopRankContract.View> implements TopRankContract.Presenter<TopRankContract.View>{

    private BookApi bookApi;

    @Inject
    public TopRankPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getRankList() {
        String key = StringUtils.creatAcacheKey("book-ranking-list");
        Observable<RankingList> fromNetWork = bookApi.getRanking()
                .compose(RxUtil.<RankingList>rxCacheBeanHelper(key));

        //依次检查disk、network
        Subscription rxSubscription = Observable.concat(RxUtil.rxCreateDiskObservable(key, RankingList.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RankingList>() {
                    @Override
                    public void onNext(RankingList data) {
                        if (data != null && mView != null) {
                            mView.showRankList(data);

                        }
                    }

                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getRankList:" + e.toString());
                        mView.complete();
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
