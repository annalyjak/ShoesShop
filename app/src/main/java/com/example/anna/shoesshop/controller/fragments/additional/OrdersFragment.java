package com.example.anna.shoesshop.controller.fragments.additional;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.controller.adapters.OrdersCardAdapter;
import com.example.anna.shoesshop.model.order.Order;
import com.example.anna.shoesshop.model.repo.LocalDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrdersFragment extends Fragment {

    private List<Order> orders;
    private RecyclerView.Adapter adapter;

    public OrdersFragment() {
        // Required empty public constructor
    }

    public static OrdersFragment newInstance() {
        OrdersFragment fragment = new OrdersFragment();
        fragment.orders = new ArrayList<>();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewOrders);
        adapter = new OrdersCardAdapter(orders);

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        if(orders.isEmpty()){
            getListOfAllOrders();
        }
        return view;
    }

    private void getListOfAllOrders() {
        LocalDatabase localDatabase = new LocalDatabase(
                Objects.requireNonNull(getActivity()).getApplicationContext());
        orders = localDatabase.getAllClientOrders();
        Log.i("TAG", "Liczba elementów w bazie: " + orders.size());
        changeViewUsingAdapter();
    }

    private void changeViewUsingAdapter() {
        for(Order order : orders) {
            ((OrdersCardAdapter) adapter).addNewOrder(order);
        }
        adapter.notifyDataSetChanged();
    }

}
