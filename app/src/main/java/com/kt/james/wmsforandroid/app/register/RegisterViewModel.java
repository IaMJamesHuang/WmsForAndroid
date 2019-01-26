package com.kt.james.wmsforandroid.app.register;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.app.login.LoginDto;
import com.kt.james.wmsforandroid.app.utils.WmsSpManager;
import com.kt.james.wmsforandroid.net.HttpClient;
import com.kt.james.wmsforandroid.utils.ResourceUtil;
import com.kt.james.wmsforandroid.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class RegisterViewModel extends AndroidViewModel {

    public final ObservableField<String> companyName = new ObservableField<>();
    public final ObservableField<String> username = new ObservableField<>();
    public final ObservableField<String> nick = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> register() {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        if (!verifyData()) {
            data.setValue(false);
            return data;
        }
        HttpClient.Builder.getWmsService()
                .register(companyName.get(), username.get(), nick.get(), password.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginDto loginDto) {
                        if (loginDto != null && loginDto.getResponseCode() == HttpClient.CODE_SUCCESS) {
                            WmsSpManager.setCompanyId(loginDto.getUserBean().getCompany_id());
                            data.setValue(true);
                        } else {
                            if (loginDto != null) {
                                ToastUtil.showToast(loginDto.getResponseMsg());
                            }
                            data.setValue(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return data;
    }

    private boolean verifyData() {
        if (TextUtils.isEmpty(companyName.get())) {
            ToastUtil.showToast(String.format(
                    ResourceUtil.getString(R.string.register_toast_pattern), "公司名称！"));
            return false;
        }
        if (TextUtils.isEmpty(username.get())) {
            ToastUtil.showToast(String.format(
                    ResourceUtil.getString(R.string.register_toast_pattern), "用户名！"));
            return false;
        }
        if (TextUtils.isEmpty(nick.get())) {
            ToastUtil.showToast(String.format(
                    ResourceUtil.getString(R.string.register_toast_pattern), "昵称！"));
            return false;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtil.showToast(String.format(
                    ResourceUtil.getString(R.string.register_toast_pattern), "密码！"));
            return false;
        }
        return true;
    }

}
