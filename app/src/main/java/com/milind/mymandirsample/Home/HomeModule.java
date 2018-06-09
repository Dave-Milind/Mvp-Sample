package com.milind.mymandirsample.Home;

import android.content.Context;

import com.milind.mymandirsample.App.AppModule;
import com.milind.mymandirsample.Network.HomeService;
import com.milind.mymandirsample.Network.MyMandirService;

import java.util.ArrayList;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

@Module (includes = AppModule.class)
public class HomeModule {

    Context context;

    HomeModule(Context context) {

        this.context = context;
    }

    @Provides
    Context getContext() {

        return context;

    }

    @Provides
    Call<ArrayList<HomeModel>> getCall(MyMandirService myMandirService) {

        return myMandirService.getDummyResponse();
    }

   @Provides
   @Named("RetrofitDownload")
    Retrofit getRetrofit(){

        return new Retrofit.Builder()
                .baseUrl("https://img4.mymandir.com/")
                .build();
   }


    @Provides
    HomeService getHomeService(@Named("RetrofitDownload") Retrofit retrofit){

      return   retrofit.create(HomeService.class);
    }


}
