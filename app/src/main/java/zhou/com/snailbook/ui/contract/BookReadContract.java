package zhou.com.snailbook.ui.contract;

import java.util.List;

import zhou.com.snailbook.base.BaseContract;
import zhou.com.snailbook.bean.BookMixAToc;
import zhou.com.snailbook.bean.ChapterRead;

/**
 * Created by zhou on 2018/1/26.
 */

public interface BookReadContract {
    interface View extends BaseContract.BaseView{
        void showBookToc(List<BookMixAToc.mixToc.Chapters> list);

        void showChapterRead(ChapterRead.Chapter data, int chapter);

        void netError(int chapter);//添加网络处理异常接口
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookMixAToc(String bookId, String view);

        void getChapterRead(String url, int chapter);
    }
}
