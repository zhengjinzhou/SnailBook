package zhou.com.snailbook.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
import rx.Observable;
import zhou.com.snailbook.base.CategoryListLv2;
import zhou.com.snailbook.base.Constant;
import zhou.com.snailbook.bean.AutoComplete;
import zhou.com.snailbook.bean.BookDetail;
import zhou.com.snailbook.bean.BookHelp;
import zhou.com.snailbook.bean.BookHelpList;
import zhou.com.snailbook.bean.BookListDetail;
import zhou.com.snailbook.bean.BookListTags;
import zhou.com.snailbook.bean.BookLists;
import zhou.com.snailbook.bean.BookMixAToc;
import zhou.com.snailbook.bean.BookReview;
import zhou.com.snailbook.bean.BookReviewList;
import zhou.com.snailbook.bean.BooksByCats;
import zhou.com.snailbook.bean.CategoryList;
import zhou.com.snailbook.bean.ChapterRead;
import zhou.com.snailbook.bean.CommentList;
import zhou.com.snailbook.bean.DiscussionList;
import zhou.com.snailbook.bean.Disscussion;
import zhou.com.snailbook.bean.HotReview;
import zhou.com.snailbook.bean.HotWord;
import zhou.com.snailbook.bean.RankingList;
import zhou.com.snailbook.bean.Rankings;
import zhou.com.snailbook.bean.Recommend;
import zhou.com.snailbook.bean.RecommendBookList;
import zhou.com.snailbook.bean.SearchDetail;

/**
 * Created by zhou on 2018/1/25.
 */

public class BookApi {

    public static BookApi instance;
    private BookApiService service;

    public BookApi(OkHttpClient okHttpClient){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(BookApiService.class);
    }

    public static BookApi getInstance(OkHttpClient okHttpClient){
        if (instance == null){
            instance = new BookApi(okHttpClient);
        }
        return instance;
    }

    public Observable<BookHelpList> getBookHelpList(String duration, String sort, String start, String limit, String distillate) {
        return service.getBookHelpList(duration, sort, start, limit, distillate);
    }

    public Observable<BookHelp> getBookHelpDetail(String helpId) {
        return service.getBookHelpDetail(helpId);
    }

    public Observable<DiscussionList> getGirlBookDisscussionList(String block, String duration, String sort, String type, String start, String limit, String distillate) {
        return service.getBookDisscussionList(block, duration, sort, type, start, limit, distillate);
    }

    public Observable<Recommend> getRecommend(String gender) {
        return service.getRecomend(gender);
    }

    public Observable<Disscussion> getBookDisscussionDetail(String disscussionId) {
        return service.getBookDisscussionDetail(disscussionId);
    }

    public Observable<HotWord> getHotWord() {
        return service.getHotWord();
    }

    public Observable<CommentList> getBookDisscussionComments(String disscussionId, String start, String limit) {
        return service.getBookDisscussionComments(disscussionId, start, limit);
    }

    public Observable<BookReview> getBookReviewDetail(String bookReviewId) {
        return service.getBookReviewDetail(bookReviewId);
    }

    public Observable<AutoComplete> getAutoComplete(String query) {
        return service.autoComplete(query);
    }

    public Observable<BookReviewList> getBookReviewList(String duration, String sort, String type, String start, String limit, String distillate) {
        return service.getBookReviewList(duration, sort, type, start, limit, distillate);
    }

    public Observable<SearchDetail> getSearchResult(String query) {
        return service.searchBooks(query);
    }

    public Observable<DiscussionList> getBookDisscussionList(String block, String duration, String sort, String type, String start, String limit, String distillate) {
        return service.getBookDisscussionList(block, duration, sort, type, start, limit, distillate);
    }

    public Observable<BookListDetail> getBookListDetail(String bookListId) {
        return service.getBookListDetail(bookListId);
    }

    public Observable<CommentList> getBookReviewComments(String bookReviewId, String start, String limit) {
        return service.getBookReviewComments(bookReviewId, start, limit);
    }

    public Observable<CommentList> getBestComments(String disscussionId) {
        return service.getBestComments(disscussionId);
    }

    public synchronized Observable<ChapterRead> getChapterRead(String url) {
        return service.getChapterRead(url);
    }

    public Observable<BooksByCats> getBooksByCats(String gender, String type, String major, String minor, int start, @Query("limit") int limit) {
        return service.getBooksByCats(gender, type, major, minor, start, limit);
    }

    public Observable<BookMixAToc> getBookMixAToc(String bookId, String view) {
        return service.getBookMixAToc(bookId, view);
    }

    public Observable<RankingList> getRanking() {
        return service.getRanking();
    }

    public Observable<Rankings> getRanking(String rankingId) {
        return service.getRanking(rankingId);
    }

    public Observable<BookDetail> getBookDetail(String bookId) {
        return service.getBookDetail(bookId);
    }

    public Observable<HotReview> getHotReview(String book) {
        return service.getHotReview(book);
    }

    public Observable<RecommendBookList> getRecommendBookList(String bookId, String limit) {
        return service.getRecommendBookList(bookId, limit);
    }

    public Observable<DiscussionList> getBookDetailDisscussionList(String book, String sort, String type, String start, String limit) {
        return service.getBookDetailDisscussionList(book, sort, type, start, limit);
    }

    public Observable<HotReview> getBookDetailReviewList(String book, String sort, String start, String limit) {
        return service.getBookDetailReviewList(book, sort, start, limit);
    }

    public Observable<BookListTags> getBookListTags() {
        return service.getBookListTags();
    }

    public Observable<BookLists> getBookLists(String duration, String sort, String start, String limit, String tag, String gender) {
        return service.getBookLists(duration, sort, start, limit, tag, gender);
    }

    public synchronized Observable<CategoryList> getCategoryList() {
        return service.getCategoryList();
    }

    public Observable<CategoryListLv2> getCategoryListLv2() {
        return service.getCategoryListLv2();
    }


}
