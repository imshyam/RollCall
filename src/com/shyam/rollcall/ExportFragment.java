package com.shyam.rollcall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by shyamsundar on 3/30/2014.
 */
public class ExportFragment extends android.app.Fragment {
    public ExportFragment(){}
    Button b1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.expor, container, false);
        b1=(Button)rootView.findViewById(R.id.bex);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rootView;
    }
}
