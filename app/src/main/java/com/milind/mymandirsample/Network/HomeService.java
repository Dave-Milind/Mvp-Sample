package com.milind.mymandirsample.Network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface HomeService {

    @Streaming
    @GET()
    Call<ResponseBody> downloadFile(@Url String params);


}
