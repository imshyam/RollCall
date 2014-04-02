
package com.shyam.rollcall;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;


import com.pras.SpreadSheet;
import com.pras.SpreadSheetFactory;
import com.shyam.rollcall.auth.AndroidAuthenticator;

public class GSSAct extends Activity {
    
	ArrayList<SpreadSheet> spreadSheets;
	TextView tv;
	ListView list;
    String classname;
    private ProgressDialog progress;
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#848789"));

        Intent intent=getIntent();
        classname=intent.getStringExtra("class");
        list = new ListView(this.getApplicationContext());

        tv = new TextView(this.getApplicationContext());
        progress = new ProgressDialog(this);
        // Init and Read SpreadSheet list from Google Server
        new MyTask().execute(null);
    }
	
	private class MyTask extends AsyncTask{



		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
            progress.setCancelable(true);
            progress.setIndeterminate(true);
            progress.setMessage("Fetching Spreadsheet List :");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.show();

		}

		@Override
		protected Object doInBackground(Object... params) {
			// Read Spread Sheet list from the server.
			SpreadSheetFactory factory = SpreadSheetFactory.getInstance(new AndroidAuthenticator(GSSAct.this));
		    spreadSheets = factory.getAllSpreadSheets();
			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(progress.isShowing())
				progress.cancel();

			if(spreadSheets == null || spreadSheets.size() == 0){
		        tv.setText("No spreadsheet exists in your account...");
		        setContentView(tv);
		    }
		    else{
		        //tv.setText(spreadSheets.size() + "  spreadsheets exists in your account...");
		    	ArrayAdapter<String> arrayAdaper = new ArrayAdapter<String>(GSSAct.this.getApplicationContext(), android.R.layout.simple_list_item_1);
		    	for(int i=0; i<spreadSheets.size(); i++){
		    		SpreadSheet sp = spreadSheets.get(i);
		    		arrayAdaper.add(sp.getTitle());
		    	}
		    	list.addHeaderView(tv);
		    	list.setAdapter(arrayAdaper);
		    	tv.setText("Number of SpreadSheets (" + spreadSheets.size() + ")");

		    	list.setOnItemClickListener(new OnItemClickListener(){

					public void onItemClick(AdapterView<?> adapterView, View view,
							int position, long id) {
						// Show details of the SpreadSheet
						if(position == 0)
							return;

						Toast.makeText(GSSAct.this.getApplicationContext(), "Showing SP details, please wait...", Toast.LENGTH_LONG).show();

						// Start SP Details Activity
						Intent i = new Intent(GSSAct.this, GSSDetails.class);
						i.putExtra("sp_id", position - 1);
                        i.putExtra("class",classname);
						startActivity(i);
					}
		    	});
		    	setContentView(list);
		    }
		}

	}
}