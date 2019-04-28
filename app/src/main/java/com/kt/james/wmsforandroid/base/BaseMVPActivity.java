package com.kt.james.wmsforandroid.base;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.utils.ARouterUtil;
import com.kt.james.wmsforandroid.utils.ClassUtil;
import com.kt.james.wmsforandroid.utils.ResourceUtil;
import com.kt.james.wmsforandroid.view.PerfectClickListener;
import com.kt.james.wmsforandroid.view.StatusBarUtil;

/**
 * author: James
 * 2019/4/28 12:13
 * version: 1.0
 */
public abstract class BaseMVPActivity<VM extends AndroidViewModel> extends AppCompatActivity {

    protected VM viewModel;
    protected ViewGroup mRoot;

    private View refresh;
    private View loadingView;
    private View bindingView;

    private Toolbar toolBar;

    private AnimationDrawable mAnimationDrawable;

    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);
        mRoot = getView(R.id.ll_root);

        LayoutInflater inflater = getLayoutInflater();
        bindingView = inflater.inflate(layoutResID, null,false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bindingView.setLayoutParams(params);
        RelativeLayout mContainer = findViewById(R.id.container);
        mContainer.addView(bindingView);

        //initView
        toolBar = getView(R.id.tool_bar);

        // 设置透明状态栏，兼容4.4
        StatusBarUtil.setColor(this, ResourceUtil.getColor(R.color.colorTheme), 0);
        loadingView = ((ViewStub) findViewById(R.id.vs_loading)).inflate();
        refresh = getView(R.id.ll_error_refresh);
        ImageView img = loadingView.findViewById(R.id.img_progress);

        // 加载动画
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        // 默认进入页面就开启动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }

        setToolBar();
        // 点击加载失败布局
        refresh.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                showLoading();
                onRefresh();
            }
        });
        bindingView.setVisibility(View.GONE);
        initViewModel();
    }

    protected void setToolBar() {
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back2);
        }
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    onBackPressed();
                }
            }
        });
    }

    protected void showError() {
        if (loadingView != null && loadingView.getVisibility() != View.GONE) {
            loadingView.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (refresh.getVisibility() != View.VISIBLE) {
            refresh.setVisibility(View.VISIBLE);
        }
        if (bindingView.getVisibility() != View.GONE) {
            bindingView.setVisibility(View.GONE);
        }
    }

    protected void showLoading() {
        if (loadingView != null && loadingView.getVisibility() != View.VISIBLE) {
            loadingView.setVisibility(View.VISIBLE);
        }
        // 开始动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        if (bindingView.getVisibility() != View.GONE) {
            bindingView.setVisibility(View.GONE);
        }
        if (refresh.getVisibility() != View.GONE) {
            refresh.setVisibility(View.GONE);
        }
    }

    /**
     * 失败后点击刷新
     */
    protected void onRefresh() {

    }

    private void initViewModel() {
        Class<VM> viewModelClass = ClassUtil.getViewModel(this);
        if (viewModelClass != null) {
            this.viewModel = ViewModelProviders.of(this).get(viewModelClass);
        }
    }

    protected void showContentView() {
        if (loadingView != null && loadingView.getVisibility() != View.GONE) {
            loadingView.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (refresh.getVisibility() != View.GONE) {
            refresh.setVisibility(View.GONE);
        }
        if (bindingView.getVisibility() != View.VISIBLE) {
            bindingView.setVisibility(View.VISIBLE);
        }
    }

    protected void nav(String uri) {
        ARouterUtil.nav(this, uri);
    }

    protected void nav(String uri, Bundle bundle) {
        ARouterUtil.nav(this, uri, bundle);
    }

    private void navForResult(String uri, int requestCode) {
        ARouterUtil.navForResult(this, uri, requestCode);
    }

    private void navForResult(String uri, Bundle bundle, int requestCode) {
        ARouterUtil.navForResult(this, uri, bundle, requestCode);
    }

    public void setBarTitle(String title) {
        toolBar.setTitle(title);
    }

}
