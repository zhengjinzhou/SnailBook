package zhou.com.snailbook.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import zhou.com.snailbook.R;
import zhou.com.snailbook.base.Constant;
import zhou.com.snailbook.bean.CommentList;
import zhou.com.snailbook.manager.SettingManager;
import zhou.com.snailbook.utils.FormatUtils;
import zhou.com.snailbook.view.recyclerview.adapter.BaseViewHolder;
import zhou.com.snailbook.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by zhou on 2018/1/27.
 *
 * 帖子 评论、回复
 */

public class CommentListAdapter extends RecyclerArrayAdapter<CommentList.CommentsBean> {

    public CommentListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<CommentList.CommentsBean>(parent, R.layout.item_comment_list) {
            @Override
            public void setData(CommentList.CommentsBean item) {
                if (!SettingManager.getInstance().isNoneCover()) {
                    holder.setCircleImageUrl(R.id.ivBookCover, Constant.IMG_BASE_URL + item.author.avatar,
                            R.drawable.avatar_default);
                } else {
                    holder.setImageResource(R.id.ivBookCover, R.drawable.avatar_default);
                }

                holder.setText(R.id.tvBookTitle, item.author.nickname)
                        .setText(R.id.tvContent, item.content)
                        .setText(R.id.tvBookType, String.format(mContext.getString(R.string.book_detail_user_lv), item.author.lv))
                        .setText(R.id.tvFloor, String.format(mContext.getString(R.string.comment_floor), item.floor))
                        .setText(R.id.tvTime, FormatUtils.getDescriptionTimeFromDateString(item.created));

                if (item.replyTo == null) {
                    holder.setVisible(R.id.tvReplyNickName, false);
                    holder.setVisible(R.id.tvReplyFloor, false);
                } else {
                    holder.setText(R.id.tvReplyNickName, String.format(mContext.getString(R.string.comment_reply_nickname), item.replyTo.author.nickname))
                            .setText(R.id.tvReplyFloor, String.format(mContext.getString(R.string.comment_reply_floor), item.replyTo.floor));
                    holder.setVisible(R.id.tvReplyNickName, true);
                    holder.setVisible(R.id.tvReplyFloor, true);
                }
            }
        };
    }
}
