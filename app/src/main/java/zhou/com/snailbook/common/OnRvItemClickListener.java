package zhou.com.snailbook.common;

import android.view.View;

/**
 * Created by zhou on 2018/1/26.
 */

public interface OnRvItemClickListener<T> {
    void onItemClick(View view,int position,T data);
}
