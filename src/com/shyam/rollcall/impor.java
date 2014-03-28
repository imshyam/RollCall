package com.shyam.rollcall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

/**
 * Created by shyamsundar on 3/2/14.
 */
public class impor extends Activity{
    Button b;
    Spinner spinner;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.impor);
        b=(Button)findViewById(R.id.button);
        spinner=(Spinner)findViewById(R.id.spinner);
        tv=(TextView)findViewById(R.id.textView);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shcla =new Intent(impor.this,GSSAct.class);
                startActivity(shcla);
            }
        });
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
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                final String tablename=spinner.getItemAtPosition(i).toString();
                tv.setText("To "+tablename);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent shcla =new Intent(impor.this,GSSAct.class);
                        shcla.putExtra("class",tablename);
                        startActivity(shcla);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



}
