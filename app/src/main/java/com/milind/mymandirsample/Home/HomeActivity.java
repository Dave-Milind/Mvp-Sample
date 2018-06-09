package com.milind.mymandirsample.Home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.milind.mymandirsample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    HomePresenter presenter;
    @BindView(R.id.recycler_view_home)
    RecyclerView recyclerViewHome;
    public static final String MESSAGE_PROGRESS = "message_progress";
    public static HomeComponent homeComponent;
    @BindView(R.id.text_view_download)
    TextView textViewDownload;
    @BindView(R.id.progressbar_download)
    ProgressBar progressbarDownload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        homeComponent = DaggerHomeComponent.builder().homeModule(new HomeModule(this)).build();
        presenter = new HomePresenter();
        presenter.attachComponent(homeComponent);
        presenter.attachView(this);
        presenter.start();


    }

    @Override
    public void startRecyclerView(HomeAdapter adapter) {
        recyclerViewHome.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHome.setAdapter(adapter);
    }

    @Override
    public void setReceiver() {

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);


    }
    BroadcastReceiver  broadcastReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(MESSAGE_PROGRESS)){

                DownloadModel download = intent.getParcelableExtra("download");
                progressbarDownload.setVisibility(View.VISIBLE);
                textViewDownload.setVisibility(View.VISIBLE);
                progressbarDownload.setProgress(download.getProgress());
                if(download.getProgress() == 100){

                    textViewDownload.setText("File Download Complete");
                    textViewDownload.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            progressbarDownload.setVisibility(View.GONE);
                            textViewDownload.setVisibility(View.GONE);
                        }
                    },3000);


                } else {
                    textViewDownload.setText(String.format("Downloading (%d/%d) MB",download.getCurrentFileSize(),download.getTotalFileSize()));
                } } }};


}
