package com.shyam.rollcall;

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
public class EditFragment extends android.app.Fragment {
    public EditFragment(){}
    Spinner sp1;
    Button b1;
    TextView tv;
    EditText et1,et2;
    database db;
    Toast toast;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.edit, container, false);
        sp1=(Spinner)rootView.findViewById(R.id.spinner);
        tv=(TextView)rootView.findViewById(R.id.textView);
        et1=(EditText)rootView.findViewById(R.id.editText);
        et2=(EditText)rootView.findViewById(R.id.editText2);
        db=new database(getActivity());
        b1=(Button)rootView.findViewById(R.id.button);
        toast=Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT);
        loadSpinner();
        return rootView;
    }
    private void loadSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
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
                            boolean EntryExists=false;
                            db.open();
                            List<String> list=db.getEnr1(str);
                            db.close();
                            final String[]array=list.toArray(new String[list.size()]);
                            for(int i=0;i<list.size();i++) {
                                if(Enr.equals(array[i])){
                                    EntryExists=true;
                                    break;
                                }
                            }
                            if(TextUtils.isEmpty(Enr)||TextUtils.isEmpty(Name)){
                                toast.setText("Please Fill All Portions");
                                toast.show();
                            }
                            else if (EntryExists==false) {
                                // showToast("Please Enter Your Name");
                                toast.setText("No Such Enrollment No. Exists.");
                                toast.show();
                            }
                            else
                            {
                                db.open();
                                db.Modify(str,Enr,Name);
                                db.close();
                                Toast.makeText(getActivity(),"DONE",Toast.LENGTH_LONG).show();
                                allDone=true;
                            }
                        }catch (Exception e){
                            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
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
    @Override
    public void onPause() {
        super.onPause();
        toast.cancel();
    }
}
