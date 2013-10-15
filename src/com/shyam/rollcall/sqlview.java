package com.shyam.rollcall;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by shyamsundar on 8/13/13.
 */
public class sqlview extends Activity{

    database db;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlview);
        Intent intent=getIntent();
        String clas=intent.getStringExtra("pas");
        String id1=intent.getStringExtra("id");
        int id=Integer.parseInt(id1);
        db=new database(this);
        tv=(TextView)findViewById(R.id.texV);
        tv.setText(clas);
        TableLayout ll=(TableLayout)findViewById(R.id.tl);
//id==0 for Enrollment no. and 1 for name...
        if(id==0){
            try {
                db.open();
                List<String> list;
                list=db.getEnr1(clas);
                db.close();
                Collections.sort(list, new Comparator<String>() {
                    @Override
                    public int compare(String s, String s2) {

                        return s.compareTo(s2);
                    }
                });
                String[] array = list.toArray(new String[list.size()]);
                for (int i=0;i<list.size();i++){
                    TableRow tbr=new TableRow(this);
                    TextView tid=new TextView(this);
                    TextView tv1=new TextView(this);
                    String st= ""+i;
                    tid.setText(st);
                    tv1.setText(array[i]);
                    CheckBox cb=new CheckBox(this);
                    tbr.addView(tid);
                    tbr.addView(tbr);
                    tbr.addView(cb);

                    ll.addView(tbr);
                    View v = new View(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 5);
                    v.setLayoutParams(params);
                    v.setBackgroundColor(Color.parseColor("#CACACA"));
                    ll.addView(v);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

       else if(id==1){
            try {
                db.open();
                List<String> list;
                list=db.getNAme(clas);
                db.close();
                Collections.sort(list, new Comparator<String>() {
                    @Override
                    public int compare(String s, String s2) {

                        return s.compareTo(s2);
                    }
                });
                String[] array = list.toArray(new String[list.size()]);
                for (int i=0;i<list.size();i++){
                    TableRow tbr=new TableRow(this);
                    TextView tid=new TextView(this);
                    TextView tv1=new TextView(this);
                    String st= ""+i;
                    tid.setText(st);
                    tv1.setText(array[i]);
                    CheckBox cb=new CheckBox(this);
                    tbr.addView(tid);
                    tbr.addView(tbr);
                    tbr.addView(cb);

                    ll.addView(tbr);
                    View v = new View(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 5);
                    v.setLayoutParams(params);
                    v.setBackgroundColor(Color.parseColor("#CACACA"));
                    ll.addView(v);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
             /*for(int i=1;i<30;i++) {
                 TableRow tbrow = new TableRow(this);
            LinearLayout.LayoutParams fieldparams = new LinearLayout.LayoutParams(1000,100, 100);
            tbrow.setLayoutParams(fieldparams);
                    TextView tv1=new TextView(this);
                    TextView tvid=new TextView(this);
                    String st=""+i;
                    tvid.setText(st);
                    tv1.setText("Element :"+ i + "\nFUCK YOU");
                    CheckBox cb=new CheckBox(this);
                    tbrow.addView(tvid);
                    tbrow.addView(cb);
                    tbrow.addView(tv1);

        ll.addView(tbrow);
        View v = new View(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(params);
        v.setBackgroundColor(Color.parseColor("#CACACA"));
        ll.addView(v);
    }*/
}}

