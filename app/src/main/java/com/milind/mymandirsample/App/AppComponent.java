package com.milind.mymandirsample.App;


import com.milind.mymandirsample.Network.MyMandirService;

import dagger.Component;

@Component (modules = AppModule.class)
public interface AppComponent {

    MyMandirService getMyMandirService();
}
