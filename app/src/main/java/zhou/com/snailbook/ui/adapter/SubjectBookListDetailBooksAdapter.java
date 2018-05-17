package zhou.com.snailbook.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import zhou.com.snailbook.R;
import zhou.com.snailbook.base.Constant;
import zhou.com.snailbook.bean.BookListDetail;
import zhou.com.snailbook.manager.SettingManager;
import zhou.com.snailbook.view.recyclerview.adapter.BaseViewHolder;
import zhou.com.snailbook.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by zhou on 2018/1/27.
 */

public class SubjectBookListDetailBooksAdapter  extends RecyclerArrayAdapter<BookListDetail.BookListBean.BooksBean> {

    public SubjectBookListDetailBooksAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<BookListDetail.BookListBean.BooksBean>(parent, R.layout.item_subject_book_list_detail) {
            @Override
            public void setData(BookListDetail.BookListBean.BooksBean item) {
                if (!SettingManager.getInstance().isNoneCover()) {
                    holder.setRoundImageUrl(R.id.ivBookCover, Constant.IMG_BASE_URL + item.getBook().getCover(),
                            R.drawable.cover_default);
                } else {
                    holder.setImageResource(R.id.ivBookCover, R.drawable.cover_default);
                }

                holder.setText(R.id.tvBookListTitle, item.getBook().getTitle())
                        .setText(R.id.tvBookAuthor, item.getBook().getAuthor())
                        .setText(R.id.tvBookLatelyFollower, String.format(mContext.getResources().getString(R.string.subject_book_list_detail_book_lately_follower),
                                item.getBook().getLatelyFollower()))
                        .setText(R.id.tvBookWordCount, String.format(mContext.getResources().getString(R.string.subject_book_list_detail_book_word_count),
                                item.getBook().getWordCount() / 10000))
                        .setText(R.id.tvBookDetail, item.getBook().getLongIntro());
            }
        };
    }
}
