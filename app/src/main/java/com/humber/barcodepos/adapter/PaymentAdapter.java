package com.humber.barcodepos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.humber.barcodepos.R;
import com.humber.barcodepos.models.Product;

import java.util.List;



public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {


    public PaymentAdapter(Context context, List<Product> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    Context context;

    List<Product> productsList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_payment_details_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(productsList!=null && productsList.size()>0)
        {
            System.out.println("Product Inside Payment Adapter"+position);
            System.out.println("Product Inside Payment Adapter"+productsList.get(0).getName());
            System.out.println("Product Inside Payment Adapter"+productsList.size());
            Product product=productsList.get(position);
            //holder.sr_no_tv.setText("0");
            if(product.getName().length()>8)
            {
                holder.product_name_tv.setText(product.getName().substring(0,10).concat(" ..."));
            }
            else
            {
                holder.product_name_tv.setText(product.getName());
            }

            holder.product_quantity_tv.setText(String.valueOf(product.getQuantity()));
            holder.product_price_tv.setText(String.format("%.2f",product.getPrice()));
            double total_price=0.0f;
            if(product.getTaxable())
                total_price=product.getPrice()*product.getQuantity()*1.13f;
            else
                total_price=product.getPrice()*product.getQuantity();
            holder.product_total_price_tv.setText(String.format("%.2f",total_price));
        }
        else
        {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sr_no_tv,product_name_tv,product_quantity_tv,product_price_tv,product_total_price_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //sr_no_tv= itemView.findViewById(R.id.sr_no_tv);
            product_name_tv= itemView.findViewById(R.id.product_name_tv);
            product_quantity_tv= itemView.findViewById(R.id.product_quantity_tv);
            product_price_tv= itemView.findViewById(R.id.product_price_tv);
            product_total_price_tv= itemView.findViewById(R.id.product_total_price_tv);

        }
    }
}
