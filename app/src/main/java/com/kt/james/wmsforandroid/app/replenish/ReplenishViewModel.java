package com.kt.james.wmsforandroid.app.replenish;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.kt.james.wmsforandroid.net.HttpClient;
import com.kt.james.wmsforandroid.utils.ToastUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;


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

    public MutableLiveData<Boolean> submitReplenishInfos(List<SubmitReplenishBean.SubmitItem> submitInfos) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        if (submitInfos == null || submitInfos.size() == 0) {
            result.setValue(false);
            return result;
        }
        SubmitReplenishBean submitBean = new SubmitReplenishBean();
        submitBean.setSubmitInfos(submitInfos);
        Gson gson = new Gson();
        String jsonData = gson.toJson(submitBean);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonData);
        HttpClient.Builder.getWmsService().postReplenishInfo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostReplenishDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostReplenishDto postReplenishDto) {
                        ToastUtil.showToast(postReplenishDto.getResponseMsg());
                        if (postReplenishDto.getResponseCode() == HttpClient.CODE_SUCCESS) {
                            result.setValue(true);
                        } else {
                            result.setValue(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast("请求失败");
                        result.setValue(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return result;
    }

}
