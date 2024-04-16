package com.humber.barcodepos;


import static com.humber.barcodepos.MainActivity.mOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.humber.barcodepos.adapter.PaymentAdapter;
import com.humber.barcodepos.models.Product;

import java.io.Serializable;

import java.util.ArrayList;

import java.util.List;

public class ViewPaymentDetailsActivity extends AppCompatActivity {


    RecyclerView products_recycler_view;

    PaymentAdapter paymentAdapter;

    Button proceedToPaymentBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_view_payment_details);
        products_recycler_view=findViewById(R.id.products_recycler_view);





        proceedToPaymentBtn =findViewById(R.id.btn_checkout);

        proceedToPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ViewPaymentDetailsActivity.this, CheckoutActivity.class);
                List<Product> products=MainActivity.mOrder.getOrder();
                intent.putExtra("productList", (Serializable) products);
                startActivity(intent);




            }
        });

        setRecyclerView();
    }

    private void setRecyclerView()
    {
        products_recycler_view.setHasFixedSize(true);
        products_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        System.out.println("-------------Inside View Payment Details---------------");

        //Bundle extra = getIntent().getBundleExtra("products");
        ArrayList<Product> products = (ArrayList<Product>) getIntent().getSerializableExtra("productList");
        System.out.println("-------------Inside View Payment Details---------------"+products.size());
        paymentAdapter=new PaymentAdapter(this,products);
        products_recycler_view.setAdapter(paymentAdapter);
    }



}