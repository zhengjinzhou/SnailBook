package zhou.com.snailbook.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;

import zhou.com.snailbook.R;
import zhou.com.snailbook.base.Constant;
import zhou.com.snailbook.bean.SearchDetail;
import zhou.com.snailbook.view.recyclerview.adapter.BaseViewHolder;
import zhou.com.snailbook.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by zhou on 2018/1/27.
 * 搜索
 *
 */

public class SearchAdapter extends RecyclerArrayAdapter<SearchDetail.SearchBooks> {


    public SearchAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<SearchDetail.SearchBooks>(parent, R.layout.item_search_result_list) {
            @Override
            public void setData(SearchDetail.SearchBooks item) {
                holder.setRoundImageUrl(R.id.ivBookCover, Constant.IMG_BASE_URL + item.cover, R.drawable.cover_default)
                        .setText(R.id.tvBookListTitle, item.title)
                        .setText(R.id.tvLatelyFollower, String.format(mContext.getString(R.string.search_result_lately_follower), item.latelyFollower))
                        .setText(R.id.tvRetentionRatio, (TextUtils.isEmpty(item.retentionRatio) ? String.format(mContext.getString(R.string.search_result_retention_ratio), "0")
                                : String.format(mContext.getString(R.string.search_result_retention_ratio), item.retentionRatio)))
                        .setText(R.id.tvBookListAuthor, String.format(mContext.getString(R.string.search_result_author), item.author));
            }
        };
    }
}
