package com.paak.delivery.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.paak.delivery.Model.Delivery;
import com.paak.delivery.Model.DeliveryDetail;
import com.paak.delivery.Networking.RetrofitClient;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryRepository {

    private final MutableLiveData<List<Delivery>> deliveries = new MutableLiveData<>();
    private final MutableLiveData<DeliveryDetail> deliveryDetail = new MutableLiveData<>();

    public void getDeliveries() {
        RetrofitClient.getService().getDeliveries()
                .enqueue(new Callback<List<Delivery>>() {
                    @Override
                    public void onResponse(@NotNull Call<List<Delivery>> call, @NotNull Response<List<Delivery>> response) {
                        if (response.body() != null) {
                            deliveries.postValue(response.body());
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<List<Delivery>> call, @NotNull Throwable t) {
                        deliveries.postValue(null);
                    }
                });
    }


    public void getDeliveryDetail(String id) {
        RetrofitClient.getService().getDeliveryDetail(id)
                .enqueue(new Callback<DeliveryDetail>() {
                    @Override
                    public void onResponse(@NotNull Call<DeliveryDetail> call, @NotNull Response<DeliveryDetail> response) {
                        if (response.body() != null) {
                            deliveryDetail.postValue(response.body());
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<DeliveryDetail> call, @NotNull Throwable t) {
                        deliveryDetail.postValue(null);
                    }
                });
    }

    public LiveData<List<Delivery>> getDeliveriesList() {
        return deliveries;
    }

    public LiveData<DeliveryDetail> getDeliveryDetail() {
        return deliveryDetail;
    }
}
