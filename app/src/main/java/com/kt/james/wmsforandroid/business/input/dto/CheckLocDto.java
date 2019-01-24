package com.kt.james.wmsforandroid.business.input.dto;

import com.kt.james.wmsforandroid.business.BaseDto;

public class CheckLocDto extends BaseDto {

    private CheckLocBean location;

    public CheckLocBean getLocation() {
        return location;
    }

    public void setLocation(CheckLocBean location) {
        this.location = location;
    }

}
