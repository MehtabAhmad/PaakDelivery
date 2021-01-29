package com.paak.delivery.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.paak.delivery.Model.Delivery;
import com.paak.delivery.Model.DeliveryDetail;
import com.paak.delivery.R;
import com.paak.delivery.View.Adapters.DeliveryAdapter;
import com.paak.delivery.View.Adapters.ItemClickListener;
import com.paak.delivery.ViewModel.DeliveryViewModel;

import java.util.List;

import static com.paak.delivery.View.Activities.DeliveryDetailActivity.DELIVERY_DETAIL;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    RecyclerView deliveryRV;
    DeliveryAdapter deliveryAdapter;
    DeliveryViewModel deliveryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        addDeliveryListObserver();
        addDeliveryDetailObserver();
    }

    private void init() {
        deliveryRV = findViewById(R.id.rv_delivery);
        deliveryAdapter = new DeliveryAdapter(this);
        deliveryRV.setLayoutManager(new LinearLayoutManager(this));
        deliveryRV.setAdapter(deliveryAdapter);

        deliveryViewModel = new ViewModelProvider(this).get(DeliveryViewModel.class);
        deliveryViewModel.init();
        deliveryViewModel.getDeliveries();

    }

    private void addDeliveryListObserver() {
        deliveryViewModel.getDeliveriesList().observe(this, deliveries -> {
            if (deliveries != null) {
                deliveryAdapter.setResults(deliveries);
            }
        });
    }

    private void addDeliveryDetailObserver() {
        deliveryViewModel.getDeliveryDetail().observe(this, delivery -> {
            if (delivery != null) {
                Intent detailIntent = new Intent(MainActivity.this, DeliveryDetailActivity.class);
                detailIntent.putExtra(DELIVERY_DETAIL, delivery);
                startActivity(detailIntent);
            }
        });
    }

    @Override
    public void onItemClicked(Delivery delivery) {
        deliveryViewModel.getDelivery(delivery.getId());
    }
}