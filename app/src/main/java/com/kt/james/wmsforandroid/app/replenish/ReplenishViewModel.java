package com.kt.james.wmsforandroid.app.replenish;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.kt.james.wmsforandroid.net.HttpClient;
import com.kt.james.wmsforandroid.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ReplenishViewModel extends AndroidViewModel {

    public ReplenishViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ReplenishBean> getReplenishInfos() {
        MutableLiveData<ReplenishBean> result = new MutableLiveData<>();
        HttpClient.Builder.getWmsService().getReplenishInfos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReplenishDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ReplenishDto replenishDto) {
                        ToastUtil.showToast(replenishDto.getResponseMsg());
                        if (replenishDto.getResponseCode() == HttpClient.CODE_SUCCESS) {
                            result.setValue(replenishDto.getResult());
                        } else {
                            result.setValue(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast("网络异常");
                        result.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return result;
    }

}
