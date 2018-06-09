package com.milind.mymandirsample.Network;

import com.milind.mymandirsample.Home.HomeModel;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface MyMandirService {

    @GET("dummy")
    Call<ArrayList<HomeModel>> getDummyResponse();



}
