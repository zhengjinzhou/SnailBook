package zhou.com.snailbook.ui.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zhou.com.snailbook.api.BookApi;
import zhou.com.snailbook.base.RxPresenter;
import zhou.com.snailbook.bean.BooksByCats;
import zhou.com.snailbook.bean.Rankings;
import zhou.com.snailbook.ui.contract.SubRankContract;
import zhou.com.snailbook.utils.LogUtils;

/**
 * Created by zhou on 2018/1/26.
 */

public class SubRankPresenter extends RxPresenter<SubRankContract.View> implements SubRankContract.Presenter<SubRankContract.View>{

    private BookApi bookApi;

    @Inject
    public SubRankPresenter(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getRankList(String id) {
        Subscription rxSubscription = bookApi.getRanking(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Rankings>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getRankList:" + e.toString());
                        mView.showError();
                    }

                    @Override
                    public void onNext(Rankings ranking) {
                        List<Rankings.RankingBean.BooksBean> books = ranking.ranking.books;

                        BooksByCats cats = new BooksByCats();
                        cats.books = new ArrayList<>();
                        for (Rankings.RankingBean.BooksBean bean : books) {
                            cats.books.add(new BooksByCats.BooksBean(bean._id, bean.cover, bean.title, bean.author, bean.cat, bean.shortIntro, bean.latelyFollower, bean.retentionRatio));
                        }
                        mView.showRankList(cats);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
