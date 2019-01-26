package com.kt.james.wmsforandroid.app.about;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.base.BaseActivity;
import com.kt.james.wmsforandroid.databinding.ActivityAboutBinding;
import com.kt.james.wmsforandroid.utils.ResourceUtil;
import com.kt.james.wmsforandroid.utils.imgloader.ImageLoader;

@Route(path = "/about/about_activity", name = "关于")
public class AboutActivity extends BaseActivity<AboutViewModel, ActivityAboutBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(ResourceUtil.getString(R.string.about_title));
        ImageLoader.getInstance().displayImage(this, R.mipmap.wms_icon, bindingView.ivIcon);
        bindingView.tvVersionName.setText(String.format(ResourceUtil.getString(R.string.about_current_version), "V1.0.0"));
        showContentView();
    }

}
