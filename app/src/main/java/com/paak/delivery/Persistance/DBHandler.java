package com.paak.delivery.Persistance;

import android.content.Context;
import android.os.AsyncTask;
import androidx.room.Room;
import com.paak.delivery.Model.TrackingInfo;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class DBHandler {

    TrackingInfoDB trackingInfoDB;
    public static DBHandler handler;


    private DBHandler(Context context) {
        trackingInfoDB = Room.databaseBuilder(context, TrackingInfoDB.class, "TrackingInfoDB")
                .build();
    }



    public static DBHandler getInstance(Context context) {
        if (handler == null)
            handler = new DBHandler(context);
        return handler;
    }



    public void saveTrackingInfo(TrackingInfo info) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                trackingInfoDB.getDAO().saveInfo(info);
            }
        });
    }



    public void deleteInfo(List<TrackingInfo> info) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                trackingInfoDB.getDAO().deleteInfo(info);
            }
        });
    }



    public List<TrackingInfo> getTrackingInfo() throws ExecutionException, InterruptedException {
        return new getInfoTask().execute().get();
    }



    private class getInfoTask extends AsyncTask<Void, Void, List<TrackingInfo>>
    {
        @Override
        protected List<TrackingInfo> doInBackground(Void... url) {
            return trackingInfoDB.getDAO().getTrackingInfo();
        }
    }
}
