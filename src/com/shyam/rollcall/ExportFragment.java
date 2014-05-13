package com.shyam.rollcall;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

/**
 * Created by shyamsundar on 3/30/2014.
 */
public class ExportFragment extends android.app.Fragment {
    public ExportFragment(){}
    Button b;
    Spinner spinner;
    TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.expor, container, false);
        b=(Button)rootView.findViewById(R.id.button);
        spinner=(Spinner)rootView.findViewById(R.id.spinner);
        tv=(TextView)rootView.findViewById(R.id.textView);
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
                tv.setText("To "+tablename);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ConnectionDetector cd = new ConnectionDetector(getActivity());
                        Boolean isInternetPresent = cd.isConnectingToInternet();
                        if(isInternetPresent){
                            Intent shcla =new Intent(getActivity(),GSSAct.class);
                            shcla.putExtra("class",tablename);
                            shcla.putExtra("type","1");
                            startActivity(shcla);
                        }
                        else
                        {
                            showAlertDialog(getActivity(), "No Internet Connection",
                                    "You don't have internet connection.", false);
                        }


                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon(R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
