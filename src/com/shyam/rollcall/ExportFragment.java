package com.shyam.rollcall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;
import java.util.HashMap;

import android.widget.Toast;
import com.pras.Log;
import com.pras.SpreadSheet;
import com.pras.SpreadSheetFactory;
import com.pras.WorkSheet;
import com.pras.table.Record;
import com.shyam.rollcall.auth.AndroidAuthenticator;

/**
 * Created by shyamsundar on 3/30/2014.
 */
public class ExportFragment extends android.app.Fragment {
    public ExportFragment(){}
    Button b1;
    Toast toast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.expor, container, false);
        b1=(Button)rootView.findViewById(R.id.bex);
        toast=Toast.makeText(getActivity(),"",Toast.LENGTH_LONG);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create SpreadSheet Factory
                SpreadSheetFactory spf = SpreadSheetFactory.getInstance(new AndroidAuthenticator(getActivity()));

                // Get All SpreadSheets
                //ArrayList<SpreadSheet> spreadSheets = spf.getAllSpreadSheets();

                // Get selected SpreadSheet- whose name contains "Pras"
                ArrayList<SpreadSheet> spreadSheets = spf.getSpreadSheet("responces", false);

                if(spreadSheets == null || spreadSheets.size() == 0){
                    toast.setText("No SpreadSheet Exists!");
                    toast.show();
                    return;
                }

                toast.setText("Number of SpreadSheets: "+ spreadSheets.size());
                toast.show();
                SpreadSheet sp = spreadSheets.get(0);

                // Add an WorkSheet
                //sp.addWorkSheet("testWork1", new String[]{"date", "item", "price"});
                //sp.addWorkSheet("tabWork2", new String[]{"date", "item", "price", "person"});

                // Get all WorkSheets
                // ArrayList<WorkSheet> wks = sp.getAllWorkSheets();

                // Get selected WorkSheet
                ArrayList<WorkSheet> wks = sp.getWorkSheet("Form Responces", false);

                if(wks == null || wks.size() == 0){
                    toast.setText("No WorkSheet exists!!");
                    toast.show();
                    return;
                }

                toast.setText("Number of WorkSheets: "+ wks.size());
                toast.show();
                WorkSheet wk = null;
		/*for(int i=0; i<wks.size(); i++){

			 * Search for WorkSheet having name "tab_*"

			if(wks.get(i).getTitle().startsWith("tab_")){
				wk = wks.get(i);
				break;
			}
		}*/
                wk = wks.get(0);

                toast.setText("wk="+ wk.getTitle());
                toast.show();
                HashMap<String, String> rs = new HashMap<String, String>();
                rs.put("date", "1st Jan 2011");
                rs.put("item", "Item3");
                rs.put("price", "250");
                wk.addRecord(sp.getKey(), rs);

		/*
		 * WorkSheet Record Handling
		 */
                // Get Data
                ArrayList<Record> records = wk.getRecords(sp.getKey());

                if(records == null || records.size() == 0){
                    toast.setText("No Record exists!!");
                    toast.show();
                    return;
                }

                toast.setText("Number of Records: "+ records.size());
                toast.show();
                // Display Record
                for(int i=0; i<records.size(); i++){
                    Record r = records.get(i);
                    HashMap<String, String> data = r.getData();
                    toast.setText("Data: "+ data);
                    toast.show();
                }

                // Delete Record
                //wk.deleteRecord(records.get(0));

                // Update Record
                //Record toUpdate = records.get(0);

                //toUpdate.addData("Name", "Update_Name");
                //wk.updateRecord(toUpdate);


            }
        });
        return rootView;
    }
}
