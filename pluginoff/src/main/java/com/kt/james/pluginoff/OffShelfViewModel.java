package com.kt.james.pluginoff;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.kt.james.wmsforandroid.app.offshelf.OffShelfDto;
import com.kt.james.wmsforandroid.app.utils.WmsSpManager;
import com.kt.james.wmsforandroid.base.BaseVM;
import com.kt.james.wmsforandroid.base.BaseView;
import com.kt.james.wmsforandroid.net.HttpClient;
import com.kt.james.wmsforandroid.utils.MathUtil;
import com.kt.james.wmsforandroid.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OffShelfViewModel extends AndroidViewModel implements BaseVM {

    private OffShelfView mView;

    public OffShelfViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> submit() {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        if (!verifyData()) {
            data.setValue(false);
            return data;
        }
        HttpClient.Builder.getWmsService().offShelfItem(mView.getItemBarcode(), WmsSpManager.getCompanyId(),
                MathUtil.tryFormatFloat(mView.getItemNum(), -1f), mView.getItemLoc())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OffShelfDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OffShelfDto offShelfDto) {
                        if (offShelfDto != null && offShelfDto.getResponseCode() == HttpClient.CODE_SUCCESS) {
                            data.setValue(true);
                        } else {
                            data.setValue(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        data.setValue(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return data;
    }

    private boolean verifyData() {
        if (TextUtils.isEmpty(mView.getItemLoc())) {
            ToastUtil.showToast("请扫描库位！");
            return false;
        }
        if (TextUtils.isEmpty(mView.getItemBarcode())) {
            ToastUtil.showToast("请扫描商品条码！");
            return false;
        }
        return !(MathUtil.tryFormatFloat(mView.getItemBarcode(), 0f) == 0f);
    }

    @Override
    public void setView(BaseView baseView) {
        mView = (OffShelfView) baseView;
    }
}
