package com.paak.delivery.Repository;

import android.content.Context;

import com.google.gson.JsonObject;
import com.paak.delivery.Model.TrackingInfo;
import com.paak.delivery.Networking.RetrofitClient;
import com.paak.delivery.Persistance.DBHandler;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoTrackingRepository {

    DBHandler dbHandler;

    public InfoTrackingRepository(Context context) {
        dbHandler = DBHandler.getInstance(context);
    }


    public void sendTrackingInfo(List<TrackingInfo> infoList, String driverID) {
        RetrofitClient.getService().sendTrackingInfo(infoList, driverID)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                        if (response.body() != null) {
                            JsonObject jsonObject = response.body();
                            if (jsonObject.get("status").getAsString().equalsIgnoreCase("ok")) {
                                deleteInfo(infoList);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {

                    }
                });
    }

    private void deleteInfo(List<TrackingInfo> info) {
        dbHandler.deleteInfo(info);
    }

    public void saveTrackingInfo(TrackingInfo info) {
        dbHandler.saveTrackingInfo(info);
    }

    public List<TrackingInfo> getTrackingInfo() throws ExecutionException, InterruptedException {
        return dbHandler.getTrackingInfo();
    }
}
