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
import java.util.*;

/**
 * Created by shyamsundar on 8/13/13.
 */
public class add_buddy extends Activity{

    Spinner spinner;
    TextView text;
    Button b1;
    EditText e1,e2;
    Toast toast;

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
        toast=Toast.makeText(getApplicationContext(), "",Toast.LENGTH_SHORT);

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

                            boolean itWorks=false;
                            try{
                                String enr=e1.getText().toString();
                                String name=e2.getText().toString();
                                boolean repe=false;
                                database repeat=new database(add_buddy.this);
                                repeat.open();
                                List<String>list=repeat.getEnr1(tablename);
                                repeat.close();
                                final String[]array=list.toArray(new String[list.size()]);
                                for(int i=0;i<list.size();i++) {
                                    if(enr.equals(array[i])){
                                        repe=true;
                                        break;
                                    }
                                }

                                if (repe) {
                                    // showToast("Please Enter Your Name");
                                    toast.setText("Enrollment No. Already Exists.");
                                    toast.show();
                                }


                                else if (TextUtils.isEmpty(enr)||TextUtils.isEmpty(name)) {
                                    // showToast("Please Enter Your Name");
                                    toast.setText("Please Fill All Parts");
                                    toast.show();
                                    e1.requestFocus();
                                }
                                else if (TextUtils.isEmpty(tablename)) {
                                    // showToast("Please Enter Your Name");
                                    toast.setText("Choose Proper Class");
                                    toast.show();
                                    e1.requestFocus();
                                }
                                else {
                                database entry=new database(add_buddy.this);
                                entry.open();
                                    List<String> list1 = entry.getEnr1(tablename);
                                    boolean alreadyExists=true;

                                    entry.createEntry(tablename, enr, name,"0");
                                    entry.close();
                                    dbAttendance create=new dbAttendance(add_buddy.this);
                                    create.open();
                                    create.createEntry1(tablename,enr);
                                    create.close();
                                    itWorks=true;
                                        }

                            }
                            catch (Exception e){
                                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();

                            }
                            finally {
                                if(itWorks){
                                    toast.setText("done");
                                    toast.show();
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

}
