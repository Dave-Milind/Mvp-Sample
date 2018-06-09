package com.milind.mymandirsample.Home;

import android.content.Context;

import com.milind.mymandirsample.Network.HomeService;

import java.util.ArrayList;

import dagger.Component;
import okhttp3.ResponseBody;
import retrofit2.Call;

@Component (modules = HomeModule.class)
public interface HomeComponent {

    Context getHomeContext();

    Call<ArrayList<HomeModel>> getHomeCall();

   HomeService getHomeService();

}
