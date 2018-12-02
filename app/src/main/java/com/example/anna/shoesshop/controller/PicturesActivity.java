package com.example.anna.shoesshop.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.model.repo.DBUtil;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class PicturesActivity extends AppCompatActivity {

    public static List<byte[]> pictures = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), pictures);

        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        WormDotsIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private byte[] picture;

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(byte[] picture) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.picture = picture;
            return fragment;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_pictures, container, false);
            ImageView imageView = rootView.findViewById(R.id.imageViewBig);
            imageView.setImageBitmap(DBUtil.transferToBigBitmap(picture));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        List<byte[]> list;

        SectionsPagerAdapter(FragmentManager fm, List<byte[]> pictures) {
            super(fm);
            list = pictures;
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(list.get(position));
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
