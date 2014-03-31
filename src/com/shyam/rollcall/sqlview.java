package com.shyam.rollcall;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by shyamsundar on 8/13/13.
 */
public class sqlview extends Activity{

    database db;
    dbAttendance da;
    TextView tv;
    Toast toast;
    String clas;
    int i=0;//for checking whether data is saved or not

    private final static int ALERT_DIALOG1=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlview);
        Intent intent=getIntent();
        String id1=intent.getStringExtra("id");
        clas=intent.getStringExtra("pas");
        int id=Integer.parseInt(id1);
        db=new database(this);
        da=new dbAttendance(this);
        tv=(TextView)findViewById(R.id.texV);
        tv.setText(clas);
        TableLayout ll=(TableLayout)findViewById(R.id.tl);
        toast=Toast.makeText(getApplicationContext(), "",Toast.LENGTH_SHORT);

//id==0 for enrollment no. and 1 for name
        //using enrollment no.
if(id==0){
            try {
                db.open();
                List<String>list=db.getEnr1(clas);
                db.close();
                Collections.sort(list, new Comparator<String>() {
                    @Override
                    public int compare(String s, String s2) {

                        return s.compareTo(s2);
                    }
                });
                final String[]array=list.toArray(new String[list.size()]);
                for(int i=0;i<list.size();i++) {
                    TableRow tbrow = new TableRow(this);
                    TextView tv1=new TextView(this);
                    TextView tvid=new TextView(this);
                    final String st=""+(i+1)+"  ";
                    tvid.setText(st);
                    tv1.setText(array[i]);
                    final CheckBox cb=new CheckBox(this);
                    tbrow.addView(tvid);
                    tbrow.addView(tv1);
                    tbrow.addView(cb);
                    final int finalI = i;
                    cb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(cb.isChecked()){
                            StringBuffer result = new StringBuffer();
                            result.append(array[finalI]+" is present.");
                            toast.setText(result.toString());
                            toast.show();
                                try {
                                    db.open();
                                    db.modifyToday(clas,Long.parseLong(array[finalI]),"1");
                                    db.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                StringBuffer result = new StringBuffer();
                                result.append(array[finalI]+" is absent.");
                                toast.setText(result.toString());
                                toast.show();
                                try {
                                    db.open();
                                    db.modifyToday(clas,Long.parseLong(array[finalI]),"0");
                                    db.close();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });



                    ll.addView(tbrow);
                    View v = new View(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);
                    v.setLayoutParams(params);
                    v.setBackgroundColor(Color.parseColor("#00bfbf"));
                    ll.addView(v);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }}
///attendance using name
        else {
    try {
        db.open();
        List<String>list=db.getEnr1(clas);
        db.close();
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String s, String s2) {

                return s.compareTo(s2);
            }
        });
        final String[]array=list.toArray(new String[list.size()]);
        for(int i=0;i<list.size();i++) {
            TableRow tbrow = new TableRow(this);
            TextView tv1=new TextView(this);
            TextView tvid=new TextView(this);
            String st=""+(i+1)+"  ";
            tvid.setText(st);
            db.open();
            long l=Long.parseLong(array[i]);
            final String name=db.getname(clas,l);
            db.close();
            tv1.setText(name);

            final CheckBox cb=new CheckBox(this);
            tbrow.addView(tvid);
            tbrow.addView(tv1);
            tbrow.addView(cb);

            final int finalI = i;
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cb.isChecked()) {
                        StringBuffer result = new StringBuffer();
                        result.append(name + " is present.");
                        toast.setText(result.toString());
                        toast.show();
                        try {
                            db.open();
                            db.modifyToday(clas, Long.parseLong(array[finalI]), "1");
                            db.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        StringBuffer result = new StringBuffer();
                        result.append(name + " is absent.");
                        toast.setText(result.toString());
                        toast.show();
                        try {
                            db.open();
                            db.modifyToday(clas, Long.parseLong(array[finalI]), "0");
                            db.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            ll.addView(tbrow);
            View v = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);
            v.setLayoutParams(params);
            v.setBackgroundColor(Color.parseColor("#00BFBF"));
            ll.addView(v);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }    }
        Button bsave=(Button)findViewById(R.id.save);
        bsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(i==0) {
                    sqlview.this.showDialog(ALERT_DIALOG1);
                }
                else {
                    toast.setText("Data Already Saved");
                    toast.show();
                }



            }
        });
    }
    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        Dialog dialog=null;
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        switch(id)
        {
            case ALERT_DIALOG1:
                b.setTitle("Save");
                b.setMessage("Sure To Save.");
                b
                        // Inflate and set the layout for the dialog
                        // Pass null as the parent view because its going in the dialog layout
                        // Add action buttons
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                try {
                                    db.open();
                                    List<String> list = db.getEnr1(clas);
                                    String[] array = list.toArray(new String[list.size()]);
                                    for (int i = 0; i < list.size(); i++) {
                                        String today = db.getToday(clas, Long.parseLong(array[i]));
                                        da.open();
                                        da.modifyAtten(clas, Long.parseLong(array[i]), Long.parseLong(today));
                                        da.close();

                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } finally {
                                    toast.setText("Done");
                                    toast.show();
                                    i++;
                                }
                                }
                            }

                            )
                                    .

                            setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                public void onClick (DialogInterface dialog,int id){
                                    dialog.cancel();
                                }
                            }

                            );

                            b.setCancelable(false);
                            dialog=b.create();
                            break;

                            default:
                        }
                return dialog;
    }

    @Override
    public void onBackPressed() {
        if(i==0){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();}
        else finish();
    }

}

