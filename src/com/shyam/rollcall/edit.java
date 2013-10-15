package com.shyam.rollcall;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
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
 * Created by shyamsundar on 8/22/13.
 */
public class edit extends Activity {
    Spinner sp1;
    Button b1;
    TextView tv;
    EditText et1,et2;
    database db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        sp1=(Spinner)findViewById(R.id.spinner);
        tv=(TextView)findViewById(R.id.textView);
        et1=(EditText)findViewById(R.id.editText);
        et2=(EditText)findViewById(R.id.editText2);
        db=new database(edit.this);
        b1=(Button)findViewById(R.id.button);
        loadSpinner();
    }

    private void loadSpinner() {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.classes, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            sp1.setAdapter(adapter);
            sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    final String str=sp1.getItemAtPosition(i).toString();
                    tv.setText("From "+str);
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String Enr=et1.getText().toString();
                            String Name=et2.getText().toString();
                            boolean allDone=false;
                            try {
                                if(TextUtils.isEmpty(Enr)||TextUtils.isEmpty(Name))
                                    Toast.makeText(getApplicationContext(),"Please Fill All Portions",Toast.LENGTH_LONG).show();
                                else
                                {
                                    db.open();
                                        db.Modify(str,Enr,Name);
                                    db.close();
                                    Toast.makeText(getApplicationContext(),"DONE",Toast.LENGTH_LONG).show();
                                    allDone=true;
                                }
                            }catch (Exception e){
                                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                            }finally {
                                if(allDone)
                                {
                                    et2.setText("");
                                    et1.setText("");
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
}
