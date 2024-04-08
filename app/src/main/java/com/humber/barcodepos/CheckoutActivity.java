package com.humber.barcodepos;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.humber.barcodepos.adapter.CheckoutAdapter;
import com.humber.barcodepos.models.Product;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ImageView back = findViewById(R.id.back_icon);
        ListView list = (ListView) findViewById(R.id.list_checkout);
        ArrayList<Product> data = new ArrayList<Product>();
        data.add(new Product(100, "Soap", 100.00, true));
        data.add(new Product(102, "Pen", 2.00, false));
        String TAG = "Sumedh_Debug";
        Log.i(TAG, data.get(0).getName());

        ArrayAdapter<Product> adapter = new CheckoutAdapter(this, R.layout.list_tile, data);
        list.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }


}
