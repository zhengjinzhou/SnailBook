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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuyh.easyadapter.glide.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhou.com.snailbook.R;
import zhou.com.snailbook.base.BaseRVActivity;
import zhou.com.snailbook.base.Constant;
import zhou.com.snailbook.bean.BookHelp;
import zhou.com.snailbook.bean.CommentList;
import zhou.com.snailbook.common.OnRvItemClickListener;
import zhou.com.snailbook.component.AppComponent;
import zhou.com.snailbook.component.DaggerCommunityComponent;
import zhou.com.snailbook.ui.adapter.BestCommentListAdapter;
import zhou.com.snailbook.ui.adapter.CommentListAdapter;
import zhou.com.snailbook.ui.contract.BookHelpDetailContract;
import zhou.com.snailbook.ui.presenter.BookHelpDetailPresenter;
import zhou.com.snailbook.utils.FormatUtils;
import zhou.com.snailbook.view.BookContentTextView;
import zhou.com.snailbook.view.SupportDividerItemDecoration;
import zhou.com.snailbook.view.recyclerview.adapter.RecyclerArrayAdapter;

public class BookHelpDetailActivity extends BaseRVActivity<CommentList.CommentsBean> implements BookHelpDetailContract.View, OnRvItemClickListener<CommentList.CommentsBean> {

    private static final String INTENT_ID = "id";

    public static void startActivity(Context context, String id) {
        context.startActivity(new Intent(context, BookHelpDetailActivity.class)
                .putExtra(INTENT_ID, id));
    }

    private String id;
    private List<CommentList.CommentsBean> mBestCommentList = new ArrayList<>();
    private BestCommentListAdapter mBestCommentListAdapter;
    private HeaderViewHolder headerViewHolder;

    @Inject
    BookHelpDetailPresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_help_detail;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCommunityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    static class HeaderViewHolder {
        @BindView(R.id.ivBookCover)
        ImageView ivAvatar;
        @BindView(R.id.tvBookTitle)
        TextView tvNickName;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvContent)
        BookContentTextView tvContent;
        @BindView(R.id.tvBestComments)
        TextView tvBestComments;
        @BindView(R.id.rvBestComments)
        RecyclerView rvBestComments;
        @BindView(R.id.tvCommentCount)
        TextView tvCommentCount;

        public HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);   //view绑定
        }
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("书荒互助区详情");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        id = getIntent().getStringExtra(INTENT_ID);

        mPresenter.attachView(this);
        mPresenter.getBookHelpDetail(id);
        mPresenter.getBestComments(id);
        mPresenter.getBookHelpComments(id, start, limit);
    }

    @Override
    public void configViews() {
        initAdapter(CommentListAdapter.class, false, true);

        mAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View headerView = LayoutInflater.from(BookHelpDetailActivity.this).inflate(R.layout.header_view_book_discussion_detail, parent, false);
                return headerView;
            }

            @Override
            public void onBindView(View headerView) {
                headerViewHolder = new HeaderViewHolder(headerView);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position, CommentList.CommentsBean data) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void showBookHelpDetail(BookHelp data) {
        Glide.with(mContext).load(Constant.IMG_BASE_URL + data.help.author.avatar)
                .placeholder(R.drawable.avatar_default)
                .transform(new GlideCircleTransform(mContext))
                .into(headerViewHolder.ivAvatar);

        headerViewHolder.tvNickName.setText(data.help.author.nickname);
        headerViewHolder.tvTime.setText(FormatUtils.getDescriptionTimeFromDateString(data.help.created));
        headerViewHolder.tvTitle.setText(data.help.title);
        headerViewHolder.tvContent.setText(data.help.content);
        headerViewHolder.tvCommentCount.setText(String.format(mContext.getString(R.string.comment_comment_count), data.help.commentCount));

    }

    @Override
    public void showBestComments(CommentList list) {
        mAdapter.addAll(list.comments);
        start = start + list.comments.size();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.getBookHelpComments(id, start, limit);
    }

    @Override
    public void showBookHelpComments(CommentList list) {
        if (list.comments.isEmpty()) {
            gone(headerViewHolder.tvBestComments, headerViewHolder.rvBestComments);
        } else {
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
    public void onItemClick(int position) {
        CommentList.CommentsBean data = mAdapter.getItem(position);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
