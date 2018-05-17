package zhou.com.snailbook.view.pdfview;

import android.graphics.Bitmap;

/**
 * Created by zhou on 2018/1/28.
 */

public interface BitmapContainer {
    Bitmap get(int position);

    void remove(int position);

    void clear();
}