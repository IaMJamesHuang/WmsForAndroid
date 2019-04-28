package com.kt.james.pluginshelf.upshelf;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.kt.james.wmsforandroid.app.scan.dto.CheckLocBean;
import com.kt.james.wmsforandroid.app.scan.dto.ItemBarcodeBean;
import com.kt.james.wmsforandroid.app.upshelf.UpShelfBean;
import com.kt.james.wmsforandroid.app.upshelf.UpShelfDto;
import com.kt.james.wmsforandroid.base.BaseVM;
import com.kt.james.wmsforandroid.base.BaseView;
import com.kt.james.wmsforandroid.net.HttpClient;
import com.kt.james.wmsforandroid.utils.MathUtil;
import com.kt.james.wmsforandroid.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class UpShelfItemViewModel extends AndroidViewModel implements BaseVM {

    private UpShelfBean upShelfInfo;

    private CheckLocBean locInfo;

    private ItemBarcodeBean itemInfo;

    private UpShelfItemView mView;

    public UpShelfItemViewModel(@NonNull Application application) {
        super(application);
    }

    public void setUpShelfInfo(UpShelfBean upShelfInfo) {
        this.upShelfInfo = upShelfInfo;
        mView.setShelfView(upShelfInfo);
    }

    public void setLocInfo(CheckLocBean locInfo) {
        this.locInfo = locInfo;
        mView.setShelfLoc(locInfo.getName());
    }

    public void setItemInfo(ItemBarcodeBean itemInfo) {
        this.itemInfo = itemInfo;
        mView.setInputItem(itemInfo.getName());
    }

    public MutableLiveData<UpShelfBean> submit() {
        MutableLiveData<UpShelfBean> result = new MutableLiveData<>();
        if (!checkInfos()) {
            result.setValue(null);
            return result;
        }
        HttpClient.Builder.getWmsService().getUpShelfInfo(String.valueOf(upShelfInfo.getShelfListId()),
                locInfo.getLoc_x(), locInfo.getLoc_y(), itemInfo.getId(), locInfo.getId(),
                MathUtil.tryFormatFloat(mView.getShelfNum(), 0))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<UpShelfDto, UpShelfBean>() {
                    @Override
                    public UpShelfBean apply(UpShelfDto dto) throws Exception {
                        if (dto != null) {
                            ToastUtil.showToast(dto.getResponseMsg());
                        }
                        if (dto == null || dto.getResponseCode() != HttpClient.CODE_SUCCESS ) {
                            return null;
                        }
                        return dto.getResult();
                    }
                }).subscribe(new Observer<UpShelfBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UpShelfBean upShelfBean) {
                result.setValue(upShelfBean);
            }

            @Override
            public void onError(Throwable e) {
                result.setValue(null);
                ToastUtil.showToast("请求失败");
            }

            @Override
            public void onComplete() {

            }
        });
        return result;
    }

    private boolean checkInfos() {
        if (TextUtils.isEmpty(mView.getInputItem())) {
            ToastUtil.showToast("请录入商品条码");
            return false;
        }
        if (TextUtils.isEmpty(mView.getShelfLoc())) {
            ToastUtil.showToast("请录入库位信息");
            return false;
        }
        if (TextUtils.isEmpty(mView.getShelfNum())) {
            ToastUtil.showToast("请输入上架数量");
            return false;
        }
        if (!MathUtil.isFloatRight(mView.getShelfNum())) {
            ToastUtil.showToast("请输入正确的数字");
            return false;
        }
        return true;
    }

    public void cleanInfo() {
        locInfo = null;
        itemInfo = null;
        mView.cleanView();
    }

    @Override
    public void setView(BaseView baseView) {
        mView = (UpShelfItemView) baseView;
    }
}
