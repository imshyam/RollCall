package com.shyam.rollcall;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Created by shyamsundar on 8/13/13.
 */
public class show extends Activity {

    Spinner spinner,spinner1;
    database db;
    ArrayAdapter<String> adapter1;
    Button b;
    TextView textView,textView1,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        spinner=(Spinner)findViewById(R.id.spinner);
        spinner1=(Spinner)findViewById(R.id.spinner2);
        textView=(TextView)findViewById(R.id.textView4);
        textView1=(TextView)findViewById(R.id.textView5);
        textView2=(TextView)findViewById(R.id.textView3);
        db=new database(show.this);

        b=(Button)findViewById(R.id.bde);


        loadSpinner();
    }

    private void loadSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
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

                        Collections.sort(list1,new Comparator<String>() {
                            @Override
                            public int compare(String s, String s2) {

                                return s.compareTo(s2);
                            }
                        });

                        adapter1 = new ArrayAdapter<String>(show.this,
                                android.R.layout.simple_spinner_item, list1);

                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinner1.setAdapter(adapter1);
                        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, final long l) {
                                String str1=spinner1.getItemAtPosition(i).toString();
                                textView.setText(str1);
                                textView1.setText("");
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
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        // spinner.setWillNotDraw(false);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        String s = e.toString();

                        Toast.makeText(show.this,s,Toast.LENGTH_SHORT).show();
                    }



                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            // spinner.setWillNotDraw(false);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}