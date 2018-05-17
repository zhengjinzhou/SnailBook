package zhou.com.snailbook.view.epubview;

/**
 * Created by zhou on 2018/1/28.
 */

public interface ReaderCallback {

    String getPageHref(int position);

    void toggleToolBarVisible();

    void hideToolBarIfVisible();


}
