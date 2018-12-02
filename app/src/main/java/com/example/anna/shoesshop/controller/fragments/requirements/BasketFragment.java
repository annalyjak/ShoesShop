package com.example.anna.shoesshop.controller.fragments.requirements;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.anna.shoesshop.MainMenuActivity;
import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.controller.adapters.BasketCardAdapter;
import com.example.anna.shoesshop.model.Price;
import com.example.anna.shoesshop.model.order.Delivery;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.repo.DBHelper;
import com.example.anna.shoesshop.model.repo.DialogUtil;
import com.example.anna.shoesshop.model.repo.LocalDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BasketFragment extends Fragment {

    private List<Product> orderedProducts;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
//    private View layoutNoRav;
    private Spinner deliverySpinner;
    private Button buttonBuy;
    private TextView priceTextView;

    private ArrayList<Delivery> deliveryList;

//    private OnFragmentInteractionListener mListener;

    public BasketFragment() {
        // Required empty public constructor
    }

    public static BasketFragment newInstance(List<Product> orderedProducts) {
        BasketFragment fragment = new BasketFragment();
        fragment.orderedProducts = orderedProducts;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(orderedProducts == null || orderedProducts.isEmpty()) {
            return inflater.inflate(R.layout.fragment_basket_empty, container, false);
        }

        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        recyclerView = view.findViewById(R.id.order_recycler_view);
        adapter = new BasketCardAdapter(getActivity(), orderedProducts, this);

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        deliverySpinner = view.findViewById(R.id.spinner);
        setDeliverySpinner();
        priceTextView = view.findViewById(R.id.textViewPrice);
        setPriceTextView();

        setDeliveryListener();

        buttonBuy = view.findViewById(R.id.buttonBuy);
        buttonBuy.setOnClickListener(view1 -> {
            createNewOrder();
            displayInfo();
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    clearSession();
                    return null;
                }
            }.execute();

        });

        return view;
    }

    public void setPriceTextView() {
        double temp = getSelectedDelivery().getPriceOfDelivery().getActuallPrice();
        for(Product p: orderedProducts) {
            temp+= p.getPrice().getActuallPrice();
        }
        priceTextView.setText(new Price(temp, "ZŁ").toString());
    }

    private Delivery getSelectedDelivery() {
        return deliveryList.get(deliverySpinner.getSelectedItemPosition());
    }

    private void displayInfo() {
        DialogUtil.createDialog(getActivity(), "ZAKUP", "Zamówienie zostało złożone");
    }

    private void clearSession() {
        MainMenuActivity.getSession().clearOrder();
        Log.i("TAG", "Session cleard");
        ((MainMenuActivity) Objects.requireNonNull(getActivity())).getPreviousFragment();
    }

    private void createNewOrder() {
        LocalDatabase localDatabase = new LocalDatabase(getContext());
        localDatabase.saveNewOrder(orderedProducts, getSelectedDelivery());
    }

    private void setDeliverySpinner() {
        ArrayList<String> delivery = new ArrayList<>();
        deliveryList = DBHelper.getDeliveryList();
        for (Delivery delivery1 : deliveryList) {
            delivery.add(delivery1.getDeliveryFirmName() + " - " + delivery1.getPriceOfDelivery());
        }
        ArrayAdapter<String> deliveryArrayAdapter = new ArrayAdapter<>(
                Objects.requireNonNull(getContext()),
                android.R.layout.simple_list_item_1,
                delivery);
        deliverySpinner.setAdapter(deliveryArrayAdapter);
    }

    private void setDeliveryListener() {
        deliverySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setPriceTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                setPriceTextView();
            }
        });

    }

    public void setNoProducts() {
        ((MainMenuActivity) Objects.requireNonNull(getActivity())).setBasketView();
    }

}
