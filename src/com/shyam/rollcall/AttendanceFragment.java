package com.shyam.rollcall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by shyamsundar on 3/30/2014.
 */
public class AttendanceFragment extends android.app.Fragment {
    public AttendanceFragment(){}
    Spinner spinner,spinner1;
    database db;
    dbAttendance da;
    ArrayAdapter<String> adapter1;
    Button b;
    TextView textView,textView1,textView2,textView6,textView55;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.show, container, false);
        spinner=(Spinner)rootView.findViewById(R.id.spinner);
        spinner1=(Spinner)rootView.findViewById(R.id.spinner2);
        textView=(TextView)rootView.findViewById(R.id.textView4);
        textView1=(TextView)rootView.findViewById(R.id.textView5);
        textView2=(TextView)rootView.findViewById(R.id.textView3);
        textView6=(TextView)rootView.findViewById(R.id.textView6);
        textView55=(TextView)rootView.findViewById(R.id.textView55);
        db=new database(getActivity());
        da=new dbAttendance(getActivity());

        b=(Button)rootView.findViewById(R.id.bde);


        loadSpinner();
        return rootView;
    }
    private void loadSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.classes, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                final String str=spinner.getItemAtPosition(i).toString();

                try {
                    db.open();
                    List<String> list1 = db.getEnr1(str);
                    db.close();

                    if(list1.size()>0){
                        Collections.sort(list1, new Comparator<String>() {
                            @Override
                            public int compare(String s, String s2) {

                                return s.compareTo(s2);
                            }
                        });

                        adapter1 = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, list1);

                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinner1.setAdapter(adapter1);
                        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, final long l) {
                                String str1=spinner1.getItemAtPosition(i).toString();

                                textView.setText(str1);
                                textView1.setText("");
                                textView6.setText("");
                                textView55.setText("Your Attendance is :");
                                textView2.setText("Details Are : ");
                                textView2.setVisibility(View.INVISIBLE);
                                final long enr=Long.parseLong(str1);
                                b.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            db.open();
                                            String name=db.getname(str,enr);
                                            db.close();
                                            textView2.setVisibility(View.VISIBLE);
                                            textView1.setText(name);
                                            da.open();
                                            String att=da.getAtten(str,enr);
                                            String total=da.getTotal(str,enr);
                                            textView6.setText("present in "+att+" out of "+total);
                                            da.close();
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });}
//if class is empty
                    else{

                        adapter1 = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, list1);

                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinner1.setAdapter(adapter1);//setting adapter

                        textView.setText("");
                        textView1.setText("");
                        textView6.setText("");
                        textView55.setText("");
                        textView2.setText("");
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                textView2.setText("Empty Class");
                                textView2.setVisibility(View.VISIBLE);

                            }
                        });

                    }
                    // spinner.setWillNotDraw(false);
                } catch (SQLException e) {
                    e.printStackTrace();
                    String s = e.toString();

                    Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // spinner.setWillNotDraw(false);


    }
}
