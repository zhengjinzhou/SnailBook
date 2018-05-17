package zhou.com.snailbook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuyh.easyadapter.glide.GlideCircleTransform;
import com.yuyh.easyadapter.glide.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhou.com.snailbook.R;
import zhou.com.snailbook.base.BaseRVActivity;
import zhou.com.snailbook.base.Constant;
import zhou.com.snailbook.bean.BookReview;
import zhou.com.snailbook.bean.CommentList;
import zhou.com.snailbook.common.OnRvItemClickListener;
import zhou.com.snailbook.component.AppComponent;
import zhou.com.snailbook.component.DaggerCommunityComponent;
import zhou.com.snailbook.ui.adapter.BestCommentListAdapter;
import zhou.com.snailbook.ui.adapter.CommentListAdapter;
import zhou.com.snailbook.ui.contract.BookReviewDetailContract;
import zhou.com.snailbook.ui.presenter.BookReviewDetailPresenter;
import zhou.com.snailbook.utils.FormatUtils;
import zhou.com.snailbook.view.BookContentTextView;
import zhou.com.snailbook.view.SupportDividerItemDecoration;
import zhou.com.snailbook.view.XLHRatingBar;
import zhou.com.snailbook.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * 书评区详情
 *
 *
 */

public class BookReviewDetailActivity extends BaseRVActivity<CommentList.CommentsBean> implements BookReviewDetailContract.View, OnRvItemClickListener<CommentList.CommentsBean> {

    private static final String INTENT_ID = "id";

    public static void startActivity(Context context, String id) {
        context.startActivity(new Intent(context, BookReviewDetailActivity.class)
                .putExtra(INTENT_ID, id));
    }

    private String id;
    private List<CommentList.CommentsBean> mBestCommentList = new ArrayList<>();
    private BestCommentListAdapter mBestCommentListAdapter;
    private HeaderViewHolder headerViewHolder;

    @Inject
    BookReviewDetailPresenter mPresenter;

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    static class HeaderViewHolder {
        @BindView(R.id.ivAuthorAvatar)
        ImageView ivAuthorAvatar;
        @BindView(R.id.tvBookAuthor)
        TextView tvBookAuthor;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvContent)
        BookContentTextView tvContent;
        @BindView(R.id.rlBookInfo)
        RelativeLayout rlBookInfo;
        @BindView(R.id.ivBookCover)
        ImageView ivBookCover;
        @BindView(R.id.tvBookTitle)
        TextView tvBookTitle;
        @BindView(R.id.tvHelpfullYesCount)
        TextView tvHelpfullYesCount;
        @BindView(R.id.tvHelpfullNoCount)
        TextView tvHelpfullNoCount;
        @BindView(R.id.tvBestComments)
        TextView tvBestComments;
        @BindView(R.id.rvBestComments)
        RecyclerView rvBestComments;
        @BindView(R.id.tvCommentCount)
        TextView tvCommentCount;
        @BindView(R.id.rating)
        XLHRatingBar ratingBar;

        public HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);   //view绑定
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_review_detail;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCommunityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("书评详情");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        id = getIntent().getStringExtra(INTENT_ID);

        mPresenter.attachView(this);
        mPresenter.getBookReviewDetail(id);
        mPresenter.getBestComments(id);
        mPresenter.getBookReviewComments(id,start, limit);
    }

    @Override
    public void configViews() {
        initAdapter(CommentListAdapter.class, false, true);

        mAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View headerView =  LayoutInflater.from(BookReviewDetailActivity.this).inflate(R.layout.header_view_book_review_detail, parent, false);
                return headerView;
            }

            @Override
            public void onBindView(View headerView) {
                headerViewHolder = new HeaderViewHolder(headerView);
            }
        });

    }

    @Override
    public void showBookReviewDetail(final BookReview data) {
        Glide.with(mContext)
                .load(Constant.IMG_BASE_URL + data.review.author.avatar)
                .placeholder(R.drawable.avatar_default)
                .transform(new GlideCircleTransform(mContext))
                .into(headerViewHolder.ivAuthorAvatar);

        headerViewHolder.tvBookAuthor.setText(data.review.author.nickname);
        headerViewHolder.tvTime.setText(FormatUtils.getDescriptionTimeFromDateString(data.review.created));
        headerViewHolder.tvTitle.setText(data.review.title);
        headerViewHolder.tvContent.setText(data.review.content);

        Glide.with(mContext)
                .load(Constant.IMG_BASE_URL + data.review.book.cover)
                .placeholder(R.drawable.cover_default)
                .transform(new GlideRoundTransform(mContext))
                .into(headerViewHolder.ivBookCover);
        headerViewHolder.tvBookTitle.setText(data.review.book.title);

        headerViewHolder.tvHelpfullYesCount.setText(String.valueOf(data.review.helpful.yes));
        headerViewHolder.tvHelpfullNoCount.setText(String.valueOf(data.review.helpful.no));

        headerViewHolder.tvCommentCount.setText(String.format(mContext.getString(R.string.comment_comment_count), data.review.commentCount));

        headerViewHolder.rlBookInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailActivity.startActivity(BookReviewDetailActivity.this, data.review.book._id);
            }
        });

        headerViewHolder.ratingBar.setCountSelected(data.review.rating);
    }

    @Override
    public void showBestComments(CommentList list) {
        if(list.comments.isEmpty()){
            gone(headerViewHolder.tvBestComments, headerViewHolder.rvBestComments);
        }else{
            mBestCommentList.addAll(list.comments);
            headerViewHolder.rvBestComments.setHasFixedSize(true);
            headerViewHolder.rvBestComments.setLayoutManager(new LinearLayoutManager(this));
            headerViewHolder.rvBestComments.addItemDecoration(new SupportDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL, true));
            mBestCommentListAdapter = new BestCommentListAdapter(mContext, mBestCommentList);
            mBestCommentListAdapter.setOnItemClickListener(this);
            headerViewHolder.rvBestComments.setAdapter(mBestCommentListAdapter);
            visible(headerViewHolder.tvBestComments, headerViewHolder.rvBestComments);
        }
    }

    @Override
    public void showBookReviewComments(CommentList list) {
        mAdapter.addAll(list.comments);
        start=start+list.comments.size();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.getBookReviewComments(id,start, limit);
    }

    @Override
    public void onItemClick(View view, int position, CommentList.CommentsBean data) {

    }

    @Override
    public void onItemClick(int position) {
        CommentList.CommentsBean data  = mAdapter.getItem(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
