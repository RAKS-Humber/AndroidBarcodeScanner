package com.humber.barcodepos.viewholders;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.humber.barcodepos.R;
import com.humber.barcodepos.models.Order;
import com.humber.barcodepos.models.Product;

import org.w3c.dom.Text;


public class ProductHolder extends RecyclerView.ViewHolder{

    private Product mProduct;

    private TextView mProductName;
    private TextView mBarcode;
    private TextView mProductPrice;
    private TextView mQuantity;



    public interface InteractionListener {
        void onQuantityChanged(int position, int newQuantity);
    }

    private InteractionListener interactionListener;

    ImageButton addQuantity;

    ImageButton removeQuantity;

    private Context context;

    public static final String EXTRA_CRIME_ID = "crime_id";

    int quantity=0;

    public ProductHolder(View itemView, Context context,InteractionListener interactionListener) {
        super(itemView);
        this.context = context;
        this.interactionListener = interactionListener;
        mQuantity = (TextView) itemView.findViewById(R.id.list_order_quantity_text_view);


        mProductName = (TextView)
                itemView.findViewById(R.id.list_order_name_text_view);
        mProductPrice = (TextView)
                itemView.findViewById(R.id.list_order_price_text_view);
        mBarcode = (TextView)
                itemView.findViewById(R.id.list_order_barcode_text_view);
        addQuantity=(ImageButton)itemView.findViewById(R.id.add_quantity);

        addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                System.out.println(mQuantity+"qunatitytttttttt");
                mQuantity.setText(String.valueOf(quantity));
                if (interactionListener != null) {
                    interactionListener.onQuantityChanged(getAdapterPosition(), quantity);
                }
            }
        });

        removeQuantity=(ImageButton)itemView.findViewById(R.id.remove_quantity);

        removeQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity>=1)
                {
                    quantity--;
                }


                mQuantity.setText(String.valueOf(quantity));
                if (interactionListener != null) {
                    interactionListener.onQuantityChanged(getAdapterPosition(), quantity);
                }
            }
        });



//        itemView.setOnClickListener(itemClickListener);
    }

    public void bindProduct(Product product) {
        mProduct = product;
        mProductName.setText(mProduct.getName());
        mProductPrice.setText(Double.toString(mProduct.getPrice()));
        quantity=mProduct.getQuantity();
        mQuantity.setText(String.valueOf(mProduct.getQuantity()));
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
