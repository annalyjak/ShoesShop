package com.example.anna.shoesshop.controller.fragments.additional;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anna.shoesshop.MainMenuActivity;
import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.model.userInfo.Account;

public class AccountInfoFragment extends Fragment {

    Account account;

    public AccountInfoFragment() {
        // Required empty public constructor
        account = MainMenuActivity.getSession().getAccount();
    }

    public static AccountInfoFragment newInstance() {
        return new AccountInfoFragment();
    }

    public static AccountInfoFragment newInstance(Account account) {
        AccountInfoFragment fragment = new AccountInfoFragment();
        fragment.account = account;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_info, container, false);
        TextView accountName = view.findViewById(R.id.textViewName);
        accountName.setText(account.getEmail());
        return view;
    }

}
