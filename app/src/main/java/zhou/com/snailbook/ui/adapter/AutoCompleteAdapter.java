package zhou.com.snailbook.ui.adapter;

import android.content.Context;

import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;

import java.util.List;

import zhou.com.snailbook.R;

/**
 * Created by zhou on 2018/1/27.
 */

public class AutoCompleteAdapter extends EasyLVAdapter<String> {

    public AutoCompleteAdapter(Context context, List<String> list) {
        super(context, list, R.layout.item_auto_complete_list);
    }

    @Override
    public void convert(EasyLVHolder holder, int position, String s) {
        holder.setText(R.id.tvAutoCompleteItem, s);
    }
}
