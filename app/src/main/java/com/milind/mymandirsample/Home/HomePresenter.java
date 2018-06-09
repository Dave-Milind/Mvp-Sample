package com.milind.mymandirsample.Home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.milind.mymandirsample.Details.DetailActivity;
import com.milind.mymandirsample.Service.DownloadService;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.Presenter, onMediaClick {

    HomeContract.View view;
    HomeComponent homeComponent;
    Context context;

    @Override
    public void attachView(HomeContract.View view) {

        this.view = view;
    }

    @Override
    public void attachComponent(HomeComponent homeComponent) {
        this.homeComponent = homeComponent;
    }

    @Override
    public void start() {
        context = homeComponent.getHomeContext();
        Call<ArrayList<HomeModel>> call = homeComponent.getHomeCall();
        call.enqueue(new Callback<ArrayList<HomeModel>>() {
            @Override
            public void onResponse(Call<ArrayList<HomeModel>> call, Response<ArrayList<HomeModel>> response) {
                if (response.isSuccessful()) {

                    ArrayList<HomeModel> homeModelArrayList = response.body();
                    HomeAdapter homeAdapter = new HomeAdapter(context, homeModelArrayList);
                    homeAdapter.setPresenter(HomePresenter.this);
                    view.startRecyclerView(homeAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HomeModel>> call, Throwable t) {

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        view.setReceiver();


    }


    @Override
    public void onMediaclick(String type, String url, String mediaName) {
        if (type != null) {
            if (type.equals("video") || type.equals("audio")) {

                startDownload(url.trim(), type, mediaName);
                Toast.makeText(context, "Downloading", Toast.LENGTH_SHORT).show();
            } else if (type.equals("image")) {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("url",url);
                context.startActivity(intent);
            }
        }
    }


    @Override
    public void startDownload(String mediaParams, String type, String userName) {

        RxPermissions rxPermissions = new RxPermissions((Activity) homeComponent.getHomeContext());
        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(permission -> {
            if (permission.granted) {
                Intent intent = new Intent(homeComponent.getHomeContext(), DownloadService.class);
                intent.putExtra("params", mediaParams);
                intent.putExtra("type", type);
                intent.putExtra("username", userName);
                homeComponent.getHomeContext().startService(intent);
            } else {
                Toast.makeText(homeComponent.getHomeContext(), "Please provide storage permissions", Toast.LENGTH_LONG)
                        .show();
            }

        });
    }


}
