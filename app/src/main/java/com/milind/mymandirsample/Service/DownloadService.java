package com.milind.mymandirsample.Service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.milind.mymandirsample.Home.DownloadModel;
import com.milind.mymandirsample.Home.HomeActivity;
import com.milind.mymandirsample.Network.HomeService;
import com.milind.mymandirsample.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;


public class DownloadService extends IntentService {



    public DownloadService() {
        super("Download Service");
    }

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    private int totalFileSize;

    String params;
    String type;
    String userName;


//This is the first method that is called when this InterService starts
    @Override
    protected void onHandleIntent(Intent intent) {

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this,"downloadId")
                .setSmallIcon(R.drawable.ic_download)
                .setContentTitle("Download")
                .setContentText("Downloading File")
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());

        params=intent.getStringExtra("params");
        type=intent.getStringExtra("type");
        userName=intent.getStringExtra("username");
        initDownload();

    }

//This Method will initiate download of file
    private void initDownload(){

     HomeService homeService = HomeActivity.homeComponent.getHomeService();

        Call<ResponseBody> request = homeService.downloadFile(params);


        try {

            downloadFile(request.execute().body());

        } catch (IOException e) {

            e.printStackTrace();


        }
    }

    File outputFile;
    //This method contains the boilerPlate code to actualy download file from Server
    private void downloadFile(ResponseBody body) throws IOException {

        int count;
        byte data[] = new byte[1024 * 4];
        long fileSize = body.contentLength();
        InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
        if(userName!=null && type!=null){
            if(type.equals("video")){
                outputFile= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), userName+".avi");
            }else {

                outputFile= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), userName+".mp3");
            }

        }

        OutputStream output = new FileOutputStream(outputFile);
        long total = 0;
        long startTime = System.currentTimeMillis();
        int timeCount = 1;
        while ((count = bis.read(data)) != -1) {

            total += count;
            totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
            double current = Math.round(total / (Math.pow(1024, 2)));

            int progress = (int) ((total * 100) / fileSize);

            long currentTime = System.currentTimeMillis() - startTime;

            DownloadModel download = new DownloadModel();
            download.setTotalFileSize(totalFileSize);

            if (currentTime > 1000 * timeCount) {

                download.setCurrentFileSize((int) current);
                download.setProgress(progress);
                sendNotification(download);
                timeCount++;
            }

            output.write(data, 0, count);
        }
        onDownloadComplete();
        output.flush();
        output.close();
        bis.close();

    }
//This method sends notifications about the download
    private void sendNotification(DownloadModel download){

        sendIntent(download);
        notificationBuilder.setProgress(100,download.getProgress(),false);
        notificationBuilder.setContentText(String.format("Downloaded (%d/%d) MB",download.getCurrentFileSize(),download.getTotalFileSize()));
        notificationManager.notify(0, notificationBuilder.build());
    }

//this method sends broadcasts about download to out broadcast receiver in HomeActivity
    private void sendIntent(DownloadModel download){

        Intent intent = new Intent(HomeActivity.MESSAGE_PROGRESS);
        intent.putExtra("download",download);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    //This method is called when download is completed
    private void onDownloadComplete(){

        DownloadModel download = new DownloadModel();
        download.setProgress(100);
        sendIntent(download);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0,0,false);
        notificationBuilder.setContentText("File Downloaded");
        notificationManager.notify(0, notificationBuilder.build());

    }
//This method is called to remove notification from our device
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }

}
