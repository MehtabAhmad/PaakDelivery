package com.paak.delivery.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.paak.delivery.Model.Delivery;
import com.paak.delivery.Model.DeliveryDetail;
import com.paak.delivery.Repository.DeliveryRepository;

import java.util.List;

public class DeliveryViewModel extends AndroidViewModel {

    private DeliveryRepository deliveryRepository;
    private LiveData<List<Delivery>> deliveries;
    private LiveData<DeliveryDetail> delivery;

    public DeliveryViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        deliveryRepository = new DeliveryRepository();
        deliveries = deliveryRepository.getDeliveriesList();
        delivery = deliveryRepository.getDeliveryDetail();
    }

    public void getDeliveries() {
        deliveryRepository.getDeliveries();
    }

    public void getDelivery(String id) {
        deliveryRepository.getDeliveryDetail(id);
    }

    public LiveData<List<Delivery>> getDeliveriesList() {
        return deliveries;
    }

    public LiveData<DeliveryDetail> getDeliveryDetail() {
        return delivery;
    }
}
