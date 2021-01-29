package com.paak.delivery.Model;

import java.io.Serializable;

public class DeliveryDetail implements Serializable {

    private String id;
    private String address;
    private String latitude;
    private String longitude;
    private String customerName;
    boolean requiresSignature;
    String specialInstructions;

    public boolean isRequiresSignature() {
        return requiresSignature;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCustomerName() {
        return customerName;
    }
}
