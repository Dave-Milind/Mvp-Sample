package com.milind.mymandirsample.Details;

public class DetailPresenter implements DetailContract.Presenter{

DetailContract.View view;
    @Override
    public void start() {
        String url=view.getIntentData();
        view.setImage(url);
    }

    @Override
    public void attachView(DetailContract.View view) {
        this.view=view;
    }
}
