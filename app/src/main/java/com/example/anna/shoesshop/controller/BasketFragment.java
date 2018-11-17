package com.example.anna.shoesshop.controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.model.product.Product;

import java.util.List;


public class BasketFragment extends Fragment {

    private List<Product> orderedProducts;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private View layoutNoRav;

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
//        view.findViewById()

        return view;
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
