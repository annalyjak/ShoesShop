package com.example.anna.shoesshop.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.model.product.Product;

import java.util.List;

public class FavouritesFragment extends Fragment {

    List<Product> favProducts;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    public static FavouritesFragment newInstance(List<Product> products) {
        FavouritesFragment fragment = new FavouritesFragment();
        fragment.favProducts = products;
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
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        if(favProducts != null) {
            if(!favProducts.isEmpty()){
                View layout = view.findViewById(R.id.no_fav_prod);
                layout.setVisibility(View.INVISIBLE);
            }
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
