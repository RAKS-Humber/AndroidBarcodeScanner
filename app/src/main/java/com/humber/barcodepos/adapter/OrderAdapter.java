package com.humber.barcodepos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.humber.barcodepos.R;
import com.humber.barcodepos.models.Product;
import com.humber.barcodepos.viewholders.ProductHolder;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<ProductHolder> {

    private List<Product> mOrder;
    private Context context;

    public OrderAdapter (Context context, List<Product> orders){
        this.mOrder = orders;
        this.context = context;
    }

    public void setOrders(List<Product> order){
        mOrder = order;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater
                    .inflate(R.layout.list_order, parent, false);
            return new ProductHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
                Product product = mOrder.get(position);
                holder.bindProduct(product);
    }

    @Override
    public int getItemCount()
    {
        System.out.println("PRoduct Count"+mOrder.size());
            return mOrder.size();
    }
}
