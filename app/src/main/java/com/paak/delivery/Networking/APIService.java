package com.paak.delivery.Networking;

import com.google.gson.JsonObject;
import com.paak.delivery.Model.Delivery;
import com.paak.delivery.Model.DeliveryDetail;
import com.paak.delivery.Model.TrackingInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {

    @GET("deliveries")
    Call<List<Delivery>> getDeliveries();

    @GET("deliveryDetail")
    Call<DeliveryDetail> getDeliveryDetail(@Query("id") String id);

    @GET("sendTrackingInfo")
    Call<JsonObject> sendTrackingInfo(@Query("tracking_data") List<TrackingInfo> trackingInfo,
                                      @Query("driver_id") String id);
}
