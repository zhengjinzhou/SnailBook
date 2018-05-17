package zhou.com.snailbook.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import zhou.com.snailbook.base.CategoryListLv2;
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

public interface BookApiService {

    /**
     * 获取分类
     *
     * @return
     */
    @GET("/cats/lv2/statistics")
    Observable<CategoryList> getCategoryList();

    @GET("/book/recommend")
    Observable<Recommend> getRecomend(@Query("gender") String gender);

    /**
     * 获取综合讨论区帖子详情
     *
     * @param disscussionId->_id
     * @return
     */
    @GET("/post/{disscussionId}")
    Observable<Disscussion> getBookDisscussionDetail(@Path("disscussionId") String disscussionId);

    /**
     * 获取书荒区帖子详情
     *
     * @param helpId->_id
     * @return
     */
    @GET("/post/help/{helpId}")
    Observable<BookHelp> getBookHelpDetail(@Path("helpId") String helpId);

    /**
     * 获取书荒区帖子列表
     * 全部、默认排序  http://api.zhuishushenqi.com/post/help?duration=all&sort=updated&start=0&limit=20&distillate=
     * 精品、默认排序  http://api.zhuishushenqi.com/post/help?duration=all&sort=updated&start=0&limit=20&distillate=true
     *
     * @param duration   all
     * @param sort       updated(默认排序)
     *                   created(最新发布)
     *                   comment-count(最多评论)
     * @param start      0
     * @param limit      20
     * @param distillate true(精品) 、空字符（全部）
     * @return
     */
    @GET("/post/help")
    Observable<BookHelpList> getBookHelpList(@Query("duration") String duration, @Query("sort") String sort, @Query("start") String start, @Query("limit") String limit, @Query("distillate") String distillate);


    /**
     * 书籍查询
     *
     * @param query
     * @return
     */
    @GET("/book/fuzzy-search")
    Observable<SearchDetail> searchBooks(@Query("query") String query);

    /**
     * 关键字自动补全
     *
     * @param query
     * @return
     */
    @GET("/book/auto-complete")
    Observable<AutoComplete> autoComplete(@Query("query") String query);

    @GET("/book/hot-word")
    Observable<HotWord> getHotWord();

    @GET("/mix-atoc/{bookId}")
    Observable<BookMixAToc> getBookMixAToc(@Path("bookId") String bookId, @Query("view") String view);

    /**
     * 获取综合讨论区帖子详情内的评论列表
     *
     * @param disscussionId->_id
     * @param start              0
     * @param limit              30
     * @return
     */
    @GET("/post/{disscussionId}/comment")
    Observable<CommentList> getBookDisscussionComments(@Path("disscussionId") String disscussionId, @Query("start") String start, @Query("limit") String limit);

    /**
     * 获取书评区帖子详情
     *
     * @param bookReviewId->_id
     * @return
     */
    @GET("/post/review/{bookReviewId}")
    Observable<BookReview> getBookReviewDetail(@Path("bookReviewId") String bookReviewId);

    /**
     * 获取神评论列表(综合讨论区、书评区、书荒区皆为同一接口)
     *
     * @param disscussionId->_id
     * @return
     */
    @GET("/post/{disscussionId}/comment/best")
    Observable<CommentList> getBestComments(@Path("disscussionId") String disscussionId);

    /**
     * 获取综合讨论区帖子列表
     * 全部、默认排序  http://api.zhuishushenqi.com/post/by-block?block=ramble&duration=all&sort=updated&type=all&start=0&limit=20&distillate=
     * 精品、默认排序  http://api.zhuishushenqi.com/post/by-block?block=ramble&duration=all&sort=updated&type=all&start=0&limit=20&distillate=true
     *
     * @param block      ramble:综合讨论区
     *                   original：原创区
     * @param duration   all
     * @param sort       updated(默认排序)
     *                   created(最新发布)
     *                   comment-count(最多评论)
     * @param type       all
     * @param start      0
     * @param limit      20
     * @param distillate true(精品)
     * @return
     */
    @GET("/post/by-block")
    Observable<DiscussionList> getBookDisscussionList(@Query("block") String block, @Query("duration") String duration, @Query("sort") String sort, @Query("type") String type, @Query("start") String start, @Query("limit") String limit, @Query("distillate") String distillate);

    /**
     * 获取书评区、书荒区帖子详情内的评论列表
     *
     * @param bookReviewId->_id
     * @param start             0
     * @param limit             30
     * @return
     */
    @GET("/post/review/{bookReviewId}/comment")
    Observable<CommentList> getBookReviewComments(@Path("bookReviewId") String bookReviewId, @Query("start") String start, @Query("limit") String limit);


