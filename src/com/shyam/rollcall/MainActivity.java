//home page of application
package com.shyam.rollcall;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class MainActivity extends Activity {
    Button b1,b2,b3,b4,b5,b6,b7,b8;
    TextView tv;
    SharedPreferences somedata;
	public static String file="MyShPre";
	
    private final static int ALERT_DIALOG1=1;
    private final static int ALERT_DIALOG2=2;
    private final static int ALERT_DIALOG3=3;
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
        
        somedata=getSharedPreferences(file, MODE_PRIVATE);

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        //textViewTime.setText(today.format("%k:%M:%S"));  // Current time
        tv.setText(today.monthDay+"-"+(++today.month)+"-"+today.year);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuchange, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click

        switch (item.getItemId()) {
            case R.id.action_changePass:
                MainActivity.this.showDialog(ALERT_DIALOG2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                b.setMessage("Designed & Developed By : \n \t \t Shyam Sundar Choudhary \n \t \t -CSE sophomore at IITR");
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
            case ALERT_DIALOG2:
                b.setTitle("Change Password");
                LayoutInflater inflater = this.getLayoutInflater();
                final View view=inflater.inflate(R.layout.change_password, null) ;
                b.setView(view)
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                        // Add action buttons
                        .setPositiveButton("Signin", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                EditText usr = (EditText) view.findViewById(R.id.username);
                                EditText pswd = (EditText) view.findViewById(R.id.password);
                                if(TextUtils.isEmpty(usr.getText().toString())||TextUtils.isEmpty(pswd.getText().toString()))
                                    {Toast.makeText(getApplicationContext(), "Empty Username or Password.", Toast.LENGTH_LONG).show();
                                        dialog.cancel();}
                                else {
                                   if(somedata.getString("username",null).equals(usr.getText().toString())&&(somedata.getString("password",null).equals(pswd.getText().toString())))
                                     { dialog.cancel();
                                       MainActivity.this.showDialog(ALERT_DIALOG3);}
                                    else {
                                       Toast.makeText(getApplicationContext(), "Wrong Username or Password.\nTry Again.", Toast.LENGTH_LONG).show();
                                       dialog.cancel();
                                     }
                                    }
                            }
                            }
                        )
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                b.setCancelable(false);
                dialog=b.create();
                break;
            case ALERT_DIALOG3:
                b.setTitle("Change Password");
                LayoutInflater inflater1 = this.getLayoutInflater();
                final View view1=inflater1.inflate(R.layout.change_conf, null) ;
                b.setView(view1)
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                        // Add action buttons
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                EditText usr = (EditText) view1.findViewById(R.id.username);
                                EditText pswd = (EditText) view1.findViewById(R.id.password);
                                EditText pswdConf = (EditText) view1.findViewById(R.id.confirm);
                                if (TextUtils.isEmpty(pswd.getText().toString())||TextUtils.isEmpty(usr.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Password or Username Can't Be Null.", Toast.LENGTH_LONG).show();
                                else if (TextUtils.isEmpty(pswdConf.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Please Confirm Password.", Toast.LENGTH_LONG).show();
                                else if (pswd.getText().toString().equals(pswdConf.getText().toString()) == false)
                                    Toast.makeText(getApplicationContext(), "Both The Passwords Are Not Same.", Toast.LENGTH_LONG).show();
                                else {
                                	SharedPreferences.Editor editor=somedata.edit();
                    				editor.putString("username", usr.getText().toString());
                    				editor.putString("password", pswd.getText().toString());
                    				editor.commit();
                    				Toast.makeText(getApplicationContext(), "Password Successfully Changed.", Toast.LENGTH_LONG).show();
                                }
                                
                        }
                    })
                                    
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
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
