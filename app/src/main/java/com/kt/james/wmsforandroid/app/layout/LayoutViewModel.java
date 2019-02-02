package com.kt.james.wmsforandroid.app.layout;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.kt.james.wmsforandroid.app.utils.WmsSpManager;
import com.kt.james.wmsforandroid.net.HttpClient;

import java.util.Iterator;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class LayoutViewModel extends AndroidViewModel {

    public LayoutViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<LayoutPoint>> requestLayoutInfo() {
        MutableLiveData<List<LayoutPoint>> result = new MutableLiveData<>();
        HttpClient.Builder.getWmsService().getLayoutInfos(WmsSpManager.getCompanyId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<GetLayoutsDto, List<LayoutPoint>>() {
                    @Override
                    public List<LayoutPoint> apply(GetLayoutsDto getLayoutsDto) throws Exception {
                        if (getLayoutsDto.getResponseCode() == HttpClient.CODE_SUCCESS && getLayoutsDto != null && getLayoutsDto.getResult() != null) {
                            return getLayoutsDto.getResult().getInfos();
                        }
                        return null;
                    }
                }).subscribe(new Observer<List<LayoutPoint>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<LayoutPoint> layoutPoints) {
                if (layoutPoints != null) {
                    result.setValue(layoutPoints);
                } else {
                    result.setValue(null);
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

    public MutableLiveData<Boolean> postLayoutInfo(List<LayoutPoint> info) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        if (info == null) {
            result.setValue(false);
            return result;
        }
        Iterator<LayoutPoint> it = info.iterator();
        while (it.hasNext()) {
            LayoutPoint point = it.next();
            if (point.getState() != LayoutSurfaceView.STATE_FILL) {
                it.remove();
            }
        }
        GetLayoutBean layoutBean = new GetLayoutBean();
        layoutBean.setInfos(info);
        Gson gson = new Gson();
        String jsonData = gson.toJson(layoutBean);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonData);
        HttpClient.Builder.getWmsService().postLayoutInfos(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostLayoutsDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostLayoutsDto postLayoutsDto) {
                        if (postLayoutsDto != null && postLayoutsDto.getResponseCode() == HttpClient.CODE_SUCCESS) {
                            result.setValue(true);
                        } else {
                            result.setValue(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.setValue(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return result;
    }

}
