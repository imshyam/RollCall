package com.shyam.rollcall;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    Button b1,b2,b3,b4,b5,b6,b7,b8;
    TextView tv;
    private final static int ALERT_DIALOG1=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
        b1=(Button)findViewById(R.id.button3);
        b2=(Button)findViewById(R.id.button1);
        b4=(Button)findViewById(R.id.button6);
        b5=(Button)findViewById(R.id.button4);
        b6=(Button)findViewById(R.id.button8);
        b7=(Button)findViewById(R.id.button10);
        b8=(Button)findViewById(R.id.button7);
        tv=(TextView)findViewById(R.id.textView);

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        //textViewTime.setText(today.format("%k:%M:%S"));  // Current time
        tv.setText(today.monthDay+"-"+today.month+"-"+today.year);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addbud =new Intent(MainActivity.this,add_buddy.class);
                startActivity(addbud);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent take =new Intent(MainActivity.this,Take.class);
                startActivity(take);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sho =new Intent(MainActivity.this,show.class);
                startActivity(sho);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rmb =new Intent(MainActivity.this,remove_buddy.class);
                startActivity(rmb);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rmcl =new Intent(MainActivity.this,edit.class);
                startActivity(rmcl);
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shcla =new Intent(MainActivity.this,show_class.class);
                startActivity(shcla);
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.this.showDialog(ALERT_DIALOG1);

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
                b.setTitle("Roll Call");
                b.setMessage("Designed & Developed By : \n \t \t Shyam Sundar Choudhary \n \t \t IITR , B.Tech. , CSE , 2nd Year . ");
                b.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                });
                b.setCancelable(false);
                dialog=b.create();
                break;


            default:
        }
        return dialog;
    }



}
