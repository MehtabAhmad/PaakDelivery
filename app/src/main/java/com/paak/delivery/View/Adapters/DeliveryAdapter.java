package com.paak.delivery.View.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.paak.delivery.Model.Delivery;
import com.paak.delivery.R;

import java.util.ArrayList;
import java.util.List;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.DeliveryHolder> {
    private List<Delivery> results = new ArrayList<>();
    private ItemClickListener clickListener;

    public DeliveryAdapter(ItemClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public DeliveryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delivery_list_item, parent, false);

        return new DeliveryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryHolder holder, int position) {
        Delivery delivery = results.get(position);
        holder.tvCustomerName.setText(delivery.getCustomerName());
        holder.tvAddress.setText(delivery.getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClicked(results.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Delivery> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    static class DeliveryHolder extends RecyclerView.ViewHolder {
        private final TextView tvCustomerName;
        private final TextView tvAddress;

        public DeliveryHolder(@NonNull View itemView) {
            super(itemView);
            tvCustomerName = itemView.findViewById(R.id.tv_cus_name);
            tvAddress = itemView.findViewById(R.id.tv_address);
        }
    }
}
