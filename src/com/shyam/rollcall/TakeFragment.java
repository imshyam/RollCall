package com.shyam.rollcall;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

/**
 * Created by shyamsundar on 3/30/2014.
 */
public class TakeFragment extends android.app.Fragment {
    public TakeFragment(){}
    Spinner spinner;
    RadioGroup rg;
    String id;
    TextView text,tid;
    Button b1;
    Toast toast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.take, container, false);

        final Intent intent = new Intent(getActivity(), sqlview.class);
        rg=(RadioGroup)rootView.findViewById(R.id.rg1);
        text=(TextView) rootView.findViewById(R.id.tv);
        tid=(TextView)rootView.findViewById(R.id.tid);
        b1=(Button)rootView.findViewById(R.id.bGo);
        toast=Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb1:
                        id="0";
                        intent.putExtra("id",id);
                        RadioButton rb=(RadioButton)rootView.findViewById(R.id.rb1);
                        String str=rb.getText().toString();
                        tid.setText("By "+str);
                        break;
                    case R.id.rb2:
                        id="1";
                        intent.putExtra("id",id);
                        RadioButton rb1=(RadioButton)rootView.findViewById(R.id.rb2);
                        String str1=rb1.getText().toString();
                        tid.setText("By "+str1);
                        break;
                }
            }
        });

        spinner =(Spinner)rootView.findViewById(R.id.spinner111);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.classes, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final String str = spinner.getItemAtPosition(i).toString();
                text.setText(str);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(TextUtils.isEmpty(tid.getText().toString()))
                        { toast.setText("Choose An Option.");
                            toast.show();}
                        else {
                            intent.putExtra("pas", str);
                            startActivity(intent);}
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootView;
    }
    @Override
    public void onPause() {
        super.onPause();
        toast.cancel();
    }
}
