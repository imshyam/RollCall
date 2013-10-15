package com.shyam.rollcall;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by shyamsundar on 8/13/13.
 */
public class remove_buddy extends Activity{

    Button b1;
    EditText e2;
    TextView text1;
    Spinner spinner;
    database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_buddy);

        b1=(Button)findViewById(R.id.rmv);
        e2=(EditText)findViewById(R.id.editText1);
        spinner=(Spinner)findViewById(R.id.spi1);
        text1=(TextView)findViewById(R.id.textView11);
        db=new database(remove_buddy.this);





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
                text1.setText("Remove from"+str);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String enroll=e2.getText().toString();
                        long enrNo=Long.parseLong(enroll);
                        boolean allDone=false;
                        try {
                            if(TextUtils.isEmpty(enroll))
                                Toast.makeText(getApplicationContext(),"Please Enter Enrollment No.",Toast.LENGTH_LONG).show();
                            else
                            {

                                db.open();
                                    db.deleteByEnr(str,enrNo);
                                db.close();
                                Toast.makeText(getApplicationContext(),"DONE",Toast.LENGTH_LONG).show();
                                allDone=true;
                            }
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                        }finally {
                            if(allDone)
                            {
                                e2.setText("");
                            }
                        }
                        db.close();
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



