package com.kt.james.wmsforandroid.app.input.dto;

import com.kt.james.wmsforandroid.base.BaseDto;

public class CheckItemBarcodeDto extends BaseDto {

    private ItemBarcodeBean result;

    public ItemBarcodeBean getResult() {
        return result;
    }

    public void setResult(ItemBarcodeBean result) {
        this.result = result;
    }

}
