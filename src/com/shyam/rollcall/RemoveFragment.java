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
public class RemoveFragment extends android.app.Fragment {
    public RemoveFragment(){}
    Button b1;
    EditText e2;
    TextView text1;
    Spinner spinner;
    database db;
    Toast toast;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.remove_buddy, container, false);
        b1=(Button)rootView.findViewById(R.id.rmv);
        e2=(EditText)rootView.findViewById(R.id.editText1);
        spinner=(Spinner)rootView.findViewById(R.id.spi1);
        text1=(TextView)rootView.findViewById(R.id.textView11);
        db=new database(getActivity());
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
                        boolean allDone=false;
                        try {
                            boolean EntryExists=false;
                            db.open();
                            List<String> list=db.getEnr1(str);
                            db.close();
                            final String[]array=list.toArray(new String[list.size()]);
                            for(int i=0;i<list.size();i++) {
                                if(e2.getText().toString().equals(array[i])){
                                    EntryExists=true;
                                    break;
                                }
                            }

                            if(TextUtils.isEmpty(enroll)){
                                toast.setText("Please Enter Enrollment No.");
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
                                long enrNo=Long.parseLong(enroll);
                                db.deleteByEnr(str,enrNo);
                                db.close();
                                Toast.makeText(getActivity(), "DONE", Toast.LENGTH_LONG).show();
                                allDone=true;
                            }
                        }catch (Exception e){
                            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
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
    @Override
    public void onPause() {
        super.onPause();
        toast.cancel();
    }
}
