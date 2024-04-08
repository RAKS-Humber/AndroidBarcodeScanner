package com.humber.barcodepos.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.humber.barcodepos.R;
import com.humber.barcodepos.models.Order;
import com.humber.barcodepos.models.Product;


public class ProductHolder extends RecyclerView.ViewHolder{

    private Product mProduct;

    private TextView mProductName;
    private TextView mBarcode;
    private TextView mProductPrice;

    private Context context;

    public static final String EXTRA_CRIME_ID = "crime_id";

    public ProductHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        mProductName = (TextView)
                itemView.findViewById(R.id.list_order_name_text_view);
        mProductPrice = (TextView)
                itemView.findViewById(R.id.list_order_price_text_view);
        mBarcode = (TextView)
                itemView.findViewById(R.id.list_order_barcode_text_view);
//        itemView.setOnClickListener(itemClickListener);
    }

    public void bindProduct(Product product) {
        mProduct = product;
        mProductName.setText(mProduct.getName());
        mProductPrice.setText(Double.toString(mProduct.getPrice()));
        //mBarcode.setText(mProduct.getBarcode() );
        //mProductPrice.setText(mProduct.getBarcode());
    }

//    private final View.OnClickListener itemClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(context, MainActivity.class);
//            intent.putExtra(EXTRA_CRIME_ID, mOrder.);
//            context.startActivity(intent);
//        }
//    };
}
