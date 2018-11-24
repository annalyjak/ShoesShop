package com.example.anna.shoesshop.controller.fragments.requirements;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anna.shoesshop.MainMenuActivity;
import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.model.product.Category;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.product.Type;
import com.example.anna.shoesshop.model.repo.LocalDatabase;

import java.util.List;
import java.util.Objects;

public class ListCategoriesFragment extends Fragment {

    public static final int WOMAN_LIST = 1;
    public static final int MAN_LIST = 2;
    public static final int KID_LIST = 3;
    private int listVersion;
    private TextView textView1, textView2, textView3;

    CardView shoesView, clothesView, accessoriesView;

    public ListCategoriesFragment() {
        // Required empty public constructor
    }

    public static ListCategoriesFragment newInstance(int listVersion) {
        ListCategoriesFragment fragment = new ListCategoriesFragment();
        fragment.listVersion = listVersion;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_categories, container, false);
        shoesView = view.findViewById(R.id.card_shoes);
        clothesView = view.findViewById(R.id.card_clothes);
        accessoriesView = view.findViewById(R.id.card_accessories);
        textView1 = view.findViewById(R.id.textView1);
        textView2 = view.findViewById(R.id.textView2);
        textView3 = view.findViewById(R.id.textView3);
        //TODO change images

        setTextViewText();
        setListeners();

        return view;
    }

    private void setListeners() {
        LocalDatabase localDatabase = new LocalDatabase(getContext());
        switch (listVersion) {
            case WOMAN_LIST: {
                shoesView.setOnClickListener(view1 -> {
                    List<Product> products =
                            localDatabase.getAllProductsForCategory(Type.women, Category.shoes);
                    ((MainMenuActivity) Objects.requireNonNull(getActivity()))
                            .setFragment(CategoriesFragment.newInstance(products));
                });
                clothesView.setOnClickListener(view2 -> {
                    List<Product> products =
                            localDatabase.getAllProductsForCategory(Type.women, Category.clothes);
                    ((MainMenuActivity) Objects.requireNonNull(getActivity()))
                            .setFragment(CategoriesFragment.newInstance(products));
                });
                accessoriesView.setOnClickListener(view3 -> {
                    List<Product> products =
                            localDatabase.getAllProductsForCategory(Type.women, Category.accessories);
                    ((MainMenuActivity) Objects.requireNonNull(getActivity()))
                            .setFragment(CategoriesFragment.newInstance(products));
                });
                break;
            }
            case MAN_LIST: {
                shoesView.setOnClickListener(view1 -> {
                    List<Product> products =
                            localDatabase.getAllProductsForCategory(Type.men, Category.shoes);
                    ((MainMenuActivity) Objects.requireNonNull(getActivity()))
                            .setFragment(CategoriesFragment.newInstance(products));
                });
                clothesView.setOnClickListener(view2 -> {
                    List<Product> products =
                            localDatabase.getAllProductsForCategory(Type.men, Category.clothes);
                    ((MainMenuActivity) Objects.requireNonNull(getActivity()))
                            .setFragment(CategoriesFragment.newInstance(products));
                });
                accessoriesView.setOnClickListener(view3 -> {
                    List<Product> products =
                            localDatabase.getAllProductsForCategory(Type.men, Category.accessories);
                    ((MainMenuActivity) Objects.requireNonNull(getActivity()))
                            .setFragment(CategoriesFragment.newInstance(products));
                });
                break;
            }
            case KID_LIST: {
                shoesView.setOnClickListener(view1 -> {
                    List<Product> products =
                            localDatabase.getAllProductsForCategory(Type.kid, Category.shoes);
                    ((MainMenuActivity) Objects.requireNonNull(getActivity()))
                            .setFragment(CategoriesFragment.newInstance(products));
                });
                clothesView.setOnClickListener(view2 -> {
                    List<Product> products =
                            localDatabase.getAllProductsForCategory(Type.kid, Category.clothes);
                    ((MainMenuActivity) Objects.requireNonNull(getActivity()))
                            .setFragment(CategoriesFragment.newInstance(products));
                });
                accessoriesView.setOnClickListener(view3 -> {
                    List<Product> products =
                            localDatabase.getAllProductsForCategory(Type.kid, Category.accessories);
                    ((MainMenuActivity) Objects.requireNonNull(getActivity()))
                            .setFragment(CategoriesFragment.newInstance(products));
                });
                break;
            }
        }
    }

    private void setTextViewText() {
        switch (listVersion) {
            case WOMAN_LIST: {
                textView1.setText(R.string.woman_shoes);
                textView2.setText(R.string.woman_clothes);
                textView3.setText(R.string.woman_accessories);
                break;
            }
            case MAN_LIST: {
                textView1.setText(R.string.man_shoes);
                textView2.setText(R.string.man_clothes);
                textView3.setText(R.string.man_accessories);
                break;
            }
            case KID_LIST: {
                textView1.setText(R.string.kid_shoes);
                textView2.setText(R.string.kid_clothes);
                textView3.setText(R.string.kid_accessories);
                break;
            }
        }
    }

}
