package zhou.com.snailbook.ui.adapter;

import android.content.Context;
import android.view.View;

import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

import zhou.com.snailbook.R;
import zhou.com.snailbook.bean.support.FindBean;
import zhou.com.snailbook.common.OnRvItemClickListener;

/**
 * Created by zhou on 2018/1/26.
 */

public class FindAdapter extends EasyRVAdapter<FindBean> {

    private OnRvItemClickListener itemClickListener;

    public FindAdapter(Context context, List<FindBean> list, OnRvItemClickListener
            listener) {
        super(context, list, R.layout.item_find);
        this.itemClickListener = listener;
    }

    @Override
    protected void onBindData(final EasyRVHolder viewHolder, final int position, final FindBean item) {
        viewHolder.setText(R.id.tvTitle,item.getTitle());
        viewHolder.setImageResource(R.id.ivIcon,item.getIcon());
        viewHolder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(viewHolder.getItemView(),position,item);
            }
        });
    }
}
