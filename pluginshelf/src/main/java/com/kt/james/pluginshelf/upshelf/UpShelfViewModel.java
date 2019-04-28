package com.kt.james.pluginshelf.upshelf;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.kt.james.wmsforandroid.app.upshelf.UpShelfDto;
import com.kt.james.wmsforandroid.base.BaseVM;
import com.kt.james.wmsforandroid.base.BaseView;
import com.kt.james.wmsforandroid.net.HttpClient;
import com.kt.james.wmsforandroid.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UpShelfViewModel extends AndroidViewModel implements BaseVM {

    private UpShelfView mView;

    public UpShelfViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<UpShelfDto> getUpShelfInfos() {
        final MutableLiveData<UpShelfDto> result = new MutableLiveData<>();
        if (TextUtils.isEmpty(mView.getInputOrder())) {
            ToastUtil.showToast("请输入上架单号");
            result.setValue(null);
            return result;
        }
        HttpClient.Builder.getWmsService().getUpShelfInfo(mView.getInputOrder(), 0, 0,
                0, 0, 0.0f)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpShelfDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpShelfDto upShelfDto) {
                        if (upShelfDto == null) {
                            result.setValue(null);
                            ToastUtil.showToast("调用失败");
                        } else if(upShelfDto.getResponseCode() != HttpClient.CODE_SUCCESS) {
                            result.setValue(null);
                            ToastUtil.showToast(upShelfDto.getResponseMsg());
                        } else {
                            if (upShelfDto.getResult() == null ||
                                    upShelfDto.getResult().getShelfListState() == UpShelfDto.STATE_FINISH) {
                                result.setValue(null);
                                ToastUtil.showToast(upShelfDto.getResponseMsg());
                            } else {
                                result.setValue(upShelfDto);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.setValue(null);
                        ToastUtil.showToast("调用失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return result;
    }

    @Override
    public void setView(BaseView baseView) {
        mView = (UpShelfView) baseView;
    }
}
