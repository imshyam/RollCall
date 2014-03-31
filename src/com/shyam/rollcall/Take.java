package com.shyam.rollcall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static android.R.layout.simple_spinner_item;

/**
 * Created by shyamsundar on 8/15/13.
 */
public class Take extends FragmentActivity {

    Spinner spinner;
    database db;
    Toast toast;
    ArrayAdapter<String> adapter;
    TextView text,tid;
    Button b1;
    RadioGroup rg;
    String id;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take);
        final Intent intent = new Intent(Take.this, sqlview.class);

        toast=Toast.makeText(getApplication(),"",Toast.LENGTH_SHORT);

        db = new database(Take.this);
        text=(TextView) findViewById(R.id.tv);
        tid=(TextView)findViewById(R.id.tid);
        b1=(Button)findViewById(R.id.bGo);
        spinner =(Spinner)findViewById(R.id.spinner111);
        rg=(RadioGroup)findViewById(R.id.rg1);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb1:
                        id="0";
                        intent.putExtra("id",id);
                        RadioButton rb=(RadioButton)findViewById(R.id.rb1);
                        String str=rb.getText().toString();
                        tid.setText("By "+str);
                        break;
                    case R.id.rb2:
                        id="1";
                        intent.putExtra("id",id);
                        RadioButton rb1=(RadioButton)findViewById(R.id.rb2);
                        String str1=rb1.getText().toString();
                        tid.setText("By "+str1);
                        break;
                }
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
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




    }
    @Override
    public void onPause() {
        super.onPause();
        toast.cancel();
        }

}
