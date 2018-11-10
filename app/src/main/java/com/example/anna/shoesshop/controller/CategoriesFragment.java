package com.example.anna.shoesshop.controller;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.controller.adapters.CategoriesCardAdapter;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.repo.LocalDatabase;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {


    public static final int GRID_VIEW = 1;
    public static final int LIST_VIEW = 2;

    private int viewType = GRID_VIEW;
    private List<Product> products;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        fragment.viewType = GRID_VIEW;
        return fragment;
    }

    public static CategoriesFragment newInstance(List<Product> products) {
        CategoriesFragment fragment = new CategoriesFragment();
        fragment.viewType = GRID_VIEW;
        fragment.products = products;
        return fragment;
    }

    public static CategoriesFragment newInstance(int viewType) {
        CategoriesFragment fragment = new CategoriesFragment();
        fragment.viewType = viewType;
        return fragment;
    }

    public static CategoriesFragment newInstance(int viewType, List<Product> products) {
        CategoriesFragment fragment = new CategoriesFragment();
        fragment.viewType = viewType;
        fragment.products = products;
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

        if(products == null) {
            products = new ArrayList<>();
        }

        recyclerView = view.findViewById(R.id.categ_recyclerView);
        ImageView image = view.findViewById(R.id.imageViewChangeGrid);
        if(viewType == GRID_VIEW) {
            image.setImageResource(R.drawable.baseline_list_24);
            image.setOnClickListener(view12 ->{
                        ProgressDialog progressDialog = ProgressDialog.show(getActivity(),
                                "Proszę chwilę poczekać ...",  "Ładuję listę produktów ...", true);
                        new Thread(() -> {
                            try {
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.main_frame, newInstance(LIST_VIEW, products))
                                        .commit();
                            } catch (Exception e) {
                                Log.e("error: ", e.getMessage());
                            }
                            progressDialog.dismiss();
                        }).start();
                    }
                   );

            adapter = new CategoriesCardAdapter(getActivity(), products, CategoriesCardAdapter.CARD_CATEGORIES_2);
            final GridLayoutManager gridManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(gridManager);

        } else {
            image.setImageResource(R.drawable.baseline_grid_on_24);

            image.setOnClickListener(view1 ->  {
                ProgressDialog progressDialog = ProgressDialog.show(getActivity(),
                        "Proszę chwilę poczekać ...",  "Ładuję listę produktów ...", true);
                new Thread(() -> {
                    try {
                        getFragmentManager().beginTransaction()
                                .replace(R.id.main_frame, newInstance(GRID_VIEW, products))
                                .commit();
                    } catch (Exception e) {
                        Log.e("error: ", e.getMessage());
                    }
                    progressDialog.dismiss();
                }).start();

            });

            adapter = new CategoriesCardAdapter(getActivity(), products, CategoriesCardAdapter.CARD_CATEGORIES_1);
            final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
        }

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
