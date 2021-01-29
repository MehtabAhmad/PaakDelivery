package com.paak.delivery.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class TrackingInfo implements Serializable {

    @NonNull
    @PrimaryKey
    String timestamp;


    String Latitude;
    String longitude;
    String deliveryId;
    int batteryLeve;

    @NonNull
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NonNull String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getBatteryLeve() {
        return batteryLeve;
    }

    public void setBatteryLeve(int batteryLeve) {
        this.batteryLeve = batteryLeve;
    }
}
