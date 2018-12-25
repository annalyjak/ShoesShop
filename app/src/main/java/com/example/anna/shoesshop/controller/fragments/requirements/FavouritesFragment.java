package com.example.anna.shoesshop.controller.fragments.requirements;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anna.shoesshop.MainMenuActivity;
import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.controller.adapters.FavouritesCardAdapter;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.repo.LocalDatabase;

import java.util.List;

public class FavouritesFragment extends Fragment {

    private List<Product> favProducts;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private View layoutNoRav;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    public static FavouritesFragment newInstance(List<Product> products) {
        FavouritesFragment fragment = new FavouritesFragment();
        fragment.favProducts = products;
        if(products != null) {
            Log.i("TAG", "Przekazano listę produktów: " + products.size());
        }
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
        // creating list
        recyclerView = view.findViewById(R.id.order_recycler_view);
        adapter = new FavouritesCardAdapter((MainMenuActivity) getActivity(), favProducts, this);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        layoutNoRav = view.findViewById(R.id.no_fav_prod);
        setFavView();

        if (favProducts == null && favProducts.isEmpty()) {
            getProductsFromDatabase();
        }
        return view;
    }

    private void getProductsFromDatabase() {
        LocalDatabase localDatabase = new LocalDatabase(getActivity().getApplicationContext());

        favProducts = localDatabase.getAllFavouritesProducts(null);
//        favProducts = (localDatabase.getAllProducts());
        if(favProducts != null) {
            Log.i("TAG", "SIZE : " + favProducts.size());
            for(Product product : favProducts) {
                ((FavouritesCardAdapter) adapter).addNewProducts(product);
            }

            adapter.notifyDataSetChanged();
            setFavView();
        }
    }

    public void setNoProducts() {
        layoutNoRav.setVisibility(View.VISIBLE);
    }

    private void setFavView() {
        if(favProducts != null && !favProducts.isEmpty()) {
            layoutNoRav.setVisibility(View.INVISIBLE);
        }
    }

}
