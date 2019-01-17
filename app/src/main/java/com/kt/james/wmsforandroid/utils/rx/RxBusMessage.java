package com.kt.james.wmsforandroid.utils.rx;

public class RxBusMessage {

    private int code;
    private Object object;
    public RxBusMessage(int code, Object object){
        this.code=code;
        this.object=object;
    }
    public RxBusMessage(){}

    public int getCode() {
        return code;
    }

    public Object getObject() {
        return object;
    }

}
