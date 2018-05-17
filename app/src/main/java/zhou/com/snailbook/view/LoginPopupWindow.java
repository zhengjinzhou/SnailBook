package zhou.com.snailbook.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;

import zhou.com.snailbook.R;

/**
 * Created by zhou on 2018/1/26.
 */

public class LoginPopupWindow extends PopupWindow implements View.OnTouchListener {

    private Activity mActivity;
    private View mContentView;

    private ImageView qq;
    private ImageView weibo;
    private ImageView wechat;

    public LoginPopupWindow(Activity activity) {
        mActivity = activity;
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        mContentView = LayoutInflater.from(activity).inflate(R.layout.layout_login_popup_window, null);
        setContentView(mContentView);

        qq = mContentView.findViewById(R.id.ivQQ);
        weibo = mContentView.findViewById(R.id.ivWeibo);
        wechat = mContentView.findViewById(R.id.ivWechat);

        qq.setOnTouchListener(this);
        weibo.setOnTouchListener(this);
        wechat.setOnTouchListener(this);

        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));

        setAnimationStyle(R.style.LoginPopup);

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                lighton();
            }
        });

    }

    private void scale(View v, boolean isDown) {
        if (v.getId() == qq.getId() || v.getId() == weibo.getId() || v.getId() == wechat.getId()) {
            if (isDown) {
                Animation testAnim = AnimationUtils.loadAnimation(mActivity, R.anim.scale_down);
                v.startAnimation(testAnim);
            } else {
                Animation testAnim = AnimationUtils.loadAnimation(mActivity, R.anim.scale_up);
                v.startAnimation(testAnim);
                dismiss();
            }
        }
    }

    private void lighton() {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 1.0f;
        mActivity.getWindow().setAttributes(lp);
    }

    private void lightoff() {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.3f;
        mActivity.getWindow().setAttributes(lp);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        lightoff();
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        lightoff();
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scale(v, true);
                break;
            case MotionEvent.ACTION_UP:
                scale(v, false);
                break;
        }
        return false;
    }
}
