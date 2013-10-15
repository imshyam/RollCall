package com.shyam.rollcall;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

/**
 * Created by shyamsundar on 8/13/13.
 */
public class add_buddy extends Activity{

    Spinner spinner;
    TextView text;
    Button b1;
    EditText e1,e2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_buddy);
        //Log.v("d","error");
        text=(TextView)findViewById(R.id.tv);
        spinner=(Spinner)findViewById(R.id.spinner);
        b1=(Button)findViewById(R.id.button);
        e1=(EditText)findViewById(R.id.editText3);
        e2=(EditText)findViewById(R.id.editText2);

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
                    text.setText("To "+tablename);


                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            boolean itWorks=true;
                            try{

                                String enr=e1.getText().toString();
                                String name=e2.getText().toString();
                                if (TextUtils.isEmpty(enr)||TextUtils.isEmpty(name)) {
                                    // showToast("Please Enter Your Name");
                                    Toast.makeText(getApplicationContext(),"Please Fill All Parts",Toast.LENGTH_LONG).show();
                                    e1.requestFocus();
                                    itWorks=false;
                                }
                                else if (TextUtils.isEmpty(tablename)) {
                                    // showToast("Please Enter Your Name");
                                    Toast.makeText(getApplicationContext(),"Choose Proper Class",Toast.LENGTH_LONG).show();
                                    e1.requestFocus();
                                    itWorks=false;}
                                else {
                                database entry=new database(add_buddy.this);
                                entry.open();
                                        entry.createEntry(tablename, enr, name);
                                entry.close();
                                        }

                            }
                            catch (Exception e){
                                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();

                            }
                            finally {
                                if(itWorks){
                                    Toast.makeText(getApplicationContext(),"DONE",Toast.LENGTH_LONG).show();
                                    e2.setText("");
                                    e1.requestFocus();
                                }
                            }
                        }
                    });


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            // spinner.setWillNotDraw(false);



    }




    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
