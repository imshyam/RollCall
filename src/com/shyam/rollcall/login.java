package com.shyam.rollcall;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by shyamsundar on 11/24/13.
 */
public class login extends Activity{
    EditText usr,pswd,reenter;
    Button lgin,reset,setup;
    CheckBox cb;
    TextView tries,left;
    Toast toast;
    SharedPreferences somedata;
	public static String file="MyShPre";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        somedata=getSharedPreferences(file, MODE_PRIVATE);
        toast=Toast.makeText(getApplicationContext(), "",Toast.LENGTH_SHORT);
        
        usr=(EditText)findViewById(R.id.editText);
        pswd=(EditText)findViewById(R.id.editText1);
        reenter=(EditText)findViewById(R.id.editText2);
        lgin=(Button)findViewById(R.id.button);
        setup=(Button)findViewById(R.id.button1);
        reset=(Button)findViewById(R.id.button2);
        cb=(CheckBox)findViewById(R.id.checkBox);
        tries=(TextView)findViewById(R.id.textView3);
        left=(TextView)findViewById(R.id.textView4);
        
        if(TextUtils.isEmpty(somedata.getString("username", null)))
        {
        	lgin.setVisibility(View.INVISIBLE);
        	cb.setVisibility(View.INVISIBLE);
        	reset.setVisibility(View.INVISIBLE);
        	tries.setVisibility(View.INVISIBLE);
        	left.setVisibility(View.INVISIBLE);
        	usr.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        	pswd.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        	
        	setup.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(TextUtils.isEmpty(usr.getText().toString())||TextUtils.isEmpty(pswd.getText().toString())||TextUtils.isEmpty(reenter.getText().toString()))
					{
						toast.setText("Fill All Credentials.");
						toast.show();
					}
					else if(pswd.getText().toString().equals(reenter.getText().toString()))
					{
						SharedPreferences.Editor editor=somedata.edit();
						editor.putString("username", usr.getText().toString());
						editor.putString("password",reenter.getText().toString());
						editor.commit();
						Toast.makeText(getApplicationContext(),"Done.",1).show();
						Intent intent =new Intent(login.this,MainActivity.class);
		                startActivity(intent);
					}
					else
					{
						toast.setText("Password Didn't Match.");
						toast.show();
					}
				}
			});
        }
        else
        {
        	reenter.setVisibility(View.INVISIBLE);
        	setup.setVisibility(View.INVISIBLE);
        	usr.setText(somedata.getString("memory", null));
        	cb.setChecked(true);
        	usr.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        	pswd.setImeOptions(EditorInfo.IME_ACTION_GO);
        	
        	lgin.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if(TextUtils.isEmpty(pswd.getText().toString())&&TextUtils.isEmpty(usr.getText().toString())){
		                toast.setText("Enter Username and Password.");
						toast.show();
		            }
		            else if(TextUtils.isEmpty(pswd.getText().toString())){
		                toast.setText("Enter Password.");
						toast.show();
		            }
		            else if(TextUtils.isEmpty(usr.getText().toString())){
		            	
		                toast.setText("Enter Username.");
						toast.show();
		            }
		            else
		            {
		            	long l=Long.parseLong(left.getText().toString());
		            	if(l>0)
		            	{
		            		if(usr.getText().toString().equals(somedata.getString("username", null))&&pswd.getText().toString().equals(somedata.getString("password",null)))
			            	{
			            		if(cb.isChecked())
			            		{
			            			SharedPreferences.Editor editor=somedata.edit();
									editor.putString("memory",usr.getText().toString());
									editor.commit();
			            		}
			            		else
			            		{
			            			SharedPreferences.Editor editor=somedata.edit();
									editor.putString("memory",null);
									editor.commit();
			            		}
			            		Intent intent =new Intent(login.this,MainActivity.class);
				                startActivity(intent);
			            	}
			            	else
			            	{
			            		toast.setText("Wrong Username or Password.");
								toast.show();
			            		l--;
			            		left.setText(String.valueOf(l));
			            		usr.setText(null);
			            		pswd.setText(null);
			            	}
		            	}
		            	else{
		            	toast.setText("No More Tries Left.");
						toast.show();
		            	}
		            }
					
				}
			});
        }
        
          
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
        toast.cancel();
    }
}
