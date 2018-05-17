package zhou.com.snailbook.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import zhou.com.snailbook.component.AppComponent;
import zhou.com.snailbook.view.loadding.CustomDialog;

/**
 * Created by zhou on 2018/1/25.
 */

public abstract class BaseFragment extends Fragment {

    protected View parentView;
    protected FragmentActivity activity;
    protected LayoutInflater inflater;
    protected Context mContext;
    private CustomDialog dialog;
    public abstract @LayoutRes
    int getLayoutResId();
    public abstract void attachView();
    public abstract void initDatas();
    public abstract void configViews();
    protected abstract void setupActivityComponent(AppComponent appComponent);

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setupActivityComponent(ReaderApplication.getsInstance().getAppComponent());
        attachView();
        initDatas();
        configViews();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(getLayoutResId(), container, false);
        activity = getSupportActivity();
        mContext = activity;
        this.inflater = inflater;
        return parentView;
    }

    public FragmentActivity getSupportActivity() {
        return super.getActivity();
    }

    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(getActivity());
            dialog.setCancelable(false);
        }
        return dialog;
    }

    public void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    public void showDialog() {
        getDialog().show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void startToActivity(Class zlass){
        Intent intent = new Intent(mContext,zlass);
        startActivity(intent);
    }

    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

}
