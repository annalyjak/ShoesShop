package com.example.anna.shoesshop.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anna.shoesshop.R;

import java.util.Objects;

public class FAQFragment extends Fragment {

    private ViewGroup transitionsContainer = null;
    private TextView andswer1 = null;
    private TextView andswer2 = null;
    private TextView andswer3 = null;
    private TextView andswer4 = null;
    private boolean visibilityAndswer1 = false;
    private boolean visibilityAndswer2 = false;
    private boolean visibilityAndswer3 = false;
    private boolean visibilityAndswer4 = false;

    public FAQFragment() {
        // Required empty public constructor
    }

    public static FAQFragment newInstance() {
        return new FAQFragment();
    }

    private void setTransitionsContainer(ViewGroup viewById) {
        transitionsContainer = viewById;
        andswer1 = Objects.requireNonNull(transitionsContainer).findViewById(R.id.textViewA1);
        andswer2 = Objects.requireNonNull(transitionsContainer).findViewById(R.id.textViewA2);
        andswer3 = Objects.requireNonNull(transitionsContainer).findViewById(R.id.textViewA3);
        andswer4 = Objects.requireNonNull(transitionsContainer).findViewById(R.id.textViewA4);
        andswer1.setVisibility(visibilityAndswer1? View.VISIBLE : View.GONE);
        andswer2.setVisibility(visibilityAndswer2? View.VISIBLE : View.GONE);
        andswer3.setVisibility(visibilityAndswer3? View.VISIBLE : View.GONE);
        andswer4.setVisibility(visibilityAndswer4? View.VISIBLE : View.GONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_faq, container, false);
        setTransitionsContainer((ViewGroup) inflate.findViewById(R.id.transitions_container));
        return inflate;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void showAndswer1() {
        TransitionManager.beginDelayedTransition(transitionsContainer);
        visibilityAndswer1 = !visibilityAndswer1;
        andswer1.setVisibility(visibilityAndswer1? View.VISIBLE : View.GONE);
    }

    public void showAndswer2() {
        TransitionManager.beginDelayedTransition(transitionsContainer);
        visibilityAndswer2 = !visibilityAndswer2;
        andswer2.setVisibility(visibilityAndswer2? View.VISIBLE : View.GONE);
    }

    public void showAndswer3() {
        TransitionManager.beginDelayedTransition(transitionsContainer);
        visibilityAndswer3 = !visibilityAndswer3;
        andswer3.setVisibility(visibilityAndswer3? View.VISIBLE : View.GONE);
    }

    public void showAndswer4() {
        TransitionManager.beginDelayedTransition(transitionsContainer);
        visibilityAndswer4 = !visibilityAndswer4;
        andswer4.setVisibility(visibilityAndswer4? View.VISIBLE : View.GONE);
    }
}
