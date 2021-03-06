package zhou.com.snailbook.view.readview;

/**
 * Created by zhou on 2018/1/27.
 */

public interface OnReadStateChangeListener {

    void onChapterChanged(int chapter);

    void onPageChanged(int chapter, int page);

    void onLoadChapterFailure(int chapter);

    void onCenterClick();

    void onFlip();
}
