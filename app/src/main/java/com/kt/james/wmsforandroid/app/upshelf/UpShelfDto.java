package com.kt.james.wmsforandroid.app.upshelf;

import com.kt.james.wmsforandroid.base.BaseDto;

public class UpShelfDto extends BaseDto {

    public static final int STATE_FINISH = 2;

    private UpShelfBean result;

    public UpShelfBean getResult() {
        return result;
    }

    public void setResult(UpShelfBean result) {
        this.result = result;
    }

}
