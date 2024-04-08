package com.humber.barcodepos.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.humber.barcodepos.R;
import com.humber.barcodepos.models.Product;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CheckoutAdapter extends ArrayAdapter<Product> {

    Context context;
    int layoutResourceId;
    List<Product> data;

    public CheckoutAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        this.layoutResourceId = resource;
        this.context = context;
        this.data = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ProductHolder holder;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ProductHolder();
            holder.name = row.findViewById(R.id.textview_name);
            holder.price = row.findViewById(R.id.textview_price);
            holder.quantity = row.findViewById(R.id.textview_qty);
            holder.barcode = row.findViewById(R.id.textview_barcode);

            row.setTag(holder);
        } else {
            holder = (ProductHolder) row.getTag();
        }

        Product item = data.get(position);
        String productName = context.getString(R.string.product, item.getName());
        holder.name.setText(productName);
        holder.barcode.setText(String.valueOf(item.getBarcode()));
        String dollarPrice = context.getString(R.string.dollar, item.getPrice());
        holder.price.setText(dollarPrice); // assuming getPrice() returns a float or double

        return row;
    }

    private static class ProductHolder {
        TextView barcode;
        TextView name;
        TextView price;
        TextView quantity;
    }
}
