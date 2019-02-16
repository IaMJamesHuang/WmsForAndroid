package com.kt.james.wmsforandroid.app.report;

import com.kt.james.wmsforandroid.base.BaseDto;

import java.util.List;

public class GetAllItemDto extends BaseDto {

    private List<ItemInfoBean> result;

    public List<ItemInfoBean> getResult() {
        return result;
    }

    public void setResult(List<ItemInfoBean> result) {
        this.result = result;
    }
}