    /**
     * 按分类获取书籍列表
     *
     * @param gender male、female
     * @param type   hot(热门)、new(新书)、reputation(好评)、over(完结)
     * @param major  玄幻
     * @param minor  东方玄幻、异界大陆、异界争霸、远古神话
     * @param limit  50
     * @return
     */
    @GET("/book/by-categories")
    Observable<BooksByCats> getBooksByCats(@Query("gender") String gender, @Query("type") String type, @Query("major") String major, @Query("minor") String minor, @Query("start") int start, @Query("limit") int limit);

    /**
     * 获取书评区帖子列表
     * 全部、全部类型、默认排序  http://api.zhuishushenqi.com/post/review?duration=all&sort=updated&type=all&start=0&limit=20&distillate=
     * 精品、玄幻奇幻、默认排序  http://api.zhuishushenqi.com/post/review?duration=all&sort=updated&type=xhqh&start=0&limit=20&distillate=true
     *
     * @param duration   all
     * @param sort       updated(默认排序)
     *                   created(最新发布)
     *                   helpful(最有用的)
     *                   comment-count(最多评论)
     * @param type       all(全部类型)、xhqh(玄幻奇幻)、dsyn(都市异能)...
     * @param start      0
     * @param limit      20
     * @param distillate true(精品) 、空字符（全部）
     * @return
     */
    @GET("/post/review")
    Observable<BookReviewList> getBookReviewList(@Query("duration") String duration, @Query("sort") String sort, @Query("type") String type, @Query("start") String start, @Query("limit") String limit, @Query("distillate") String distillate);


    @GET("http://chapter2.zhuishushenqi.com/chapter/{url}")
    Observable<ChapterRead> getChapterRead(@Path("url") String url);

    /**
     * 获取书单详情
     *
     * @return
     */
    @GET("/book-list/{bookListId}")
    Observable<BookListDetail> getBookListDetail(@Path("bookListId") String bookListId);


    /**
     * 获取二级分类
     *
     * @return
     */
    @GET("/cats/lv2")
    Observable<CategoryListLv2> getCategoryListLv2();

    /**
     * 获取所有排行榜
     *
     * @return
     */
    @GET("/ranking/gender")
    Observable<RankingList> getRanking();

    /**
     * 获取单一排行榜
     * 周榜：rankingId->_id
     * 月榜：rankingId->monthRank
     * 总榜：rankingId->totalRank
     *
     * @return
     */
    @GET("/ranking/{rankingId}")
    Observable<Rankings> getRanking(@Path("rankingId") String rankingId);

    @GET("/book/{bookId}")
    Observable<BookDetail> getBookDetail(@Path("bookId") String bookId);

    /**
     * 热门评论
     *
     * @param book
     * @return
     */
    @GET("/post/review/best-by-book")
    Observable<HotReview> getHotReview(@Query("book") String book);

    @GET("/book-list/{bookId}/recommend")
    Observable<RecommendBookList> getRecommendBookList(@Path("bookId") String bookId, @Query("limit") String limit);

    /**
     * 获取书籍详情讨论列表
     *
     * @param book  bookId
     * @param sort  updated(默认排序)
     *              created(最新发布)
     *              comment-count(最多评论)
     * @param type  normal
     *              vote
     * @param start 0
     * @param limit 20
     * @return
     */
    @GET("/post/by-book")
    Observable<DiscussionList> getBookDetailDisscussionList(@Query("book") String book, @Query("sort") String sort, @Query("type") String type, @Query("start") String start, @Query("limit") String limit);

    /**
     * 获取书籍详情书评列表
     *
     * @param book  bookId
     * @param sort  updated(默认排序)
     *              created(最新发布)
     *              helpful(最有用的)
     *              comment-count(最多评论)
     * @param start 0
     * @param limit 20
     * @return
     */
    @GET("/post/review/by-book")
    Observable<HotReview> getBookDetailReviewList(@Query("book") String book, @Query("sort") String sort, @Query("start") String start, @Query("limit") String limit);

    /**
     * 获取主题书单标签列表
     *
     * @return
     */
    @GET("/book-list/tagType")
    Observable<BookListTags> getBookListTags();

    /**
     * 获取主题书单列表
     * 本周最热：duration=last-seven-days&sort=collectorCount
     * 最新发布：duration=all&sort=created
     * 最多收藏：duration=all&sort=collectorCount
     *
     * @param tag    都市、古代、架空、重生、玄幻、网游
     * @param gender male、female
     * @param limit  20
     * @return
     */
    @GET("/book-list")
    Observable<BookLists> getBookLists(@Query("duration") String duration, @Query("sort") String sort, @Query("start") String start, @Query("limit") String limit, @Query("tag") String tag, @Query("gender") String gender);

}
