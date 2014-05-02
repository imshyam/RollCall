
package com.shyam.rollcall;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;
import com.pras.SpreadSheet;
import com.pras.SpreadSheetFactory;
import com.pras.WorkSheet;
import com.pras.WorkSheetCell;
import com.pras.WorkSheetRow;


public class WKDetails extends Activity {
	
	int wkID;
	int spID;
	ArrayList<WorkSheetRow> rows;
    private ProgressDialog progress;
	String[] cols;
	TextView tv;
    Button b1;
    String nameDb,enrDb,classname;
    Toast toast;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.wk_details);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#848789"));

		Intent intent = getIntent();
		wkID = intent.getIntExtra("wk_id", -1);
		spID = intent.getIntExtra("sp_id", -1);
        progress = new ProgressDialog(this);
		classname=intent.getStringExtra("class");
		if(wkID == -1 || spID == -1){
			finish();
			return;
		}

        toast=Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT);
		tv =(TextView)findViewById(R.id.textView);
		b1=(Button)findViewById(R.id.button);
		new MyTask().execute();
	}
	
	private class MyTask extends AsyncTask{

		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
            progress.setCancelable(true);
            progress.setIndeterminate(true);
            progress.setMessage("Fetching Data :");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.show();
		}

		@Override
		protected Object doInBackground(Object... params) {
			// Read Spread Sheet list from the server.
			SpreadSheetFactory factory = SpreadSheetFactory.getInstance();
			// Read from local Cache
			ArrayList<SpreadSheet> sps = factory.getAllSpreadSheets(false);
			SpreadSheet sp = sps.get(spID);
			WorkSheet wk = sp.getAllWorkSheets(false).get(wkID);
			cols = wk.getColumns();
			rows = wk.getData(false);

			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(progress.isShowing())
				progress.cancel();
			
			if(rows == null || rows.size() == 0){
				tv.setText("No record exists....");
				setContentView(tv);
				return;
			}
			
			StringBuffer record = new StringBuffer();
			
			if(cols != null){
				record.append("Columns: ["+ cols +"]\n");
			}
			record.append("Number of Records: "+ rows.size()+"\n");
			
			for(int i=0; i<rows.size(); i++){
				WorkSheetRow row = rows.get(i);
				record.append("[ Row ID "+ (i + 1) +" ]\n");
                Boolean doneLoading=false;
				ArrayList<WorkSheetCell> cells = row.getCells();
				
				for(int j=0; j<cells.size(); j++){
					WorkSheetCell cell = cells.get(j);
                    if(cell.getName().equalsIgnoreCase("name")||cell.getName().equalsIgnoreCase("enrollmentno.")){
					    record.append(cell.getName() +" = "+ cell.getValue() +"\n");
                    }

                    }

          }
      tv.setText(record);
//----------------loading to database--------------

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0; i<rows.size(); i++){
                        WorkSheetRow row = rows.get(i);
                        ArrayList<WorkSheetCell> cells = row.getCells();

                        for(int j=0; j<cells.size(); j++){
                            WorkSheetCell cell = cells.get(j);
                            if(cell.getName().equalsIgnoreCase("name")){
                                nameDb=cell.getValue();
                            }
                            if(cell.getName().equalsIgnoreCase("enrollmentno.")) {
                                enrDb = cell.getValue();

                                database db = new database(WKDetails.this);

                                try {
                                    db.open();
                                    boolean repe = false;
                                    database repeat = new database(WKDetails.this);
                                    repeat.open();
                                    List<String> list = repeat.getEnr1(classname);
                                    repeat.close();
                                    final String[] array = list.toArray(new String[list.size()]);
                                    for (int k = 0; k < list.size(); k++) {
                                        if (enrDb.equals(array[k])) {
                                            repe = true;
                                            break;
                                        }
                                    }
                                    if (repe == false) {
                                        db.createEntry(classname, enrDb, nameDb, "0");
                                        dbAttendance create = new dbAttendance(WKDetails.this);
                                        create.open();
                                        create.createEntry1(classname, enrDb);
                                        create.close();
                                        db.close();
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }finally {
                                    toast.setText("Done Loading Database");
                                    toast.show();
                                }
                            }
                        }

                    }
                }
            });

  }
   }
    @Override
    public void onPause() {
        super.onPause();
        toast.cancel();

    }
 }