package zhou.com.snailbook.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;

import zhou.com.snailbook.R;
import zhou.com.snailbook.base.Constant;
import zhou.com.snailbook.bean.BooksByCats;
import zhou.com.snailbook.manager.SettingManager;
import zhou.com.snailbook.view.recyclerview.adapter.BaseViewHolder;
import zhou.com.snailbook.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by zhou on 2018/1/26.
 */

public class SubCategoryAdapter extends RecyclerArrayAdapter<BooksByCats.BooksBean> {

    public SubCategoryAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<BooksByCats.BooksBean>(parent, R.layout.item_sub_category_list){

            @Override
            public void setData(BooksByCats.BooksBean item) {
                super.setData(item);
                if (!SettingManager.getInstance().isNoneCover()){
                    holder.setRoundImageUrl(R.id.ivSubCateCover, Constant.IMG_BASE_URL + item.cover,
                            R.drawable.cover_default);
                }else {
                    holder.setImageResource(R.id.ivSubCateCover, R.drawable.cover_default);
                }
                holder.setText(R.id.tvSubCateTitle, item.title)
                        .setText(R.id.tvSubCateAuthor, (item.author == null ? "未知" : item.author) + " | " + (item.majorCate == null ? "未知" : item.majorCate))
                        .setText(R.id.tvSubCateShort, item.shortIntro)
                        .setText(R.id.tvSubCateMsg, String.format(mContext.getResources().getString(R.string.category_book_msg),
                                item.latelyFollower,
                                TextUtils.isEmpty(item.retentionRatio) ? "0" : item.retentionRatio));
            }
        };
    }
}
