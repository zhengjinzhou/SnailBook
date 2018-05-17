package zhou.com.snailbook.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import zhou.com.snailbook.R;
import zhou.com.snailbook.base.Constant;
import zhou.com.snailbook.bean.HotReview;
import zhou.com.snailbook.manager.SettingManager;
import zhou.com.snailbook.utils.FormatUtils;
import zhou.com.snailbook.view.XLHRatingBar;
import zhou.com.snailbook.view.recyclerview.adapter.BaseViewHolder;
import zhou.com.snailbook.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by zhou on 2018/1/26.
 */

public class BookDetailReviewAdapter extends RecyclerArrayAdapter<HotReview.Reviews> {


    public BookDetailReviewAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<HotReview.Reviews>(parent, R.layout.item_book_detai_hot_review_list) {
            @Override
            public void setData(HotReview.Reviews item) {
                if (!SettingManager.getInstance().isNoneCover()) {
                    holder.setCircleImageUrl(R.id.ivBookCover, Constant.IMG_BASE_URL + item.author.avatar,
                            R.drawable.avatar_default);
                } else {
                    holder.setImageResource(R.id.ivBookCover, R.drawable.avatar_default);
                }

                holder.setText(R.id.tvBookTitle, item.author.nickname)
                        .setText(R.id.tvBookType, String.format(mContext.getString(R.string
                                .book_detail_user_lv), item.author.lv))
                        .setText(R.id.tvTime, FormatUtils.getDescriptionTimeFromDateString(item.created))
                        .setText(R.id.tvTitle, item.title)
                        .setText(R.id.tvContent, String.valueOf(item.content))
                        .setText(R.id.tvHelpfulYes, String.valueOf(item.helpful.yes));
                holder.setVisible(R.id.tvTime, true);
                XLHRatingBar ratingBar = holder.getView(R.id.rating);
                ratingBar.setCountSelected(item.rating);
            }
        };
    }
}
