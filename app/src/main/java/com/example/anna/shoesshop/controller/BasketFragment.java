package com.example.anna.shoesshop.controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.anna.shoesshop.MainMenuActivity;
import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.controller.adapters.BasketCardAdapter;
import com.example.anna.shoesshop.model.order.Delivery;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.repo.DBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BasketFragment extends Fragment {

    private List<Product> orderedProducts;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private View layoutNoRav;
    private Spinner deliverySpinner;

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

        return view;
    }

    private void setDeliverySpinner() {
        ArrayList<String> delivery = new ArrayList<>();
        ArrayList<Delivery> deliveryList = DBHelper.getDeliveryList();
        for (Delivery delivery1 : deliveryList) {
            delivery.add(delivery1.getDeliveryFirmName() + " - " + delivery1.getPriceOfDelivery());
        }
        ArrayAdapter<String> deliveryArrayAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                delivery);
        deliverySpinner.setAdapter(deliveryArrayAdapter);
    }

    public void setNoProducts() {
        ((MainMenuActivity) Objects.requireNonNull(getActivity())).setBasketView();
    }

//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        void onFragmentInteraction(Uri uri);
//    }
}
