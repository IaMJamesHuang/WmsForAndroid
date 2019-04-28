package com.kt.james.pluginshelf.upshelf;

import com.kt.james.wmsforandroid.app.upshelf.UpShelfBean;
import com.kt.james.wmsforandroid.base.BaseView;

/**
 * author: James
 * 2019/4/28 19:26
 * version: 1.0
 */
public interface UpShelfItemView extends BaseView {

    void setShelfView(UpShelfBean data);

    void cleanView();

    String getInputItem();

    String getShelfLoc();

    String getShelfNum();

    void setInputItem(String name);

    void setShelfLoc(String loc);

    void setShelfNum(String shelfNum);

}
