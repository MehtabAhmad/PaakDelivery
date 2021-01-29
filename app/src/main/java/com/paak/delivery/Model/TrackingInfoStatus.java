package com.paak.delivery.Model;

import java.util.List;

public class TrackingInfoStatus {

    boolean status;
    List<TrackingInfo> info;

    public boolean isStatus() {
        return status;
    }

    public List<TrackingInfo> getInfo() {
        return info;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setInfo(List<TrackingInfo> info) {
        this.info = info;
    }
}
