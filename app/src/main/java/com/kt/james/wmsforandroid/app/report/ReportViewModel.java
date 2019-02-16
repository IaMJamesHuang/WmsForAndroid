package com.kt.james.wmsforandroid.app.report;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.kt.james.wmsforandroid.net.HttpClient;
import com.kt.james.wmsforandroid.utils.ToastUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ReportViewModel extends AndroidViewModel {

    public ReportViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<ItemInfoBean>> getAllItem() {
        MutableLiveData<List<ItemInfoBean>> result = new MutableLiveData<>();
        HttpClient.Builder.getWmsService().getAllItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetAllItemDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetAllItemDto getAllItemDto) {
                        ToastUtil.showToast(getAllItemDto.getResponseMsg());
                        if (getAllItemDto.getResponseCode() == HttpClient.CODE_SUCCESS) {
                            result.setValue(getAllItemDto.getResult());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return result;
    }

}
