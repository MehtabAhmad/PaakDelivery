package com.paak.delivery.View.Activities;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.paak.delivery.BackgroundServices.TrackingBCR;
import com.paak.delivery.BackgroundServices.TrackingService;
import com.paak.delivery.Model.DeliveryDetail;
import com.paak.delivery.R;

public class DeliveryDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String DELIVERY_DETAIL = "delivery_detail";
    protected static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 100;

    TextView tvName;
    TextView tvAddress;
    TextView tvInstructions;
    Button btnActiveDelivery;
    DeliveryDetail deliveryDetail;
    Intent mServiceIntent;
    private TrackingService mTrackingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_detail_activity);
        init();
        setData();
    }

    private void askForLocationPermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            startTracking();
        }
    }

    private void setData() {
        deliveryDetail = (DeliveryDetail) getIntent().getSerializableExtra(DELIVERY_DETAIL);
        assert deliveryDetail != null;
        tvName.setText(deliveryDetail.getCustomerName());
        tvAddress.setText(deliveryDetail.getAddress());
        tvInstructions.setText(deliveryDetail.getSpecialInstructions());
    }

    private void init() {
        tvName = findViewById(R.id.tv_name);
        tvAddress = findViewById(R.id.tv_address);
        tvInstructions = findViewById(R.id.tv_instructions);
        btnActiveDelivery = findViewById(R.id.btn_active_delivery);
        btnActiveDelivery = findViewById(R.id.btn_active_delivery);
        btnActiveDelivery.setOnClickListener(this);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("Service status", "Running");
                return true;
            }
        }
        Log.i("Service status", "Not running");
        return false;
    }

    @Override
    public void onClick(View v) {
        askForLocationPermission();
    }

    @Override
    protected void onDestroy() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, TrackingBCR.class);
        this.sendBroadcast(broadcastIntent);
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startTracking();
                } else {
                }
                return;
            }
        }
    }

    private void startTracking() {
        mTrackingService = new TrackingService();
        mServiceIntent = new Intent(this, mTrackingService.getClass());
        if (!isMyServiceRunning(mTrackingService.getClass())) {
            startService(mServiceIntent);
        }
    }
}