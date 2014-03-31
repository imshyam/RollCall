package com.shyam.rollcall;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;

/**
 * Created by shyamsundar on 3/30/2014.
 */
public class AddFragment extends android.app.Fragment {
    public AddFragment(){}
    Spinner spinner;
    TextView text;
    Button b1;
    EditText e1,e2;
    Toast toast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_buddy, container, false);
        text=(TextView)rootView.findViewById(R.id.tv);
        spinner=(Spinner)rootView.findViewById(R.id.spinner);
        b1=(Button)rootView.findViewById(R.id.button);
        e1=(EditText)rootView.findViewById(R.id.editText3);
        e2=(EditText)rootView.findViewById(R.id.editText2);
        toast=Toast.makeText(getActivity(), "",Toast.LENGTH_SHORT);

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
                            database repeat=new database(getActivity());
                            repeat.open();
                            List<String> list=repeat.getEnr1(tablename);
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
                                database entry=new database(getActivity());
                                entry.open();
                                List<String> list1 = entry.getEnr1(tablename);


                                entry.createEntry(tablename, enr, name,"0");
                                entry.close();
                                dbAttendance create=new dbAttendance(getActivity());
                                create.open();
                                create.createEntry1(tablename,enr);
                                create.close();
                                itWorks=true;
                            }

                        }
                        catch (Exception e){
                            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();

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


    @Override
    public void onPause() {
        super.onPause();
        toast.cancel();

    }

}

