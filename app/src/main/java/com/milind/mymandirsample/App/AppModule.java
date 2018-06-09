package com.milind.mymandirsample.App;


import com.milind.mymandirsample.Network.MyMandirService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {


    @Provides
    Retrofit getRetrofit() {

        return new Retrofit.Builder()
                .baseUrl("http://staging.mymandir.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    MyMandirService getMainService(Retrofit retrofit) {

        return retrofit.create(MyMandirService.class);
    }

}
