package com.example.neo.mymmorpg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by neo on 2/25/2018.
 */

public class FragmentC extends Fragment {
    // Store instance variables

    // newInstance constructor for creating fragment with arguments
    public static FragmentC newInstance() {
        FragmentC fragmentFirst = new FragmentC();
        Bundle args = new Bundle();
        fragmentFirst.setArguments(args);

        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bag, container, false);

        return v;
    }
}