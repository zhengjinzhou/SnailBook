package zhou.com.snailbook.view.readview;

import android.content.Context;

import java.util.List;

import zhou.com.snailbook.bean.BookMixAToc;

/**
 * Created by zhou on 2018/1/27.
 */

public class NoAimWidget extends OverlappedWidget {

    public NoAimWidget(Context context, String bookId, List<BookMixAToc.mixToc.Chapters> chaptersList, OnReadStateChangeListener listener) {
        super(context, bookId, chaptersList, listener);
    }

    @Override
    protected void startAnimation() {
        startAnimation(700);
    }

    protected void startAnimation(int duration) {
        int dx;
        if (actiondownX > mScreenWidth / 2) {
            dx = (int) -(mScreenWidth + touch_down);
            mScroller.startScroll((int) (mScreenWidth + touch_down), (int) mTouch.y, dx, 0, duration);
        } else {
            dx = (int) (mScreenWidth - touch_down);
            mScroller.startScroll((int) touch_down, (int) mTouch.y, dx, 0, duration);
        }
    }

}