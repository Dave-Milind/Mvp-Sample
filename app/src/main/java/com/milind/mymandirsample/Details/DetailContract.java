package com.milind.mymandirsample.Details;

import com.milind.mymandirsample.Base.BasePresenter;
import com.milind.mymandirsample.Base.BaseView;

public interface DetailContract {

    interface View extends BaseView{

        void setImage(String url);

        String getIntentData();
    }
    interface Presenter<T extends View> extends BasePresenter{

        void attachView(T view);
    }
}
