package com.shyam.rollcall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by shyamsundar on 3/30/2014.
 */
public class ExportFragment extends android.app.Fragment {
    public ExportFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.expor, container, false);

        return rootView;
    }
}
