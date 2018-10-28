package com.example.anna.shoesshop.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.controller.adapters.CategoriesCardAdapter;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.repo.LocalDatabase;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {


    private List<Product> products;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        // creating list
        recyclerView = view.findViewById(R.id.categ_recyclerView);

//                Log.i("", mRecyclerView == null? "null" : mRecyclerView.toString());
        if(products == null) {
            products = new ArrayList<>();
        }
        adapter = new CategoriesCardAdapter(getActivity(), products);

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        if(products.isEmpty()){
            getListOfAllProducts();
        }

        return view;
    }

    private void getListOfAllProducts() {

        LocalDatabase localDatabase = new LocalDatabase(getActivity().getApplicationContext());
        products = localDatabase.getAllProducts();

        Log.i("TAG", "SIZE : " + products.size());

        for(Product product : products) {
            ((CategoriesCardAdapter) adapter).addNewProducts(product);
        }
        adapter.notifyDataSetChanged();
    }

}
