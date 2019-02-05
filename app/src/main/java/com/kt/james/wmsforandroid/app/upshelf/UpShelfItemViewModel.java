package com.kt.james.wmsforandroid.app.upshelf;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.kt.james.wmsforandroid.app.scan.dto.CheckLocBean;
import com.kt.james.wmsforandroid.app.scan.dto.ItemBarcodeBean;
import com.kt.james.wmsforandroid.net.HttpClient;
import com.kt.james.wmsforandroid.utils.MathUtil;
import com.kt.james.wmsforandroid.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class UpShelfItemViewModel extends AndroidViewModel {

    private UpShelfBean upShelfInfo;

    private CheckLocBean locInfo;

    private ItemBarcodeBean itemInfo;

    public final ObservableField<String> itemName = new ObservableField<>();
    public final ObservableField<String> itemBarcode= new ObservableField<>();
    public final ObservableField<String> itemSpec = new ObservableField<>();
    public final ObservableField<String> itemBrand = new ObservableField<>();
    public final ObservableField<String> locCode = new ObservableField<>();
    public final ObservableField<String> num = new ObservableField<>();
    public final ObservableField<String> etItemBarcode = new ObservableField<>();
    public final ObservableField<String> etLocCode = new ObservableField<>();
    public final ObservableField<String> etNum = new ObservableField<>();

    public UpShelfItemViewModel(@NonNull Application application) {
        super(application);
    }

    public void setUpShelfInfo(UpShelfBean upShelfInfo) {
        this.upShelfInfo = upShelfInfo;
        itemName.set(upShelfInfo.getItemName());
        itemBarcode.set(upShelfInfo.getItemBarcode());
        itemSpec.set(upShelfInfo.getItemSpec());
        itemBrand.set(upShelfInfo.getItemFactory());
        locCode.set(upShelfInfo.getLocName());
        num.set(String.valueOf(upShelfInfo.getNum()));
    }

    public void setLocInfo(CheckLocBean locInfo) {
        this.locInfo = locInfo;
        etLocCode.set(locInfo.getName());
    }

    public void setItemInfo(ItemBarcodeBean itemInfo) {
        this.itemInfo = itemInfo;
        etItemBarcode.set(itemInfo.getBarcode());
    }

    public MutableLiveData<UpShelfBean> submit() {
        MutableLiveData<UpShelfBean> result = new MutableLiveData<>();
        if (!checkInfos()) {
            result.setValue(null);
            return result;
        }
        HttpClient.Builder.getWmsService().getUpShelfInfo(String.valueOf(upShelfInfo.getShelfListId()),
                locInfo.getLoc_x(), locInfo.getLoc_y(), itemInfo.getId(), locInfo.getId(),
                MathUtil.tryFormatFloat(etNum.get(), 0))
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
        if (TextUtils.isEmpty(etItemBarcode.get())) {
            ToastUtil.showToast("请录入商品条码");
            return false;
        }
        if (TextUtils.isEmpty(etLocCode.get())) {
            ToastUtil.showToast("请录入库位信息");
            return false;
        }
        if (TextUtils.isEmpty(etNum.get())) {
            ToastUtil.showToast("请输入上架数量");
            return false;
        }
        if (!MathUtil.isFloatRight(etNum.get())) {
            ToastUtil.showToast("请输入正确的数字");
            return false;
        }
        return true;
    }

    public void cleanInfo() {
        locInfo = null;
        itemInfo = null;
        etItemBarcode.set(null);
        etLocCode.set(null);
        etNum.set(null);
    }

}
