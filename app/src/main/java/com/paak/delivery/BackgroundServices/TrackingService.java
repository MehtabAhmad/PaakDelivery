package com.paak.delivery.BackgroundServices;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;

import com.google.gson.Gson;
import com.paak.delivery.BackgroundServices.Location.LocationClient;
import com.paak.delivery.Model.TrackingInfo;
import com.paak.delivery.Repository.InfoTrackingRepository;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class TrackingService extends LifecycleService {

    private static final String TAG = "TRACKING";
    public int counter = 0;
    private Context context;
    BatteryManager bm;
    InfoTrackingRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
        repository = new InfoTrackingRepository(context);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "paak.delivery";
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stoptimertask();
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, TrackingBCR.class);
        this.sendBroadcast(broadcastIntent);
    }

    private Timer timer;
    private TimerTask timerTask;

    public void startTimer() {
        if (timer == null) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                public void run() {

                    Log.i(TAG, "Second : **************" + (counter++));

                    if (counter % 10 == 0) {
                        try {
                            List<TrackingInfo> info = repository.getTrackingInfo();
                            Log.i(TAG, " Request Body : **************  "+ new Gson().toJson(info));
                            repository.sendTrackingInfo(info, "12345");

                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                    String ts = String.valueOf(System.currentTimeMillis()/1000);

                    TrackingInfo info = new TrackingInfo();

                    info.setBatteryLeve(batLevel);
                    info.setDeliveryId("7899");
                    info.setTimestamp(ts);

                    LocationClient.getInstance(context).fusedLocationClient.getLastLocation().addOnCompleteListener(task -> {

                        if (task.getResult() != null) {
                            info.setLatitude(String.valueOf(task.getResult().getLatitude()));
                            info.setLongitude(String.valueOf(task.getResult().getLongitude()));
                        }
                    });
                    repository.saveTrackingInfo(info);
                }
            };
            timer.schedule(timerTask, 1000, 1000);
        }
    }


    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
