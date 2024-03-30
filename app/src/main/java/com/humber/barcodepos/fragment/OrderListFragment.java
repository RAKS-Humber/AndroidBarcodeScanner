package com.humber.barcodepos.fragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.humber.barcodepos.MainActivity;
import com.humber.barcodepos.R;
import com.humber.barcodepos.adapter.OrderAdapter;
import com.humber.barcodepos.models.Product;
import com.humber.barcodepos.models.Order;

import java.util.List;
import java.util.UUID;

public class OrderListFragment extends Fragment{
    private RecyclerView mOrderRecyclerView;
    private OrderAdapter mAdapter;
   public OrderListFragment(){}

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public static OrderListFragment newInstance (){
        OrderListFragment fragment = new OrderListFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);
        mOrderRecyclerView = (RecyclerView) view
                .findViewById(R.id.order_recycler_view);
        mOrderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateUI(){
        List<Product> mOrder = MainActivity.mOrder;
        if (mAdapter == null) {
            mAdapter = new OrderAdapter(getActivity(), mOrder);
            mOrderRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }
}
