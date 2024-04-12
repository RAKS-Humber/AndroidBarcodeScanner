package com.humber.barcodepos;

import static com.humber.barcodepos.MainActivity.mOrder;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.humber.barcodepos.adapter.CheckoutAdapter;
import com.humber.barcodepos.models.Product;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    public TextView subTotal;
    public TextView taxableTotal;
    public TextView total;
    public ImageView back;
    public ListView list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        back = findViewById(R.id.back_icon);
        list = (ListView) findViewById(R.id.list_checkout);
        subTotal = findViewById(R.id.sub_total);
        taxableTotal = findViewById(R.id.taxable_total);
        total = findViewById(R.id.total);

        ArrayAdapter<Product> adapter = new CheckoutAdapter(this, R.layout.list_tile, mOrder.getOrder());
        list.setAdapter(adapter);

        subTotal.setText(String.valueOf(mOrder.getSubTotal()));
        taxableTotal.setText(String.valueOf(mOrder.getTaxableSubTotal()));
        total.setText(String.valueOf(mOrder.getTotal()));


        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }


}
