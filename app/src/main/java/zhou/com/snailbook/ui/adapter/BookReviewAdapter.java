package zhou.com.snailbook.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;

import zhou.com.snailbook.R;
import zhou.com.snailbook.base.Constant;
import zhou.com.snailbook.bean.BookReviewList;
import zhou.com.snailbook.manager.SettingManager;
import zhou.com.snailbook.utils.FormatUtils;
import zhou.com.snailbook.view.recyclerview.adapter.BaseViewHolder;
import zhou.com.snailbook.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by zhou on 2018/1/27.
 */

public class BookReviewAdapter extends RecyclerArrayAdapter<BookReviewList.ReviewsBean> {

    public BookReviewAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<BookReviewList.ReviewsBean>(parent, R.layout.item_community_book_review_list) {
            @Override
            public void setData(BookReviewList.ReviewsBean item) {
                if (!SettingManager.getInstance().isNoneCover()) {
                    holder.setRoundImageUrl(R.id.ivBookCover, Constant.IMG_BASE_URL + item.book.cover,
                            R.drawable.cover_default);
                } else {
                    holder.setImageResource(R.id.ivBookCover, R.drawable.cover_default);
                }

                holder.setText(R.id.tvBookTitle, item.book.title)
                        .setText(R.id.tvBookType, String.format(mContext.getString(R.string.book_review_book_type), Constant.bookType.get(item.book.type)))
                        .setText(R.id.tvTitle, item.title)
                        .setText(R.id.tvHelpfulYes, String.format(mContext.getString(R.string.book_review_helpful_yes), item.helpful.yes));

                if (TextUtils.equals(item.state, "hot")) {
                    holder.setVisible(R.id.tvHot, true);
                    holder.setVisible(R.id.tvTime, false);
                    holder.setVisible(R.id.tvDistillate, false);
                } else if (TextUtils.equals(item.state, "distillate")) {
                    holder.setVisible(R.id.tvDistillate, true);
                    holder.setVisible(R.id.tvHot, false);
                    holder.setVisible(R.id.tvTime, false);
                } else {
                    holder.setVisible(R.id.tvTime, true);
                    holder.setVisible(R.id.tvHot, false);
                    holder.setVisible(R.id.tvDistillate, false);
                    holder.setText(R.id.tvTime, FormatUtils.getDescriptionTimeFromDateString(item.created));
                }
            }
        };
    }
}
