package com.milind.mymandirsample.Home;

import com.milind.mymandirsample.Base.BasePresenter;
import com.milind.mymandirsample.Base.BaseView;

public interface HomeContract {


    interface View extends BaseView {

        void startRecyclerView(HomeAdapter adapter);
        void setReceiver();
    }

    interface Presenter<T extends View> extends BasePresenter {

        void attachView(T view);
        void attachComponent(HomeComponent homeComponent);
        void startDownload(String mediaParams,String type,String username);



    }


}
